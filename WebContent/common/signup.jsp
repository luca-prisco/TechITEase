<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SignUp</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/signup.css" />
</head>
<body>
	<%@ include file="/include/header.jsp"%>
	<script src="scripts/validateSignup.js"></script>

	<div class="content">
		<div class="signup-form">
			<h1>Registrazione</h1><br>
			<form id="formSignup" method="post" action="${pageContext.request.contextPath}/common/AutenticationControl?action=signup">
				<label for="nome">Nome</label>
				<input id="nome "type="text" name="nome" placeholder="Mario" onchange="validateFormElem(this, nameOrLastnamePattern, document.getElementById('errorName'), nameErrorMessage)">
				<span id="errorName"></span><br>
					
				<label for="cognome">Cognome</label>
				<input id="cognome" type="text" name="cognome" onchange="validateFormElem(this, nameOrLastnamePattern, document.getElementById('errorLastname'), lastnameErrorMessage)">
				<span id="errorLastname"></span><br>
					
				<label for="emailUtente">Email</label>
				<input id="emailUtente" type="text" name="emailUtente"><br>
				
				<label for="telefono">Numero di telefono</label>
				<input id="telefono" type="text" name="telefono" onchange="validateFormElem(this, phonePattern, document.getElementById('errorPhone'), phoneErrorMessage)">
				<span id="errorPhone"></span><br>
					
				<label for="password">Password</label>
				<input id="password" type="password" name="password" placeholder="enter password" onchange="validateFormElem(this, )"><br>
				
				<input id="registrati" type="submit" value="Registrati">
				
				<c:if test="${not empty errors}">
					<c:forEach var="error" items="${errors}">
						<span style="color: red;font-size:13px;">${error}</span>
					</c:forEach>
				</c:if>	
			</form>
		</div>
	</div>

</body>
</html>