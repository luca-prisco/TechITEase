<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${prodotto.nomeProdotto}</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/productPage.css" />
</head>
<body>
	<jsp:include page="/include/header.jsp" />
	<div class="page">
		<div class="product">
			<div class="product__left">
				<img id="productImage"
					src="./getPicture?id1=${prodotto.IDProdotto}&id2=${param.id2}"
					alt="prova">
			</div>
			<div class="product__right">
				<div class="details">
					<h1>${prodotto.nomeProdotto}</h1>
					<p>${prodotto.dettagli}</p>
					<c:forEach var="spec" items="${prodotto.specifiche}">
						<c:if test="${spec.IDSpecifiche == param.id2}">
							<div class="specifica-selezionata">
								<p id="colore">Colore: ${spec.colore}</p>
								<p id="hdd">HDD: ${spec.hdd} GB</p>
								<p id="ram">RAM: ${spec.ram} GB</p>
								<p id="prezzo">&euro;${spec.prezzo}</p>
							</div>
						</c:if>
					</c:forEach>
				</div>
				<form id="addToCartForm" action="${pageContext.request.contextPath}/common/AddToCart?action=add" method="post">
					<input type="hidden" name="idProdCart" value="${prodotto.IDProdotto}">
					<input type="hidden" name="idSpecCart" value="${id2}">
					<button type="submit" class="addToCart-button">Aggiungi al carrello</button>
				</form>
				<div id="spec-buttons">
					<c:forEach var="spec" items="${prodotto.specifiche}">
						<button type="button" class="spec-button"
							data-id="${spec.IDSpecifiche}">${spec.colore}- HDD:
							${spec.hdd} GB - RAM: ${spec.ram} GB</button>
					</c:forEach>
				</div>

			</div>
		</div>
	</div>

	<script>
    var specifiche = JSON.parse('${specificheJson}');
    var selectedSpecId = '${id2}'; //Prendiamo l'id della specifica del prodotto selezionato

    function updateSpecifiche() {
    	//Troviamo il bottone con la classe selected e prendiamo il valore di data-id
        var selectedSpecId = document.querySelector('.spec-button.selected')?.getAttribute('data-id');

    	//Cerchiamo nelle specifiche l'oggetto con IDSpecifiche uguale a selectedSpecId
        var selectedSpec = specifiche.find(spec => spec.IDSpecifiche == selectedSpecId);

    	//Se lo trova avviene l'aggiornamento del DOM
        if (selectedSpec) {
            document.getElementById('productImage').src = "./getPicture?id1=${prodotto.IDProdotto}&id2=" + selectedSpec.IDSpecifiche;
            document.getElementById('colore').textContent = "Colore: " + selectedSpec.colore;
            document.getElementById('hdd').textContent = "HDD: " + selectedSpec.hdd + " GB";
            document.getElementById('ram').textContent = "RAM: " + selectedSpec.ram + " GB";
            document.getElementById('prezzo').textContent = "€" + selectedSpec.prezzo;
			document.querySelector('input[name="idSpecCart"]').value = selectedSpec.IDSpecifiche; // Aggiorna l'input nascosto per l'aggiunta al carrello
        }
    }


	function initializeSpecButtons() {
		// Selezioniamo tutti i pulsanti con la classe spec-button
		document.querySelectorAll('.spec-button').forEach(button => {
			button.addEventListener('click', function() { // Aggiungiamo un eventListener ad ogni pulsante
				document.querySelectorAll('.spec-button').forEach(btn => btn.classList.remove('selected')); // Togliamo il selected a tutti i pulsanti
				this.classList.add('selected'); // Aggiungiamo selected al pulsante premuto
				updateSpecifiche(); // Aggiorniamo le specifiche in base al pulsante selezionato
			});

			// Se il pulsante ha l'ID uguale a selectedSpecId, aggiungiamo la classe selected
			if (button.getAttribute('data-id') === selectedSpecId) {
				button.classList.add('selected');
			}
		});
	}

	// Inizializza lo stato dei bottoni in base alla selezione predefinita
	initializeSpecButtons();
	updateSpecifiche();
</script>

</body>
</html>