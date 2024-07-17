<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<%@ page import="java.util.*" %>
<%@ page import="model.bean.*" %>

<%
Collection<ProdottoBean> ordini = (Collection<ProdottoBean>) request.getAttribute("ordini");
if (ordini == null || ordini.isEmpty()) {
    response.sendRedirect(request.getContextPath() + "/OrdineControl");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Tech IT Ease</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/gestioneOrdini.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="/include/headerAdmin.jsp"/>
	<div class="content">
		<div class="row-container">
			<h1>Luca Prisco</h1>
			<p id="datetime" style="margin: 30px;">
				<%= new java.util.Date() %></p>
		</div>
		<div class="ordini__storage">
			<c:choose>
				<c:when test="${ordini.size() eq 0}">
					<h1>Nessun ordine</h1>
				</c:when>
				<c:otherwise>
					<table>
						<thead>
							<tr>
								<th>ID Ordine</th>
								<th>Email Cliente</th>
								<th>Data ordine</th>
								<th>Risolto</th>
							</tr>
						</thead>
						<tbody id="myTable">
							<c:forEach var="ordine" items="${ordini}">
								<tr>
									<td>${ordine.IDOrdine}</td>
									<td>${ordine.emailUtente}</td>
									<td>${ordine.dataOrdine}</td>
									<td>${ordine.isResolved}</td>
									<td id="azioni">
										<form method="get"
											action="${pageContext.request.contextPath}/admin/dettagliOrdine.jsp">
											<input type="hidden" name="idOrdine" value="${ordine.IDOrdine}">
										
											<button type="submit">Mostra dettagli</button>
										</form>
									</td>

								</tr>
								<script>
									document
											.getElementById('fileInput')
											.addEventListener(
													'change',
													function() {
														document
																.getElementById(
																		'uploadPhoto')
																.submit();
													});
									document
											.getElementById('deleteSubmit')
											.addEventListener(
													'click',
													function() {
														document
																.getElementById(
																		'deleteForm')
																.submit();
													});
								</script>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
<script>
	$(document).ready(function() {
		$("#myInput").on("keyup", function() {
			var value = $(this).val().toLowerCase();
			$("#myTable tr").filter(function() {
				$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
			});
		});
	});
</script>
</html>
