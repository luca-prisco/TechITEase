<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/headerAdmin.css" />
</head>
<body>
	<div class="header">
		<div class="logo">
			<img src="${pageContext.request.contextPath}/img/logo/TechITEase.svg" alt="LOGO">
		</div>
		<div class="menu">
			<ul>
				<li><a href="${pageContext.request.contextPath}/admin/dashboard.jsp">Dashboard</a></li>
				<li><a href="${pageContext.request.contextPath}/admin/gestioneProdotti.jsp">Prodotti</a></li>
				<li><a href="">Ordini</a></li>
				<li><a href="">Admins</a></li>
				<c:choose>
					<c:when test="${not empty sessionScope.utente}">
						<li><a href="${pageContext.request.contextPath}/common/AutenticationControl?action=logout">Logout</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="">LogIn</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>	
</body>
</html>