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
    	<input type="hidden" id="idProdotto" value="${prodotto.IDProdotto}">
    	<input type="hidden" id="idSpecifiche" value="${param.id2}">
    	
        <div class="product__left">
            <img id="productImage" src="./getPicture?id1=${prodotto.IDProdotto}&id2=${param.id2}" alt="prova">
        </div>
        <div class="product__right">
            <div class="details">
                <h1 style="margin-top: 0;">${prodotto.nomeProdotto}</h1>
                <p>${prodotto.dettagli}</p>
                
                <p style="font-weight: 400;">Colore</p>
                <div class="spec-options">
                		<c:forEach var="colore" items="${colorsSet}">
						    <input type="radio" id="colore-${colore}" name="colori" value="${colore}" />
						    <label class="radio-button-label" for="colore-${colore}">${colore}</label>
						    <br />
						</c:forEach>
				</div>
							
			    <p style="font-weight: 400;">HDD</p>
			    <div class="spec-options">
			        <c:forEach var="hdd" items="${hddsSet}">
			            <input type="radio" id="hdd-${hdd}" name="hdds" value="${hdd}" />
			            <label class="radio-button-label" for="hdd-${hdd}">${hdd} gb</label>
			            <br />
			        </c:forEach>
			    </div>
				
			    <p style="font-weight: 400;">RAM</p>
			    <div class="spec-options">
			        <c:forEach var="ram" items="${ramsSet}">
			            <input type="radio" id="ram-${ram}" name="rams" value="${ram}" />
			            <label class="radio-button-label" for="ram-${ram}">${ram} gb</label>
			            <br />
			        </c:forEach>
			    </div>
			
            </div>
            <form id="addToCartForm" action="${pageContext.request.contextPath}/common/CartControl?action=add" method="post">
                <input type="hidden" name="idProdCart" value="${prodotto.IDProdotto}">
                <input type="hidden" name="idSpecCart" value="${id2}">
                <button type="submit" class="addToCart-button">Aggiungi al carrello</button>
            </form>
        </div>
    </div>
</div>

<script>	

var specificheJson = '${specificheJson}';

// Convertire il JSON in una struttura JavaScript valida
var specificheArray = JSON.parse(specificheJson);


function initProductPage() {
    var idProdotto = document.getElementById('idProdotto').value;
    var idSpecCart = document.getElementById('idSpecifiche').value;

    // Cerca le specifiche corrispondenti nel JSON
    var specifiche = specificheArray.find(function(spec) {
        return (spec.IDProdotto == idProdotto) && (spec.IDSpecifiche == idSpecCart);
    });

    if (specifiche) {
        // Imposta il colore selezionato
        if (specifiche.colore) {
            document.querySelector('input[name="colori"][value="' + specifiche.colore + '"]').checked = true;
        }
        // Imposta l'HDD selezionato
        if (specifiche.hdd) {
            document.querySelector('input[name="hdds"][value="' + specifiche.hdd + '"]').checked = true;
        }
        // Imposta la RAM selezionata
        if (specifiche.ram) {
            document.querySelector('input[name="rams"][value="' + specifiche.ram + '"]').checked = true;
        }
    }
}

// Chiama la funzione quando la pagina è caricata
document.addEventListener("DOMContentLoaded", initProductPage);

</script>


<script>
var specificheJson = '${specificheJson}';

//Convertire il JSON in una struttura JavaScript valida
var specificheArray = JSON.parse(specificheJson);
console.log(specificheArray);

function updateProductPage() {
    // Recupera i valori selezionati di colore, HDD e RAM
    var selectedColor = document.querySelector('input[name="colori"]:checked').value;
    var selectedHDD = document.querySelector('input[name="hdds"]:checked').value;
    var selectedRAM = document.querySelector('input[name="rams"]:checked').value;

    // Cerca le specifiche corrispondenti nel JSON
    var prodotto = specificheArray.find(function(spec) {
        return (spec.colore == selectedColor) && (spec.hdd == selectedHDD) && (spec.ram == selectedRAM);
    });
    
    if(prodotto) {
        var url = './getPicture?id1=' + prodotto.IDProdotto + '&id2=' + prodotto.IDSpecifiche;
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.responseType = 'blob'; // Indica che la risposta sarà un Blob

        // quando la richiesta è completata effettua 
        xhr.onload = function() {
            if (xhr.status === 200) {
                // Converte il Blob ricevuto in un URL blob
                var blob = xhr.response;
                var imageUrl = URL.createObjectURL(blob);

                // Aggiorna l'immagine con l'URL blob
                document.getElementById('productImage').src = imageUrl;
            } else {
                console.error('Errore durante il recupero dell\'immagine:', xhr.statusText);
            }
        };
        
        xhr.send();
        
        //Aggiorno gli id del prodotto selezionato per l'aggiunta al carrello
        document.querySelector('input[name="idProdCart"]').value = prodotto.IDProdotto;
       	document.querySelector('input[name="idSpecCart"]').value = prodotto.IDSpecifiche;
    }
    
}

//Invocazione della funzione ad ogni cambiamento dei radio
var coloriRadios = document.querySelectorAll('input[name="colori"]');
var hddsRadios = document.querySelectorAll('input[name="hdds"]');
var ramsRadios = document.querySelectorAll('input[name="rams"]');

coloriRadios.forEach(function(radio) {
    radio.addEventListener('change', updateProductPage);
});

hddsRadios.forEach(function(radio) {
    radio.addEventListener('change', updateProductPage);
});

ramsRadios.forEach(function(radio) {
    radio.addEventListener('change', updateProductPage);
});
</script>


</body>
</html>