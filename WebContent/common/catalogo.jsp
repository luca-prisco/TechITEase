<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="model.dao.*" %>
<%@ page import="model.bean.*" %>
<%@ page import="model.*" %>


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
						<option selected value="bestseller">Bestseller</option>
						<option value="crescente">Prezzo crescente</option>
						<option value="decrescente">Prezzo decrescente</option>
					</select> 
				</div>
				<h3>Prezzo</h3> 
				<div class="prezzo" style="display: flex;justify-content: space-evenly;">     
			        <input type="text" id="minPrice" name="minPrice" min="0" placeholder="Min(€)">
			        <input type="text" id="maxPrice" name="maxPrice" min="0" placeholder="Max(€)">
				</div>
 				<div class="radio-filter">
                    <h3>Categorie</h3>
                    <ul>
                        <li><input type="radio" name="categoria-radio" class="categoria-radio" value="smartphone" onchange="categoriaChange(this)"> Smartphone</li>
                        <li><input type="radio" name="categoria-radio" class="categoria-radio" value="notebook" onchange="categoriaChange(this)"> Notebook</li>
                        <li><input type="radio" name="categoria-radio" class="categoria-radio" value="tablet" onchange="categoriaChange(this)"> Tablet</li>
                        <li><input type="radio" name="categoria-radio" class="categoria-radio" value="smartwatch" onchange="categoriaChange(this)"> Smartwatch</li>
                    </ul>
                </div>
                <div class="radio-filter">
                    <h3>Brand</h3>
                    <input type="radio" name="brand-radio" class="brand-radio" value="apple" onchange="brandChange(this)">
                    <label for="apple"> Apple</label><br>
                    <input type="radio" name="brand-radio" class="brand-radio" value="samsung" onchange="brandChange(this)">
                    <label for="samsung"> Samsung</label><br>
                    <input type="radio" name="brand-radio" class="brand-radio" value="google" onchange="brandChange(this)">
                    <label for="google"> Google</label><br>
                    <input type="radio" name="brand-radio" class="brand-radio" value="asus" onchange="brandChange(this)">
                    <label for="asus"> Asus</label><br>
                    <input type="radio" name="brand-radio" class="brand-radio" value="hp" onchange="brandChange(this)">
                    <label for="hp"> HP</label><br>
                    <input type="radio" name="brand-radio" class="brand-radio" value="msi" onchange="brandChange(this)">
                    <label for="msi"> MSI</label><br>
                </div>
			</aside>

			<div class="content__prodotti" id="productContainer">
				<c:forEach var="p" items="${prodotti}">
					<div class="product-card">
						<a
							href="./ProdottoControl?action=toPage&id1=${p.prodotto.IDProdotto}&id2=${p.specRidotte.IDSpecifiche}"
							style="text-decoration: none;"> <img
							src="./getPicture?id1=${p.prodotto.IDProdotto}&id2=${p.specRidotte.IDSpecifiche}"
							alt="prova" style="width: 210px;">
							<div class="product-details">
								<h2>${p.prodotto.nomeProdotto}- ${p.specRidotte.hdd}GB</h2>
								<p>${p.prodotto.descrizione}</p>
								<div class="product-price-buy">
									<span style="float: left; font-size: 14px; margin-bottom: 5px;">A
										partire da:</span>
									<h3>&euro;${p.specRidotte.prezzo}</h3>
								</div>
							</div>
							
						</a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
<script>
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("sort").addEventListener("change", function() {
        var sortOption = this.value;
        fetchProdotti(sortOption);
    });

    function fetchProdotti(sortOption) {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "./FiltriCatalogo?sort=" + sortOption, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var prodotti = JSON.parse(xhr.responseText);
                updateProdotti(prodotti);
            }
        };
        xhr.send();
    }
    
    function updateProdotti(prodotti) {
        var prodottiDiv = document.getElementById("productContainer");
        prodottiDiv.innerHTML = "";
        prodotti.forEach(function(p) {

            var prodottoDiv = document.createElement("div");
            prodottoDiv.className = "product-card";

            var idProdotto = p.prodotto.IDProdotto;
            var idSpecifiche = p.specRidotte.IDSpecifiche;

            prodottoDiv.innerHTML = '<a href="./ProdottoControl?action=toPage&id1=' + idProdotto + '&id2=' + idSpecifiche + '" style="text-decoration: none;">' +
            '<img src="./getPicture?id1=' + idProdotto + '&id2=' + idSpecifiche + '" alt="prova" style="width: 210px;">' +
            '<div class="product-details">' +
            '<h2>' + p.prodotto.nomeProdotto + ' - ' + p.specRidotte.hdd + 'GB</h2>' +
            '<p>' + p.prodotto.descrizione + '</p>' +
            '<div class="product-price-buy">' +
            '<span style="float: left; font-size: 14px; margin-bottom: 5px;">A partire da:</span>' +
            '<h3>&euro;' + p.specRidotte.prezzo + '</h3>' +
            '</div>' +
            '</div>' +
            '</a>';
       		prodottiDiv.appendChild(prodottoDiv)
        });
    }
});
</script>

