<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.bean.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>
	<div class="header">
		<div class="logo">
			<img src="${pageContext.request.contextPath}/img/logo/TechITEase.svg" alt="LOGO">
		</div>
		<div class="menu">
			<ul>
				<li><a
					href="${pageContext.request.contextPath}/common/index.jsp">Home</a></li>
				<li><a
					href="${pageContext.request.contextPath}/Catalogo">Catalogo</a></li>
				<li><a href="HomeSelection?action=aboutus">Chi siamo</a></li>
			</ul>
		</div>

		<a href=""><img id="cart"src="img/icons/cart.png" alt="cart" style="width:25px;"></a>

		<div class="account">

			<% 
			UtenteBean utente = (UtenteBean) session.getAttribute("utente");
			if(utente != null) {%>
				<a href=""><button>${utente.nome}</button></a> 
				<a href="${pageContext.request.contextPath}/common/AutenticationControl?action=logout"><button>LogOut</button></a> 
			<%
			} else {
			%>
		
				<a style="text-decoration: none;" href="${pageContext.request.contextPath}/common/login.jsp">
					<button id="login-button">LogIn</button>
				</a>
				<a href="${pageContext.request.contextPath}/common/signup.jsp">
					<button id="signup-button">SignUp</button>
				</a>
				
			<%}%>
	
		</div>
		
		<div class="search">
			<input type="text" placeholder="Search">
		</div>
	</div>
</body>
</html>