package de.gmino.meva.android.request;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.shared.request.NetworkRequests;
import de.gmino.meva.shared.request.RequestListener;

/**
 * This class implements NetworkRequests based on Android AysncTask and binary
 * communication over HTTP Post. Just as the name implies.
 * 
 * @author lena
 * 
 */
public class NetworkRequestsImplAsyncTaskBinaryHttp implements NetworkRequests {

	String baseUrl;
	
	Map<Integer, Collection<Long>> queryCache = new TreeMap<Integer, Collection<Long>>();

	public NetworkRequestsImplAsyncTaskBinaryHttp(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public void getIdsByQuery(Query query, final RequestListener<Long> listener) {
		StringBuilder sb = new StringBuilder();
		try {
			query.serializeJson(sb);
			final int queryHash = sb.toString().hashCode();
			Collection<Long> result = queryCache.get(queryHash);
			if(result != null)
			{
				listener.onFinished(result);
				return;
			}
			else
			{
				new AsyncTask<Query, Long, Collection<Long>>() {

					private Throwable storedException;


					protected Collection<Long> doInBackground(Query... queryParams) {
						Query query = queryParams[0];
						Collection<Long> ids = new LinkedList<Long>();

						try {
							HttpClient client = new DefaultHttpClient();
							HttpPost request = new HttpPost();

							request.setURI(new URI(Util.getBaseUrl() + "Binary/"
									+ query.getUrlPostfix()));

							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							DataOutputStream dos = new DataOutputStream(baos);
							((ValueBinary) query).serializeBinary(dos);

							HttpEntity postBody = new ByteArrayEntity(
									baos.toByteArray());
							request.setEntity(postBody);

							HttpResponse response = client.execute(request);
							DataInputStream dis = new DataInputStream(response
									.getEntity().getContent());

							long id = dis.readLong();
							while (id != 0) {
								ids.add(id);
								publishProgress(id);
								id = dis.readLong();
							}
						} catch (Exception e) {
							this.cancel(false);
							storedException = e;
							return null;
						}
						queryCache.put(queryHash, ids);
						return ids;
					};

					protected void onPostExecute(java.util.Collection<Long> result) {
						listener.onFinished(result);
					};
					
					protected void onProgressUpdate(Long... ids) 
					{
						for(long id : ids)
							listener.onNewResult(id);
					};
					
					
					protected void onCancelled(java.util.Collection<Long> result) {
						listener.onError("Request was cancelled due to an exception.", storedException);
					};
				}.execute(query);
			}
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
	
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getNewIds(final EntityTypeName type, int count,
			final RequestListener<Long> listener) {

		new AsyncTask<Integer, Long, Collection<Long>>() {
			private Throwable storedException;


			protected Collection<Long> doInBackground(Integer... countParams) {
				int count = countParams[0];
				Collection<Long> ids = new ArrayList<Long>(count);
				try {
					HttpClient client = new DefaultHttpClient();
					HttpPost request = new HttpPost();
					request.setURI(new URI(baseUrl + "Binary/newEntities"));

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(baos);
					dos.writeUTF(type.toString());
					dos.writeInt(count);

					HttpEntity postBody = new ByteArrayEntity(
							baos.toByteArray());
					request.setEntity(postBody);

					HttpResponse response = client.execute(request);
					DataInputStream dis = new DataInputStream(response
							.getEntity().getContent());

					for (int i = 0; i < count; i++)
					{
						long id = dis.readLong();
						publishProgress(id);
						ids.add(id);
					}
					
					
				} catch (Exception e) {
					this.cancel(false);
					storedException = e;
					return null;
				}
				return ids;
			};
			
			protected void onPostExecute(java.util.Collection<Long> result) {
				listener.onFinished(result);
			};
			
			protected void onProgressUpdate(Long... ids) 
			{
				for(long id : ids)
					listener.onNewResult(id);
			};
			
			
			protected void onCancelled(java.util.Collection<Long> result) {
				listener.onError("Request was cancelled due to an exception.", storedException);
			};
		}.execute(count);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <EntityClass extends Entity> void loadEntities(
			final Collection<EntityClass> entities,
			final RequestListener<EntityClass> listener) {
		
		Collection<EntityClass> unloadedEntities = null;
		
		for(EntityClass e : entities)
		{
			if(!e.isReady())
			{
				if(unloadedEntities == null)
					unloadedEntities = new LinkedList<EntityClass>();
				unloadedEntities.add(e);
			}
		}

		if(unloadedEntities == null || unloadedEntities.isEmpty())
		{
			listener.onFinished(entities);
			return;
		}
		
		new AsyncTask<Collection<EntityClass>, EntityClass, Collection<EntityClass>>() {
			private Throwable storedException;


			protected Collection<EntityClass> doInBackground(
					Collection<EntityClass>... entitiesParams) {
				Collection<EntityClass> entities = entitiesParams[0];
				try {
					HttpClient client = new DefaultHttpClient();
					HttpPost request = new HttpPost();
					request.setURI(new URI(baseUrl + "Binary/getEntities"));

					String typeName = entities.iterator().next().getTypeName();

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(baos);
					dos.writeUTF(typeName);
					for (EntityClass e : entities)
						dos.writeLong(e.getId());
					dos.writeLong(0);

					HttpEntity postBody = new ByteArrayEntity(
							baos.toByteArray());
					request.setEntity(postBody);

					HttpResponse response = client.execute(request);
					DataInputStream dis = new DataInputStream(response
							.getEntity().getContent());

					for (EntityClass e : entities) {
						((EntityBinary) e).deserializeBinary(dis);
						publishProgress(e);
					}

				} catch (Exception e) {
					this.cancel(false);
					storedException = e;
					return null;
					
				}
				return entities;
			};
			
			protected void onPostExecute(java.util.Collection<EntityClass> result) {
				listener.onFinished(result);
			};
			
			protected void onProgressUpdate(EntityClass... entities)
			{
				for(EntityClass entity : entities)
					listener.onNewResult(entity);
			};
			
			
			protected void onCancelled(java.util.Collection<EntityClass> result) {
				listener.onError("Request was cancelled due to an exception.", storedException);
			};
		}.execute(unloadedEntities);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <EntityClass extends Entity> void saveEntities(
			Collection<EntityClass> entities,
			final RequestListener<EntityClass> listener) {
		new AsyncTask<Collection<EntityClass>, EntityClass, Collection<EntityClass>>() {
			private Throwable storedException;

			protected Collection<EntityClass> doInBackground(
					Collection<EntityClass>... entitiesParams) {
				Collection<EntityClass> entities = entitiesParams[0];
				try {
					String typeName = entities.iterator().next().getTypeName();

					HttpClient client = new DefaultHttpClient();
					HttpPost request = new HttpPost();
					request.setURI(new URI(baseUrl + "Binary/saveEntities"));

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(baos);
					dos.writeUTF(typeName);

					for (EntityClass e : entities) {
						dos.writeLong(e.getId());
						((EntityBinary) e).serializeBinary(dos);
						publishProgress(e);
					}
					dos.writeLong(0);

					HttpEntity postBody = new ByteArrayEntity(
							baos.toByteArray());
					request.setEntity(postBody);

					client.execute(request);
				} catch (Exception e) {
					this.cancel(false);
					storedException = e;
					return null;
				}
				return entities;
			};
			
			protected void onPostExecute(java.util.Collection<EntityClass> result) {
				listener.onFinished(result);
			};
			
			protected void onProgressUpdate(EntityClass... entities)
			{
				for(EntityClass entity : entities)
					listener.onNewResult(entity);
			};
			
			protected void onCancelled(java.util.Collection<EntityClass> result) {
				listener.onError("Request was cancelled due to an exception.", storedException);
			};
		}.execute(entities);
		

	}

}
