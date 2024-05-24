<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="model.*" %>
	
<%
	Collection<?> prodotti = (Collection<?>) request.getAttribute("prodotti");
	if(prodotti == null) {
		response.sendRedirect(request.getContextPath() + "/Home");	
		return;
	}
	
	Cart cart = (Cart) request.getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, width=device-width">
<title>Tech IT Ease</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/responsive/index-responsive.css" />

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" />
</head>
<body>
	
	<jsp:include page="/include/header.jsp"/>

	<div class="page">
		<!-- Slider container -->
			<div class="slideshow-container">
				<c:forEach var="p" items="${prodotti}" begin="0" end="2">
					<div class="mySlides fade">
						<div class="prod__image" style="width: 58%">
							<img src="./getPicture?id1=${p.IDProdotto}&id2=${p.specifiche[0].IDSpecifiche}"
								alt="${p.nomeProdotto}" style="width: 100%">
						</div>
						<div class="info__prodotto">
							<div class="descrizione__prod" style="width: 90%;">
								<h1>${p.nomeProdotto}</h1>
								<p style="text-align: justify; line-height: 1.5;">${p.dettagli}</p>
							</div>
							<div class="descrizione__prod" style="width: 90%;">
								<h3>&euro;${p.specifiche[0].prezzo}</h3>
								<a href=""><button>Scopri di più</button></a>
							</div>
						</div>
					</div>
				</c:forEach>
				<button class="slider-button slider-display-left"
					onclick="plusDivs(-1)">&#10094;</button>
				<button class="slider-button slider-display-right"
					onclick="plusDivs(1)">&#10095;</button>
			</div>
		

		<div class="bottom-container">
			<h1>Apple</h1>

			<div class="product-cards-container">
				<c:forEach var="p" items="${prodotti}" begin="0" end="2">
					<div class="product-card">
						<img src="./getPicture?id1=${p.IDProdotto}&id2=${p.specifiche[0].IDSpecifiche}"
							alt="prova" style="width: 100%">
						<div class="product-details">
							<h2>${p.nomeProdotto}</h2>
							<p>${p.descrizione}</p>
							<div class="product-price-buy">
								<h3>&euro;${p.specifiche[0].prezzo}</h3>
								<a href=""><button>Acquista</button></a>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<script src="scripts/slider.js"></script>
</body>
</html>