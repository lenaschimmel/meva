package de.gmino.issuemap.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = -6195800215354851852L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		URL url = new URL( "http://gmino.de/products/issuemap/upload.php" );
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod( "POST" );
		connection.setDoInput( true );
		connection.setDoOutput( true );
		connection.setUseCaches( false );
		connection.setRequestProperty( "Content-Type", req.getHeader("Content-Type"));
		System.out.println(req.getHeader("Content-Type"));
		connection.setRequestProperty( "Content-Length", String.valueOf(req.getContentLength()));

		transferAll(req.getInputStream(), connection.getOutputStream());
		transferAll(connection.getInputStream(), resp.getOutputStream());
	}

	private void transferAll(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len != -1) {
		    out.write(buffer, 0, len);
		    len = in.read(buffer);
		}
		out.flush();
		out.close();
		in.close();
	}
}
