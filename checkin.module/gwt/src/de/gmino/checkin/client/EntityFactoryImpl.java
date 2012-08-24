package de.gmino.checkin.client;

import java.util.ArrayList;
import java.util.Collection;

import de.gmino.checkin.client.domain.Checkin;
import de.gmino.checkin.client.domain.Consumer;
import de.gmino.checkin.client.domain.Coupon;
import de.gmino.checkin.client.domain.CouponOwenership;
import de.gmino.checkin.client.domain.Shop;
import de.gmino.checkin.client.domain.ShopAdmin;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactoryInterface;

public class EntityFactoryImpl implements EntityFactoryInterface {

	
	@Override
	public Collection<Entity> createEntityObjects(String typeName,
			Collection<Long> ids) {
		Collection<Entity> ret = new ArrayList<Entity>(ids.size());
		for(long id:ids)
		{
			ret.add(createEntityObject(typeName, id));
		}
		return ret;
	}

	// TODO autocreate this class, at least the method below

	@Override
	public Entity createEntityObject(String typeName, long id) {
		if(typeName.equals("Shop"))
			return new Shop(id);
		if(typeName.equals("Coupon"))
			return new Coupon(id);
		if(typeName.equals("Checkin"))
			return new Checkin(id);
		if(typeName.equals("CouponOwenership"))
			return new CouponOwenership(id);
		if(typeName.equals("ShopAdmin"))
			return new ShopAdmin(id);
		if(typeName.equals("Consumer"))
			return new Consumer(id);
		throw new RuntimeException("Unsupported Entity type: " + typeName);
	}

}
