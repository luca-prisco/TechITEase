<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.bean.*"%>

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
				<li><a href="${pageContext.request.contextPath}/Catalogo">Catalogo</a></li>
				<li><a href="HomeSelection?action=aboutus">Chi siamo</a></li>
			</ul>
		</div>

		<a href=""><img id="cart" src="img/icons/cart.png" alt="cart"
			style="width: 25px;"></a>

		<div class="account">

			<%
			UtenteBean utente = (UtenteBean) session.getAttribute("utente");
			if (utente != null) {
			%>
			<button class="dropbtn" data-dropdown-button>${utente.nome}<img
					src="img/icons/user.svg" style="padding-left: 10px;"
					alt="Non disponibile">
			</button>
			<a
				href="${pageContext.request.contextPath}/common/AutenticationControl?action=logout"><button>LogOut</button></a>
			<%
			} else {
			%>

			<a href="${pageContext.request.contextPath}/common/login.jsp"
				style="text-decoration: none;">
				<button id="login-button">LogIn</button>
			</a> <a href="${pageContext.request.contextPath}/common/signup.jsp">
				<button id="signup-button">SignUp</button>
			</a>

			<%}%>

		</div>

		<div class="search">
			<input type="text" placeholder="Search">
		</div>
	</div>

	<!-- Header mobile -->
	<div class="logo-mobile">
		<img src="${pageContext.request.contextPath}/img/logo/TechITEase.svg" alt="LOGO">
	</div>
	<div class="header-mobile">
		<a href="${pageContext.request.contextPath}/common/index.jsp"> <img
			src="img/icons/homeChiaro.png" alt="home" style="width: 25px;">
		</a> <a href="${pageContext.request.contextPath}/Catalogo"> <img
			src="img/icons/prodottiChiaro.png" alt="catalogo" style="width: 25px;">
		</a> 
		<a href="#"> <img id="cart" src="img/icons/cartChiaro.png" alt="cart"
			style="width: 25px;">
		</a>

		<%
		if (utente != null) {
		%>
		<a
			href="${pageContext.request.contextPath}/common/AutenticationControl?action=logout">
			<img src="img/icons/logout.png" alt="logout" style="width: 25px;">
		</a>
		<%
		} else {
		%>
		<a href="${pageContext.request.contextPath}/common/login.jsp"> <img
			src="img/icons/login.png" alt="login" style="width: 25px;">
		</a> <a href="${pageContext.request.contextPath}/common/signup.jsp">
			<img src="img/icons/signup.png" alt="signup" style="width: 25px;">
		</a>
		<%
		}
		%>

	</div>
</body>
</html>