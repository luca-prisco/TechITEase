<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, width=device-width">
<title>Tech IT Ease</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagamento.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/responsive/pagamento-responsive.css" />

<script src="${pageContext.request.contextPath}/scripts/validatePagamentoForm.js"></script>

<body>
<form id="pagamento-details" method="post" action="${pageContext.request.contextPath}/OrdineControl?action=add">
    <h1>Pagamento</h1>
    <div class="form-container">
	    <div class="ordine-forms">
	    	<div class="pagamento-form" style="border-right: .2px solid grey;padding-right: 40px;">
		        <h4>Metodo di Pagamento</h4>
		        <div class="tableRow">
		            <label for="nomeCarta">Nome</label>
		            <div>
		                <input id="nomeCarta" type="text" name="nomeCarta" onchange="validateFormElem(this, nomeCartaPattern, document.getElementById('errorNomeCarta'), nomeCartaErrorMessage)"><br>
		                <span id="errorNomeCarta"></span><br>
						<span></span>	
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="cognomeCarta">Cognome</label>
		            <div>
		                <input id="cognomeCarta" type="text" name="cognomeCarta" onchange="validateFormElem(this, cognomeCartaPattern, document.getElementById('errorCognomeCarta'), cognomeCartaErrorMessage)"><br>
		                <span id="errorCognomeCarta"></span><br>
						<span></span>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="numeroCarta">Numero carta</label>
		            <div>
		                <input id="numeroCarta" type="text" name="numeroCarta" onchange="validateFormElem(this, numeroCartaPattern, document.getElementById('errorNumeroCarta'), numeroCartaErrorMessage)"><br>
		                <span id="errorNumeroCarta"></span><br>
						<span></span>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="scadenzaCarta">Data di Scadenza</label>
		            <div>
		                <input id="scadenzaCarta" type="text" name="scadenzaCarta" placeholder="MM/AA" onchange="validateFormElem(this, scadenzaCartaPattern, document.getElementById('errorScadenzaCarta'), scadenzaCartaErrorMessage)"><br>
		                <span id="errorScadenzaCarta"></span><br>
						<span></span>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="cvv">CVV</label>
		            <div>
		                <input id="cvv" type="text" name="cvv" onchange="validateFormElem(this, cvvPattern, document.getElementById('errorCvv'), cvvErrorMessage)"><br>
		                <span id="cvvCarta"></span><br>
						<span></span>
		            </div>
		        </div>
		    </div>
			<div class="indirizzo-form" style="padding-left: 40px;">
		        <h4>Indirizzo di Spedizione</h4>
		        <div class="tableRow">
		            <label for="via">Via</label>
		            <div>
		                <input id="via" type="text" name="via" onchange="validateFormElem(this, viaPattern, document.getElementById('errorVia'), viaErrorMessage)"><br>
		                <span id="errorVia"></span><br>
						<span></span>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="civico">Civico</label>
		            <div>
		                <input id="civico" type="text" name="civico" onchange="validateFormElem(this, civicoPattern, document.getElementById('errorCivico'), civicoErrorMessage)"><br>
		                <span id="errorCivico"></span><br>
						<span></span>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="cap">CAP</label>
		            <div>
		                <input id="cap" type="text" name="cap" onchange="validateFormElem(this, capPattern, document.getElementById('errorCap'), capErrorMessage)"><br>
		                <span id="errorCap"></span><br>
						<span></span>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="citta">Città</label>
		            <div>
		                <input id="citta" type="text" name="citta" onchange="validateFormElem(this, cittaPattern, document.getElementById('errorCitta'), cittaErrorMessage)"><br>
		                <span id="errorCitta"></span><br>
						<span></span>
		            </div>
		        </div>
		
		        
			</div>
		</div>
		<c:if test="${not empty errors}">
			<c:forEach var="error" items="${errors}">
				<span style="color: red;font-size:13px;">${error}</span>
			</c:forEach>
		</c:if>
		<div class="tableRow-sumbit">
			<div>
				<input id="invia" type="submit" value="Conferma acquisto">
			</div>
		</div>
	</div>
</form>

</body>
</html>