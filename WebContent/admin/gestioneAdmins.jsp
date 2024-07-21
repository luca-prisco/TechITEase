<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="model.bean.*" %>
    
<%
	Collection<UtenteBean> admins = (Collection<UtenteBean>) request.getAttribute("admins");
	if (admins == null || admins.isEmpty()) {
	    response.sendRedirect(request.getContextPath() + "/UtenteControl?action=getAdmins");
	    return;
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, width=device-width">
<title>Take IT Ease</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/gestioneAdmins.css" />

</head>
<body>
	<jsp:include page="/include/headerAdmin.jsp"/>
	<div class="content">
		<div class="row-container">
			<h1>${utente.nome}</h1>
			<p id="datetime" style="margin: 30px;">
				<%= new java.util.Date() %></p>
		</div>
		<div class="add-admin">
			<a href="${pageContext.request.contextPath}/admin/registrazioneAdmin.jsp"><button>Aggiungi admin</button></a> 
		</div>

		<div class="admins__storage">
			<c:choose>
				<c:when test="${admins.size() eq 0}">
					<h1>Nessun admin</h1>
				</c:when>
				<c:otherwise>
					<table class="table-adattabile">
						<thead>
							<tr>
								<th>Email admin</th>
								<th>Nome</th>
								<th>Cognome</th>
								<th>Telefono</th>
							</tr>
						</thead>
						<tbody id="myTable">
							<c:forEach var="admin" items="${admins}">
								<tr>
									<td class="emailAdmin">${admin.emailUtente}</td>
									<td>${admin.nome}</td>
									<td>${admin.cognome}</td>
									<td>${admin.telefono}</td>
									<td id="azioni">
										<form id="deleteForm" method="get" action="UtenteControl">
											<input type="hidden" name="action" value="deleteAdmin">
											<input type="hidden" name="emailAdmin" value="${admin.emailUtente}">
											<button type="submit"><u>Rimuovi</u></button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</body>
</html>