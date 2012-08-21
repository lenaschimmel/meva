package de.gmino.checkin.android;

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

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.EntityRequestInterface;

/**
 * This class is used by EntityFactory to perform actual requests to a server.
 * 
 * @author lena
 * 
 */
public class EntityRequestBinary implements EntityRequestInterface {

	String baseUrl;

	public EntityRequestBinary(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public void loadEntities(Collection<Entity> c) {
		if (c.isEmpty())
			return;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost();
			request.setURI(new URI(
					"http://192.168.178.64:8888/Binary/getEntities"));

			String typeName = c.iterator().next().getTypeName();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeUTF(typeName);
			for (Entity e : c)
				dos.writeLong(e.getId());
			dos.writeLong(0);

			HttpEntity postBody = new ByteArrayEntity(baos.toByteArray());
			request.setEntity(postBody);

			HttpResponse response = client.execute(request);
			DataInputStream dis = new DataInputStream(response.getEntity()
					.getContent());

			for (Entity e : c)
				((EntityBinary) e).deserializeBinary(dis);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void loadEntity(Entity e) {
		Collection<Entity> c = new LinkedList<Entity>();
		c.add(e);
		loadEntities(c);
	}

	@Override
	public Collection<Long> getNewEntities(String typeName, int count) {
		Collection<Long> ret = new ArrayList<Long>(count);
		if (count == 0)
			return ret;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost();
			request.setURI(new URI(
					"http://192.168.178.64:8888/Binary/newEntities"));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeUTF(typeName);
			dos.writeInt(count);

			HttpEntity postBody = new ByteArrayEntity(baos.toByteArray());
			request.setEntity(postBody);

			HttpResponse response = client.execute(request);
			DataInputStream dis = new DataInputStream(response.getEntity()
					.getContent());

			for (int i = 0; i < count; i++)
				ret.add(dis.readLong());
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
