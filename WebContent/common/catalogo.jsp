<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="model.dao.*" %>
<%@ page import="model.*" %>
	
<%
	Collection<?> prodotti = (Collection<?>) request.getAttribute("prodotti");
	if(prodotti == null) {
		response.sendRedirect(request.getContextPath() + "/Catalogo");	
		return;
	}
	
	Cart cart = (Cart) request.getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, width=device-width">
<title>Prodotti</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/catalogo.css" />
</head>
<body>
	<jsp:include page="/include/header.jsp"/>
	<div class="page">
		<h2 style="margin: 140px 0 0 100px;">Store</h2>
		<div class="catalogo">
			<aside class="filtri">
				<h3>Ordina per</h3> 
				<div class="prezzo" style="display: flex;justify-content: left;">
					<select name="sort" id="sort">
						<option value="crescente">Prezzo crescente</option>
						<option value="decrescente">Prezzo decrescente</option>
						<option value="bestseller">Bestseller</option>
					</select> 
				</div>
				<h3>Prezzo</h3> 
				<div class="prezzo" style="display: flex;justify-content: space-evenly;">     
			        <input type="text" id="minPrice" name="minPrice" min="0" placeholder="Min(€)">
			        <input type="text" id="maxPrice" name="maxPrice" min="0" placeholder="Max(€)">
				</div>
				<div class="categoria">
					<h3>Categorie</h3>
					<ul>
						<li>Smartphone</li>
						<li>Pc</li>
						<li>Tablet</li>
						<li>Smartwatch</li>
					</ul>
				</div>
				<div class="checkbox-list">
					<h3>Brand</h3>
					<input type="checkbox" id="apple" name="apple" value="apple">
					<label for="apple"> Apple</label><br>
					<input type="checkbox" id="samsung" name="samsung" value="samsung">
					<label for="samsung"> Samsung</label><br>
					<input type="checkbox" id="google" name="google" value="google">
					<label for="google"> Google</label><br>
					<input type="checkbox" id="oppo" name="oppo" value="oppo">
					<label for="oppo"> Oppo</label><br>
					<input type="checkbox" id="asus" name="asus" value="asus">
					<label for="asus"> Asus</label><br>
					<input type="checkbox" id="hp" name="hp" value="hp">
					<label for="hp"> HP</label><br>
					<input type="checkbox" id="msi" name="msi" value="msi">
					<label for="msi"> MSI</label><br>
				</div>
				<div class="checkbox-list">
					<h3>Memoria HDD</h3>
					<input type="checkbox" id="apple" name="apple" value="apple">
					<label for="apple"> 64GB</label><br>
					<input type="checkbox" id="samsung" name="samsung" value="samsung">
					<label for="samsung"> 128GB</label><br>
					<input type="checkbox" id="google" name="google" value="google">
					<label for="google"> 256GB</label><br>
					<input type="checkbox" id="oppo" name="oppo" value="oppo">
					<label for="oppo"> 512GB</label><br>
					<input type="checkbox" id="asus" name="asus" value="asus">
					<label for="asus"> 1TB</label><br>
					<input type="checkbox" id="hp" name="hp" value="hp">
					<label for="hp"> 2TB</label><br>
				</div>
			</aside>
			
			<div class="content__prodotti">
				<c:forEach var="p" items="${prodotti}" begin="0">
			        <c:forEach var="s" items="${p.specifiche}" begin="0">
			            <a href="./ProdottoControl?action=toPage&id1=${p.IDProdotto}&id2=${s.IDSpecifiche}" style="text-decoration:none;">
			                <div class="product-card">
			                    <img src="./getPicture?id1=${p.IDProdotto}&id2=${s.IDSpecifiche}" alt="prova" style="width: 210px;">
			                    <div class="product-details">
			                        <h2>${p.nomeProdotto}</h2>
			                        <p>${p.descrizione}</p>
			                        <div class="product-price-buy">
			                            <span style="float: left; font-size: 14px; margin-bottom: 5px;">A partire da:</span>
			                            <h3>&euro;${s.prezzo}</h3>
			                        </div>
			                    </div>
			                </div>
			            </a>
			        </c:forEach>
			    </c:forEach>
	
			</div>
		</div>
	</div>

</body>
</html>