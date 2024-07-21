<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String nome = request.getParameter("nome");
    String cognome = request.getParameter("cognome");
    String email = request.getParameter("emailUtente");
    String telefono = request.getParameter("telefono");
    String password = request.getParameter("password");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, width=device-width">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datiUtente.css" />

<title>Take IT Ease</title>
</head>
<body>
	<jsp:include page="/include/header.jsp"/>
	<div class="page">
		<form id="updateUtente" method="post" action="${pageContext.request.contextPath}/UtenteControl?action=update">
				<h1>Dati personali</h1>
				<div class="utente-form">
					<h3 style="margin-top: 0;"><%=nome %> <%=cognome %></h3>
					<div class="tableRow">
						<label for="nome"> Nome </label>
						<div>
							<input id="nome" type="text" name="nome"
								value="<%=nome%>">
						</div>
					</div>
					<div class="tableRow">
						<label for="cognome"> Cognome </label>
						<div>
							<input type="text" id="cognome" name="cognome" value="<%=cognome%>">
						</div>
					</div>
					<div class="tableRow">
						<label for="email"> Email </label>
						<div>
							<input type="text" id="email" name="email"
								value="<%=email%>">
						</div>
					</div>
					<div class="tableRow">
						<label for="telefono"> Telefono </label>
						<div>
							<input type="text" id="telefono" name="telefono"
								value="<%=telefono%>">
						</div>
					</div>
					<div class="tableRow">
						<label></label>
						<div class="tableRow-sumbit">
							<input type="hidden" name="emailOld" value="<%= email %>">
							<input class="invia" type="submit" value="Modifica dati">
						</div>
					</div>
			</div>
		</form>
		<form id="updatePassword" method="post" action="${pageContext.request.contextPath}/UtenteControl?action=updateP">
			<div class="password-form">
				<div class="tableRow">
					<label for="password"> Password </label>
					<div>
						<input type="password" id="password" name="password"
							placeholder="Nuova password"></input>
					</div>
				</div>
				<div class="tableRow">
					<label></label>
					<div class="tableRow-sumbit">
						<input type="hidden" name="email" value="<%=email%>"> 
						<input class="invia" type="submit" value="Modifica password">
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>