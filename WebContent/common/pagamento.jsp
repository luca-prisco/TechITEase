<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, width=device-width">
<title>Tech IT Ease</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagamento.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/responsive/pagamento-responsive.css" />

<body>
<form id="pagamento-details" method="post" action="${pageContext.request.contextPath}/OrdineControl?action=create">
    <h1>Pagamento</h1>
    <div class="form-container">
	    <div class="ordine-forms">
	    	<div class="pagamento-form" style="border-right: .2px solid grey;padding-right: 40px;">
		        <h4>Metodo di Pagamento</h4>
		        <div class="tableRow">
		            <label for="nomeCarta">Nome</label>
		            <div>
		                <input id="nomeCarta" type="text" name="nomeCarta" required>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="cognomeCarta">Cognome</label>
		            <div>
		                <input id="cognomeCarta" type="text" name="cognomeCarta" required>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="numeroCarta">Numero carta</label>
		            <div>
		                <input id="numeroCarta" type="text" name="numeroCarta" required>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="scadenzaCarta">Data di Scadenza</label>
		            <div>
		                <input id="scadenzaCarta" type="text" name="scadenzaCarta" placeholder="MM/AA" required>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="cvv">CVV</label>
		            <div>
		                <input id="cvv" type="text" name="cvv" required>
		            </div>
		        </div>
		    </div>
			<div class="indirizzo-form" style="padding-left: 40px;">
		        <h4>Indirizzo di Spedizione</h4>
		        <div class="tableRow">
		            <label for="via">Via</label>
		            <div>
		                <input id="via" type="text" name="via" required>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="civico">Civico</label>
		            <div>
		                <input id="civico" type="text" name="civico" required>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="cap">CAP</label>
		            <div>
		                <input id="cap" type="text" name="cap" required>
		            </div>
		        </div>
		        <div class="tableRow">
		            <label for="citta">Città</label>
		            <div>
		                <input id="citta" type="text" name="citta" required>
		            </div>
		        </div>
		
		        
			</div>
		</div>
		<div class="tableRow-sumbit">
			<div>
				<input id="invia" type="submit" value="Conferma l'acquisto">
			</div>
		</div>
	</div>
</form>

</body>
</html>