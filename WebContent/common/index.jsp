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
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" />
</head>
<body>
	
	<jsp:include page="/include/header.jsp"/>

	<div class="page">
		<section>
		<!-- Slider container -->
			<div class="slideshow-container">
				<c:forEach var="p" items="${prodotti}" begin="0" end="2">
					<div class="mySlides">
					
						<img src="./getPicture?id1=${p.IDProdotto}&id2=${p.IDSpecifiche}" alt="prova" style="width: 55%">
						<div class="info__prodotto">
							<h1 style="margin: 20px 0 10px 0;">${p.nomeProdotto}</h1>
							<div class="descrizione__prod">
								<p>${p.descrizione}</p>
							</div>
							<h3 style="font-size: 18px;">${p.prezzo}</h3>
							<a href="" style="margin-top: 20px;"><button>Scopri
									di più</button></a>
						</div>
		
											
					</div>
				</c:forEach>
				<button class="slider-button w3-black slider-display-left" onclick="plusDivs(-1)">&#10094;</button>
				<button class="slider-button w3-black slider-display-right" onclick="plusDivs(1)">&#10095;</button>

			</div>
		</section>

		<div class="bottom-container">
			<h1>Apple</h1>
			
			<div class="product-cards-container">
				<c:forEach var="p" items="${prodotti}" begin="0" end="2">
					<div class="product-card">
						<img src="./getPicture?id1=${p.IDProdotto}&id2=${p.IDSpecifiche}" alt="prova" style="width: 100%">
						<div class="product-details">
							<h2>${p.nomeProdotto}</h2>
							<p>${p.descrizione}</p>
							<div class="product-price-buy">
								<h3>&euro;${p.prezzo}</h3>
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