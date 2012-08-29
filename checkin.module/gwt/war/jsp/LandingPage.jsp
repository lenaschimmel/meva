<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns="http://www.w3.org/1999/xhtml" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ci="urn:jsptld:/WEB-INF/ci.tld"  xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0">
<jsp:output doctype-root-element="html" doctype-public="-//W3C//DTD XHTML 1.1//EN" doctype-system="http://www.w3c.org/TR/xhtml11/DTD/xhtml11.dtd" />
<jsp:directive.page contentType="text/html; charset=utf-8" language="java" />
<jsp:directive.page import="de.gmino.checkin.server.domain.Shop" />

<ci:shop varname="myShop" scanCode="${param.scanCode}"/>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>JSP 2.0 Test</title>
	</head>
	<body>
	  
		<h1>Wilkommen bei ${myShop.title}!</h1>
		
		${myShop.description}<br/>
		<br/>
		<h2>Aktuelle Coupons:</h2>
		<ul>
		<c:forEach var="coupon" items="${myShop.coupons}">
	      <li>${coupon.title}</li>
        </c:forEach>
		</ul>
		<a href="http://www.facebook.com/pages/pages/${myShop.facebookId}">Besuche ${myShop.title} auf Facebook!</a>
	</body>
</html>
</jsp:root>