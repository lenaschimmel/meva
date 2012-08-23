package de.gmino.checkin.client;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.connectors.GetCallback;
import org.itemscript.core.gwt.GwtSystem;
import org.itemscript.core.values.JsonValue;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.EntityRequestInterface;

public class EntityRequestJson implements EntityRequestInterface {

	String baseUrl;

	public EntityRequestJson(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public void loadEntities(final Collection<? extends Entity> c) {
		if (c.isEmpty())
			return;
		try {
			JsonSystem system = GwtSystem.SYSTEM;

			system.get(baseUrl + "Json/getEntities", new GetCallback() {
				@Override
				public void onSuccess(JsonValue value) {
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
				}

				@Override
				public void onError(Throwable e) {
					throw new RuntimeException(e);
				}
			});

			

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
		throw new RuntimeException("Not yet implemented!");
	}

	@Override
	public void saveEntity(Entity e) {
		Collection<Entity> c = new LinkedList<Entity>();
		c.add(e);
		saveEntities(c);
	}

	@Override
	public void saveEntities(Collection<? extends Entity> c) {
		throw new RuntimeException("Not yet implemented!");
	}

}
