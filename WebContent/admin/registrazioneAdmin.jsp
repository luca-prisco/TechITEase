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

<script src="${pageContext.request.contextPath}/scripts/validate.js"></script>
</head>
<body>
	<%@ include file="/include/header.jsp"%>

	<div class="content">
		<div class="signup-form">
			<h1>Registrazione Admin</h1><br>
			<form id="formSignup" method="post" action="${pageContext.request.contextPath}/common/AutenticationControl?action=regAdmin">
				<label for="nome">Nome</label>
				<input id="nome "type="text" name="nome" placeholder="Mario" onchange="validateFormElem(this, nameOrLastnamePattern, document.getElementById('errorName'), nameErrorMessage)">
				<span id="errorName"></span><br>
				<span></span>
					
				<label for="cognome">Cognome</label>
				<input id="cognome" type="text" name="cognome" onchange="validateFormElem(this, nameOrLastnamePattern, document.getElementById('errorLastname'), lastnameErrorMessage)">
				<span id="errorLastname"></span><br>
				<span></span>
					
				<label for="emailUtente">Email</label>
				<input id="emailUtente" type="text" name="emailUtente" onchange="validateFormElem(this, emailPattern, document.getElementById('errorEmail'), emailErrorMessage)">
				<span id="errorEmail"></span><br>
				<span></span>
				
				<label for="telefono">Numero di telefono</label>
				<input id="telefono" type="text" name="telefono" onchange="validateFormElem(this, phonePattern, document.getElementById('errorPhone'), phoneErrorMessage)">
				<span id="errorPhone"></span><br>
				<span></span>
					
				<label for="password">Password</label>
				<input id="password" type="password" name="password" placeholder="enter password" onchange="validateFormElem(this, passwordPattern, document.getElementById('errorPassword'), passwordErrorMessage)">
				<span id="errorPassword"></span><br>
				<span></span>
				
				<input id="registrati" type="submit" value="Registra">
				
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