<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.bean.*, model.*, model.dao.*, controller.*, utils.*"%>

<%
	DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
	UtenteDAO utenteDAO = new UtenteDAO(dm);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/responsive/header-responsive.css" />
</head>
<body>
<div class="header">
		<div class="logo">
			<img src="${pageContext.request.contextPath}/img/logo/TechITEase.svg"
				alt="LOGO">
		</div>
		<div class="menu">
			<ul>
				<li><a
					href="${pageContext.request.contextPath}/common/index.jsp">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/Catalogo">Prodotti</a></li>
				<li><a href="HomeSelection?action=aboutus">Chi siamo</a></li>
			</ul>
		</div>

		<div class="search">
			<input type="text" placeholder="Search">
		</div>
		
		<div class="account">

			<%
			UtenteBean utente = (UtenteBean) session.getAttribute("utente");
			if (utente != null) {
			%>
			<form id="datiPersonaliUtente" method="post" action="${pageContext.request.contextPath}/common/datiUtente.jsp">
				<input type="hidden" name="nome" value="${utente.nome}">
				<input type="hidden" name="cognome" value="${utente.cognome}">
				<input type="hidden" name="emailUtente" value="${utente.emailUtente}">
				<input type="hidden" name="telefono" value="${utente.telefono}">
				<input type="hidden" name="password" value="${utente.password}">
				
				<button class="dropbtn">${utente.nome}<img
					src="${pageContext.request.contextPath}/img/icons/user.svg" style="padding-left: 10px;"
					alt="Non disponibile">
			</button>
			</form>

			<a
				href="${pageContext.request.contextPath}/common/AutenticationControl?action=logout"><button>LogOut</button></a>
			<%
			} else {
			%>

			<a href="${pageContext.request.contextPath}/common/login.jsp" style="text-decoration: none;"> <img
				src="${pageContext.request.contextPath}/img/icons/login.png" alt="login" style="width: 25px; margin-top: 5px;">
			</a> 
			<a href="${pageContext.request.contextPath}/common/signup.jsp">
				<img src="${pageContext.request.contextPath}/img/icons/signup.png" alt="signup" style="width: 25px;margin-top: 5px;">
			</a>

			<%}%>
			<% 
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart != null && cart.getItems().isEmpty()) {
			%>
			<a href="${pageContext.request.contextPath}/common/cart.jsp"> <img id="cart" src="${pageContext.request.contextPath}/img/icons/cart.png" alt="cart"
				style="width: 25px;">
			</a>
			<%} else {%>
		
			<a href="${pageContext.request.contextPath}/common/cart.jsp"> <img id="cart" src="${pageContext.request.contextPath}/img/icons/cart-pieno.png" alt="cart"
				style="width: 25px;">
			</a>
			<%} %>
		

		</div>
		
	</div>

	<!-- Header mobile -->
	<div class="logo-mobile">
		<img src="${pageContext.request.contextPath}/img/logo/TechITEase.svg" alt="LOGO">
	</div>
	<div class="header-mobile">
		<a href="${pageContext.request.contextPath}/common/index.jsp"> <img
			src="${pageContext.request.contextPath}/img/icons/homeChiaro.png" alt="home" style="width: 25px;">
		</a> <a href="${pageContext.request.contextPath}/Catalogo"> <img
			src="${pageContext.request.contextPath}/img/icons/prodottiChiaro.png" alt="catalogo" style="width: 25px;">
		</a> 
		<% 
		if (cart!=null && cart.getItems().isEmpty()) {
		%>
		<a href="${pageContext.request.contextPath}/common/cart.jsp"> <img id="cart" src="${pageContext.request.contextPath}/img/icons/cartChiaro.png" alt="cart"
			style="width: 25px; margin: 0;">
		</a>
		<%} else {%>
	
		<a href="${pageContext.request.contextPath}/common/cart.jsp"> <img id="cart" src="${pageContext.request.contextPath}/img/icons/cart-pieno-white.png" alt="cart"
			style="width: 25px;">
		</a>
		<%} %>
		
		<%
		if (utente != null) {
		%>
		<a
			href="${pageContext.request.contextPath}/common/AutenticationControl?action=logout">
			<img src="${pageContext.request.contextPath}/img/icons/logout.png" alt="logout" style="width: 25px;">
		</a>
		<%
		} else {
		%>
		<a href="${pageContext.request.contextPath}/common/login.jsp"> <img
			src="${pageContext.request.contextPath}/img/icons/login.png" alt="login" style="width: 25px;">
		</a> <a href="${pageContext.request.contextPath}/common/signup.jsp">
			<img src="${pageContext.request.contextPath}/img/icons/signup.png" alt="signup" style="width: 25px;">
		</a>
		<%
		}
		%>

	</div>
</body>
</html>