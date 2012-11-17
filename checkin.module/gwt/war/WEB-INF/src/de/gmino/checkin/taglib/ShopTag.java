package de.gmino.checkin.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import de.gmino.checkin.server.EntityFactoryImpl;
import de.gmino.checkin.server.domain.Shop;
import de.gmino.checkin.server.request.LocalRequetsImpl;
import de.gmino.checkin.server.request.NetworkRequestsImplAsyncLocalSql;
import de.gmino.checkin.server.request.QueryShopByCode;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.request.Requests;

public class ShopTag extends SimpleTagSupport {
	private String varname;
	private String scanCode;

	@Override
	public void doTag() throws JspException, IOException {
		// FIXME Don't create multiple instances!
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Requests.setImplementation(new NetworkRequestsImplAsyncLocalSql());

		EntityQuery q = new QueryShopByCode(scanCode);
		Shop shop = (Shop) LocalRequetsImpl.getLoadedEntitiesByQuery(Shop.type, q).iterator().next();
		LocalRequetsImpl.loadEntities(shop.getCoupons());
		getJspContext().setAttribute(varname, shop);
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}
}
