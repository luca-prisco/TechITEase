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
		
		<div class="filtri">	
			<form class="data-filter" method="post" action="${pageContext.request.contextPath}/OrdineControl?action=filtraData">						
				<input type="date" name="dataX">
				<input type="date" name="dataY">
				<button type="submit">Filtra</button>
			</form>
			<form class="utente-filter" method="post" action="${pageContext.request.contextPath}/OrdineControl?action=filtraUtente">						
				<input type="text" name="emailUtente" placeholder="mariorossi@gmail.com">
				<button type="submit">Filtra</button>
			</form>
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
								<th>Prezzo totale</th>
								<th>Risolto</th>
							</tr>
						</thead>
						<tbody id="myTable">
							<c:forEach var="ordine" items="${ordini}">
								<tr>
									<td>${ordine.IDOrdine}</td>
									<td>${ordine.emailUtente}</td>
									<td>${ordine.dataOrdine}</td>
									<td>${ordine.prezzoTotale}€</td>
									<td>${ordine.isResolved}</td>
									<td id="azioni">
										<form method="post" action="${pageContext.request.contextPath}/OrdineControl?action=dettagli">
											<input type="hidden" name="idOrdine" value="${ordine.IDOrdine}">
											<input type="hidden" name="email" value="${ordine.emailUtente}">
											<input type="hidden" name="total" value="${ordine.prezzoTotale}">
											<input type="hidden" name="via" value="${ordine.via}">
											<input type="hidden" name="civico" value="${ordine.civico}">
											<input type="hidden" name="cap" value="${ordine.cap}">
											<input type="hidden" name="citta" value="${ordine.citta}">
											<button type="submit"><u>Mostra dettagli</u></button>
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
