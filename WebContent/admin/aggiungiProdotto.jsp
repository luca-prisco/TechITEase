<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/aggiungiProdotto.css" />
    
    <title>Aggiungi Prodotto</title>
    
 	<script src="${pageContext.request.contextPath}/scripts/validateProductForm.js"></script>

</head>
<body>

	<form id="addProduct" method="post" action="${pageContext.request.contextPath}/ProdottoControl?action=insert">
		<h1>Aggiungi Prodotto</h1>
			<div class="prodotto-form">
				<h4 style="margin-top: 0;">Prodotto</h4>
				<div class="tableRow">
					<label for="nomeProdotto"> Nome prodotto </label>
					<div>
						<input id="nomeProdotto" type="text" name="nomeProdotto" onchange="validateFormElem(this, nomeProdottoPattern, document.getElementById('errorNomeProdotto'), nomeProdottoErrorMessage)" required>
						<span id="errorNomeProdotto"></span><br>
						<span></span>
					</div>
				</div>
				<div class="tableRow">
					<label for="brand"> Brand </label> 
					<div>
						<input type="text" id="brand" name="brand" onchange="validateFormElem(this, brandPattern, document.getElementById('errorBrand'), brandErrorMessage)" required>
						<span id="errorBrand"></span><br>
						<span></span>
					</div>
				</div>
				<div class="tableRow">
					<label for="categoria"> Categoria </label> 
					<div>
						<input type="text" id="categoria" name="categoria" onchange="validateFormElem(this, categoriaPattern, document.getElementById('errorCategoria'), categoriaErrorMessage)" required>
						<span id="errorCategoria"></span><br>
						<span></span>
					</div>
				</div>
				<div class="tableRow">
					<label for="descrizione"> Descrizione </label> 
					<div>
						<input type="text" id="descrizione" name="descrizione" onchange="validateFormElem(this, descrizionePattern, document.getElementById('errorDescrizione'), descrizioneErrorMessage)" required>
						<span id="errorDescrizione"></span><br>
						<span></span>
					</div>
				</div>
				<div class="tableRow">
					<label for="dettagli"> Dettagli </label> 
					<div>
						<textarea type="text" id="dettagli" name="dettagli" onchange="validateFormElem(this, dettagliPattern, document.getElementById('errorDettagli'), dettagliErrorMessage)" required></textarea><br>
						<span id="errorDettagli"></span><br>
						<span></span>
					</div>
				</div>
			

				<div id="additionalSpecs"> <!-- Contenitore per specifiche aggiuntive -->
				    <h4>Specifica </h4>
                    <div class="tableRow">
                        <label for="colore">Colore</label>
                        <div>
                        <input type="text" id="colore" name="colore" onchange="validateFormElem(this, colorePattern, document.getElementById('errorColore'), coloreErrorMessage)" required>
						<span id="errorColore"></span><br>
						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="hdd">Memoria HDD</label>
                        <div>
                        <input type="number" id="hdd" name="hdd" onchange="validateFormElem(this, hddPattern, document.getElementById('errorHdd'), hddErrorMessage)" required>
						<span id="errorHdd"></span><br>
						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="ram">Memoria RAM</label>
                        <div>
                       	<input type="number" id="ram" name="ram" onchange="validateFormElem(this, ramPattern, document.getElementById('errorRam'), ramErrorMessage)" required>
						<span id="errorRam"></span><br>
						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="quantita">Quantità</label>
                        <div>
                            <input type="number" id="quantita" name="quantita" onchange="validateFormElem(this, quantitaPattern, document.getElementById('errorQuantita'), quantitaErrorMessage)" required>
						<span id="errorQuantita"></span><br>
						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="prezzo">Prezzo</label>
                        <div>
                            <input type="text" id="prezzo" name="prezzo" placeholder="999.00" onchange="validateFormElem(this, prezzoPattern, document.getElementById('errorPrezzo'), prezzoErrorMessage)" required>
						<span id="errorPrezzo"></span><br>
						<span></span>
                        </div>
                    </div>
				</div> 
	            <div class="tableRow">
	                <label></label>
	                <div>
	                    <button type="button" onclick="aggiungiSpecifica()">Aggiungi Specifica</button>
	                </div>
	            </div>

			<c:if test="${not empty errors}">
				<c:forEach var="error" items="${errors}">
					<span style="color: red; font-size: 13px;">${error}</span><br>
				</c:forEach>
			</c:if>

			<div class="tableRow">
					<label></label>
					<div>
						<input type="submit" value="Aggiungi">
					</div>
				</div>
			</div>
	</form>
	    <script>
        let specificaIndex = 1;

        function aggiungiSpecifica() {
            const additionalSpecs = document.getElementById('additionalSpecs');
            const nuovaSpecifica = document.createElement('div');
            nuovaSpecifica.innerHTML = `
               
                    <h4>Specifica ${specificaIndex}</h4>
                    <div class="tableRow">
                        <label for="colore">Colore</label>
                        <div>
                            <input type="text" id="colore" name="colore" onchange="validateFormElem(this, colorePattern, document.getElementById('errorColore'), coloreErrorMessage)" required>
    						<span id="errorColore"></span><br>
    						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="hdd">Memoria HDD</label>
                        <div>
                            <input type="number" id="hdd" name="hdd" onchange="validateFormElem(this, hddPattern, document.getElementById('errorHdd'), hddErrorMessage)" required>
    						<span id="errorHdd"></span><br>
    						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="ram">Memoria RAM</label>
                        <div>
                            <input type="number" id="ram" name="ram" onchange="validateFormElem(this, ramPattern, document.getElementById('errorRam'), ramErrorMessage)" required>
    						<span id="errorRam"></span><br>
    						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="quantita">Quantità</label>
                        <div>
                            <input type="number" id="quantita" name="quantita" onchange="validateFormElem(this, quantitaPattern, document.getElementById('errorQuantita'), quantitaErrorMessage)" required>
    						<span id="errorQuantita"></span><br>
    						<span></span>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="prezzo">Prezzo</label>
                        <div>
                            <input type="text" id="prezzo" name="prezzo" placeholder="999.00" onchange="validateFormElem(this, prezzoPattern, document.getElementById('errorPrezzo'), prezzoErrorMessage)" required>
    						<span id="errorPrezzo"></span><br>
    						<span></span>
                        </div>
                    </div>
            `;
            additionalSpecs.appendChild(nuovaSpecifica);
            specificaIndex++;
        }
    </script>
	
</body>
</html>
