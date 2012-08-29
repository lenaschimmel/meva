package de.gmino.checkin.taglib;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import de.gmino.checkin.server.EntityFactoryImpl;
import de.gmino.checkin.server.domain.Shop;
import de.gmino.checkin.server.request.LocalRequetsImpl;
import de.gmino.checkin.server.request.NetworkRequestsImplAsyncLocalSql;
import de.gmino.checkin.server.request.QueryShopByCode;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.request.Requests;

public class ShopTag extends SimpleTagSupport {
	private String varname;
	private String scanCode;
	
	@Override
	public void doTag() throws JspException, IOException {
		System.out.println("Do all the tags! scanCode: " + scanCode);
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Requests.setImplementation(new NetworkRequestsImplAsyncLocalSql());

		Query q = new QueryShopByCode(scanCode); //req.getParameter("scanCode"));
		Shop shop = (Shop) LocalRequetsImpl.getLoadedEntitiesByQuery(Shop.type, q).iterator().next();
		LocalRequetsImpl.loadEntities(shop.getCoupons());
		System.out.println("Loaded " + shop.getTitle());
		getJspContext().setAttribute(varname, shop);
	}
	
	public void setVarname(String varname) {
		this.varname = varname;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}
}
