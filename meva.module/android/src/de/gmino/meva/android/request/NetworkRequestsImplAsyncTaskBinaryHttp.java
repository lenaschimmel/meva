package de.gmino.meva.android.request;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

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

	public NetworkRequestsImplAsyncTaskBinaryHttp(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public void getIdsByQuery(Query query, RequestListener<Long> listener) {
		new AsyncTask<Query, Long, Collection<Long>>() {

			protected Collection<Long> doInBackground(Query... queryParams) {
				Query query = queryParams[0];
				Collection<Long> ids = new LinkedList<Long>();

				try {
					HttpClient client = new DefaultHttpClient();
					HttpPost request = new HttpPost();

					// TODO why is the actual connection within this Request
					// class, even though other request classes delegate that to
					// Impl classes?

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
					throw new RuntimeException(e);
				}
				return ids;
			};
		}.execute(query);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getNewIds(final EntityTypeName type, int count,
			RequestListener<Long> listener) {

		new AsyncTask<Integer, Long, Collection<Long>>() {
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
						ids.add(dis.readLong());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return ids;
			};
		}.execute(count);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <EntityClass extends Entity> void loadEntities(
			final Collection<EntityClass> entities,
			final RequestListener<EntityClass> listener) {

		new AsyncTask<Collection<EntityClass>, EntityClass, Collection<EntityClass>>() {
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
					throw new RuntimeException(e);
				}
				return entities;
			};
		}.execute(entities);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <EntityClass extends Entity> void saveEntities(
			Collection<EntityClass> entities,
			RequestListener<EntityClass> listener) {
		new AsyncTask<Collection<EntityClass>, EntityClass, Collection<EntityClass>>() {
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
					throw new RuntimeException(e);
				}
				return entities;
			};
		}.execute(entities);
	}

}
