<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />

<script src="${pageContext.request.contextPath}/scripts/validate.js"></script>
</head>
<body>
	<jsp:include page="/include/header.jsp"/>

	<div class="content">
		<div class="login-form">
			<h1>Login</h1><br>
			<form id="formLogin" method="post" action="${pageContext.request.contextPath}/common/AutenticationControl?action=login">
				<label for="emailUtente">Email</label>
				<input id="emailUtente" type="text" name="emailUtente" onchange="validateFormElem(this, emailPattern, document.getElementById('errorEmail'), emailErrorMessage)"><span
					id="errorEmail"></span><br>
				<span></span>
					
				<label for="password">Password</label>
				<input id="password" type="password" name="password" placeholder="enter password" onchange="validateFormElem(this, passwordPattern, document.getElementById('errorPassword'), passwordErrorMessage)"><span
					id="errorPassword"></span><br>
					<span></span>
				
				<c:if test="${not empty errors}">
					<c:forEach var="error" items="${errors}">
						<span style="color: red;font-size:13px;">${error}</span>
					</c:forEach>
				</c:if>
				<input id="login" type="submit" value="Login">	
			</form>
		</div>
	</div>

</body>
</html>