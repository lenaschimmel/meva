package de.gmino.issuemap.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.gmino.geobase.shared.domain.Address;
import de.gmino.issuemap.server.domain.Map;
import de.gmino.meva.server.SqlHelper;
import de.gmino.meva.server.request.NetworkRequestsImplAsyncLocalSql;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityFactoryInterface;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CsvExportServlet extends HttpServlet {

	private static final long serialVersionUID = 6373640218753449746L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			String entityFactoryClass = getServletContext().getInitParameter("entityFactoryClass");
			EntityFactoryInterface factory = (EntityFactoryInterface)Class.forName(entityFactoryClass).newInstance();
			EntityFactory.setImplementations(factory);
			Requests.setImplementation(new NetworkRequestsImplAsyncLocalSql());
			String sqlConnectionUrl = getServletContext().getInitParameter("sqlConnectionUrl");
			SqlHelper.setConnectionUrl(sqlConnectionUrl);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		resp.setContentType("text/comma-separated-values");
		resp.setCharacterEncoding("ISO-8859-1");
		resp.setHeader("Content-Disposition", "attachment; filename=\"geoengine-export.csv\"");
		
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "ISO-8859-1"));
		writer.println("id;Ansprache;Name;Adresszusatz;Straße+Hausnummer;PLZ;Stadt;Ansprechpartner;email;URL;URL_Impressum;Titel;Imfotext;Ansprechpartner_gmino;url_website");

		Requests.getLoadedEntitiesByType(Map.type, new RequestListener<Map>() {
			@Override
			public void onNewResult(Map map) {
				final Address address = map.getPostal_address();
				writeColumn(		map.getId()+""										, false);
				writeColumn(		address.getRecipientName()							, true);
				writeColumn(		address.getRecipientName()							, true);
				writeColumn(		address.getAdditionalAddressLine()					, true);
				if(address.getStreet() != null && address.getHouseNumber() != null)
					writeColumn(		address.getStreet() + " " + address.getHouseNumber(), true);
				else
					writeColumn(""														, true);
				writeColumn(		address.getZip()									, true);
				writeColumn(		address.getCity()									, true);
				writeColumn(		address.getRecipientName()							, true);
				writeColumn(		map.getEmail()										, true);
				writeColumn(		map.getSubdomain()+".geoengine.de"					, true);
				writeColumn(		map.getImpressum_url()								, true);
				writeColumn(		map.getTitle()										, true);
				writeColumn(		map.getInfoText().replaceAll("\n", " ")				, true);
				writeColumn(		"Anton Tranelis"									, true);
				writeColumn(		map.getWebsite()									, true);
				writer.println();
			}

			private void writeColumn(String s, boolean b) {
				if(b)
					writer.write("; ");
				if(s == null)
					s = "";
				writer.write(s);
			//	writer.write("\"" + s + "\"");
			}
		});
		
		writer.close();
	}

}