<script>
	function categoriaChange(radio) {
	    var categoriaSelezionata = radio.value;
	    console.log("Categoria selezionata:", categoriaSelezionata);
	    filtraPerCategoria(categoriaSelezionata);
	}
	
	function filtraPerCategoria(sortOption) {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "./FiltriCatalogo?categoria=" + sortOption, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var prodotti = JSON.parse(xhr.responseText);
                updateProdotti(prodotti);
            }
        };
        xhr.send();
    }
	function updateProdotti(prodotti) {
    	console.log(prodotti);
        var prodottiDiv = document.getElementById("productContainer");
        prodottiDiv.innerHTML = "";
        prodotti.forEach(function(p) {
            // Logging per debug
            console.log("Prodotto: ", p);

            var prodottoDiv = document.createElement("div");
            prodottoDiv.className = "product-card";

            var idProdotto = p.prodotto.IDProdotto;
            var idSpecifiche = p.specRidotte.IDSpecifiche;

            console.log("IDProdotto: ", idProdotto);
            console.log("IDSpecifiche: ", idSpecifiche);

            prodottoDiv.innerHTML = '<a href="./ProdottoControl?action=toPage&id1=' + idProdotto + '&id2=' + idSpecifiche + '" style="text-decoration: none;">' +
            '<img src="./getPicture?id1=' + idProdotto + '&id2=' + idSpecifiche + '" alt="prova" style="width: 210px;">' +
            '<div class="product-details">' +
            '<h2>' + p.prodotto.nomeProdotto + ' - ' + p.specRidotte.hdd + 'GB</h2>' +
            '<p>' + p.prodotto.descrizione + '</p>' +
            '<div class="product-price-buy">' +
            '<span style="float: left; font-size: 14px; margin-bottom: 5px;">A partire da:</span>' +
            '<h3>&euro;' + p.specRidotte.prezzo + '</h3>' +
            '</div>' +
            '</div>' +
            '</a>';
       		prodottiDiv.appendChild(prodottoDiv)
        });
    }
</script>

<script>
	function brandChange(radio) {
	    var brandSelezionato = radio.value;
	    filtraPerBrand(brandSelezionato);
	}
	
	function filtraPerBrand(sortOption) {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "./FiltriCatalogo?brand=" + sortOption, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var prodotti = JSON.parse(xhr.responseText);
                updateProdotti(prodotti);
            }
        };
        xhr.send();
    }
	function updateProdotti(prodotti) {
        var prodottiDiv = document.getElementById("productContainer");
        prodottiDiv.innerHTML = "";
        prodotti.forEach(function(p) {

            var prodottoDiv = document.createElement("div");
            prodottoDiv.className = "product-card";

            var idProdotto = p.prodotto.IDProdotto;
            var idSpecifiche = p.specRidotte.IDSpecifiche;

            prodottoDiv.innerHTML = '<a href="./ProdottoControl?action=toPage&id1=' + idProdotto + '&id2=' + idSpecifiche + '" style="text-decoration: none;">' +
            '<img src="./getPicture?id1=' + idProdotto + '&id2=' + idSpecifiche + '" alt="prova" style="width: 210px;">' +
            '<div class="product-details">' +
            '<h2>' + p.prodotto.nomeProdotto + ' - ' + p.specRidotte.hdd + 'GB</h2>' +
            '<p>' + p.prodotto.descrizione + '</p>' +
            '<div class="product-price-buy">' +
            '<span style="float: left; font-size: 14px; margin-bottom: 5px;">A partire da:</span>' +
            '<h3>&euro;' + p.specRidotte.prezzo + '</h3>' +
            '</div>' +
            '</div>' +
            '</a>';
       		prodottiDiv.appendChild(prodottoDiv)
        });
    }
</script>


</body>
</html>