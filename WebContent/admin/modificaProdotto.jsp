<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="model.bean.*" %>

<%
String idProdotto = request.getParameter("id");
String nomeProdotto = request.getParameter("nome");
String brand = request.getParameter("brand");
String categoria = request.getParameter("categoria");
String descrizione = request.getParameter("descrizione");
String dettagli = request.getParameter("dettagli");
String hdd = request.getParameter("hdd");
String ram = request.getParameter("ram");
String colore = request.getParameter("colore");
String prezzo = request.getParameter("prezzo");
String quantita = request.getParameter("quantita");
String idSpecifiche = request.getParameter("idSpecifiche");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/aggiungiProdotto.css" />
 	<script src="${pageContext.request.contextPath}/scripts/validateProductForm.js"></script>
<title>Tech IT Ease</title>
</head>
<body>
<form id="updateProduct" method="post" action="${pageContext.request.contextPath}/ProdottoControl?action=update">
		<h1>Modifica Prodotto</h1>
			<div class="prodotto-form">
				<h4 style="margin-top: 0;">Prodotto</h4>
			<div class="tableRow">
					<label for="nomeProdotto"> Nome prodotto </label>
					<div>
						<input id="nomeProdotto" type="text" name="nomeProdotto" value="<%= nomeProdotto %>" onchange="validateFormElem(this, nomeProdottoPattern, document.getElementById('errorNomeProdotto'), nomeProdottoErrorMessage)" required>
						<span id="errorNomeProdotto"></span><br>
						<span></span>
					</div>
				</div>
				<div class="tableRow">
					<label for="brand"> Brand </label> 
					<div>
						<input type="text" id="brand" name="brand" value="<%= brand %>" onchange="validateFormElem(this, brandPattern, document.getElementById('errorBrand'), brandErrorMessage)" required>
						<span id="errorBrand"></span><br>
						<span></span>
					</div>
				</div>
				<div class="tableRow">
					<label for="categoria"> Categoria </label> 
					<div>
						<input type="text" id="categoria" name="categoria" value="<%= categoria %>" onchange="validateFormElem(this, categoriaPattern, document.getElementById('errorCategoria'), categoriaErrorMessage)" required>
						<span id="errorCategoria"></span><br>
						<span></span>
					</div>
				</div>
				<div class="tableRow">
					<label for="descrizione"> Descrizione </label> 
					<div>
						<input type="text" id="descrizione" name="descrizione" value="<%= descrizione %>" onchange="validateFormElem(this, descrizionePattern, document.getElementById('errorDescrizione'), descrizioneErrorMessage)" required>
						<span id="errorDescrizione"></span><br>
						<span></span>
					</div>
				</div>
				<div class="tableRow">
					<label for="dettagli"> Dettagli </label> 
					<div>
						<textarea type="text" id="dettagli" name="dettagli" onchange="validateFormElem(this, dettagliPattern, document.getElementById('errorDettagli'), dettagliErrorMessage)" required><%= dettagli %></textarea><br>
						<span id="errorDettagli"></span><br>
						<span></span>
					</div>
				</div>



			<div id="additionalSpecs"> <!-- Contenitore per specifiche aggiuntive -->
				    <h4>Specifica </h4>
                    <div class="tableRow">
                        <label for="colore">Colore</label>
                        <div>
                        <input type="text" id="colore" name="colore" value="<%= colore %>" onchange="validateFormElem(this, colorePattern, document.getElementById('errorColore'), coloreErrorMessage)" required>
						<span id="errorColore"></span><br>
						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="hdd">Memoria HDD</label>
                        <div>
                        <input type="number" id="hdd" name="hdd" value="<%= hdd %>" onchange="validateFormElem(this, hddPattern, document.getElementById('errorHdd'), hddErrorMessage)" required>
						<span id="errorHdd"></span><br>
						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="ram">Memoria RAM</label>
                        <div>
                       	<input type="number" id="ram" name="ram" value="<%= ram %>" onchange="validateFormElem(this, ramPattern, document.getElementById('errorRam'), ramErrorMessage)" required>
						<span id="errorRam"></span><br>
						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="quantita">Quantità</label>
                        <div>
                            <input type="number" id="quantita" name="quantita" value="<%= quantita %>" onchange="validateFormElem(this, quantitaPattern, document.getElementById('errorQuantita'), quantitaErrorMessage)" required>
						<span id="errorQuantita"></span><br>
						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="prezzo">Prezzo</label>
                        <div>
                            <input type="text" id="prezzo" name="prezzo" placeholder="999.00" value="<%= prezzo %>" onchange="validateFormElem(this, prezzoPattern, document.getElementById('errorPrezzo'), prezzoErrorMessage)" required>
						<span id="errorPrezzo"></span><br>
						<span></span>
                        </div>
                    </div>
				</div>

			<c:if test="${not empty errors}">
				<c:forEach var="error" items="${errors}">
					<span style="color: red; font-size: 13px;">${error}</span>
					<br>
				</c:forEach>
			</c:if>

			<div class="tableRow">
					<label></label>
					<div class="tableRow-sumbit">
						<input type="hidden" name="idProdotto" value="<%= idProdotto %>">
						<input type="hidden" name="idSpecifiche" value="<%= idSpecifiche %>">
						<input type="submit" value="Modifica">
					</div>
				</div>
			</div>
	</form>
</body>
</html>