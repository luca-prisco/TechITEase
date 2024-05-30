<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<%@ page import="java.util.*" %>
<%@ page import="model.bean.*" %>

<%
Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("prodotti");
if(prodotti == null) 
	response.sendRedirect(request.getContextPath() + "/ProdottoControl");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Tech IT Ease - Prodotti</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/gestioneProdotti.css" />
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
		<div class="row-container">
			<a href="${pageContext.request.contextPath}/admin/aggiungiProdotto.jsp"><button>Aggiungi prodotto</button></a> 
			<input id="myInput" type="text" placeholder="Search..">
			<div class="filters">
				<a href=""><button>Brand</button></a> 
				<a href=""><button>Categoria</button></a>
			</div>
		</div>
		<div class="prodotti__storage">
			<c:choose>
				<c:when test="${prodotti.size() eq 0}">
					<h1>Nessun prodotto disponibile</h1>
				</c:when>
				<c:otherwise>
					<table>
						<thead>
							<tr>
								<th>Nome</th>
								<th>Id Prodotto</th>
								<th>Memoria HD</th>
								<th>Memoria RAM</th>
								<th>Colore</th>
								<th>Immagine</th>
								<th>Azioni</th>
							</tr>
						</thead>
						<tbody id="myTable">
							<c:forEach var="prodotto" items="${prodotti}">
								<c:forEach var="specifiche" items="${prodotto.specifiche}">
									<tr>
										<td>${prodotto.nomeProdotto}</td>
										<td>${prodotto.IDProdotto}</td>
										<td>${specifiche.hdd}</td>
										<td>${specifiche.ram}</td>
										<td>${specifiche.colore}</td>
										<td>
											<c:choose>
												<c:when test="${empty specifiche.image}">
													<form id="uploadPhoto" action="./UploadPhoto" enctype="multipart/form-data" method="post">
														<input type="hidden" name="id1" value="${prodotto.IDProdotto}">
														<input type="hidden" name="id2" value="${specifiche.IDSpecifiche}">
														<input id="fileInput${specifiche.IDSpecifiche}" class="file" type="file" name="talkPhoto" maxlength="255" style="display: none;">
														<label for="fileInput${specifiche.IDSpecifiche}" style="cursor: pointer;"> 
															<img src="${pageContext.request.contextPath}/img/icons/addImage.png" style="width:25px; margin: 10px 0 10px 25px;">
														</label>
													</form>
												</c:when>
												<c:otherwise>
													<img src="./getPicture?id1=${prodotto.IDProdotto}&id2=${specifiche.IDSpecifiche}"
														alt="Immagine" style="width: 70px;">
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<form method="get" action="GestioneCatalogo?action=updateProduct">
												<input type="hidden" name="id" value="${prodotto.IDProdotto}">
												<button type="submit">Edit</button>
											</form>
										</td>
										<td>
											<form id="deleteForm" method="get" action="ProdottoControl">
												<input type="hidden" name="action" value="delete">
												<input type="hidden" name="del1" value="${prodotto.IDProdotto}">
												<input type="hidden" name="del2" value="${specifiche.IDSpecifiche}">
												<label for="deleteSubmit${specifiche.IDSpecifiche}" style="cursor: pointer;"> 
													<img src="${pageContext.request.contextPath}/img/icons/deleteIcon.png" style="width:25px; margin: 10px 0 10px 25px;">
												</label>
											</form>
										</td>
									</tr>
									<script>
										document.getElementById('fileInput').addEventListener('change', function() {
											document.getElementById('uploadForm${specifiche.IDSpecifiche}').submit();
										});
										document.getElementById('deleteSubmit').addEventListener('click', function() {
											document.getElementById('deleteForm').submit();
										});
									</script>
								</c:forEach>
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
