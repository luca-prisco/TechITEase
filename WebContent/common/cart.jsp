<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %> 
<%@ page import="model.bean.ProdottoBean" %> 
<%@ page import="model.bean.Specifiche" %>  
<%@ page import="model.Cart" %>
    

<%
    Cart cart = (Cart) session.getAttribute("cart");
%>
  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, width=device-width">
<title>Carrello</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cart.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/responsive/cart-responsive.css" />


</head>
<body>
	<jsp:include page="/include/header.jsp"/>
	
    <div class="page">
        <div class="left">
        	<div class="top-content">
        		<h1>Il tuo carrello</h1>
        		<form id="deleteForm" method="post" action="${pageContext.request.contextPath}/common/CartControl?action=deleteAll">
					<button type="submit" style="margin-top:12px;">Svuota</button>
				</form>
        	</div>
        	<div class="prodotti__container">
	  			<c:forEach var="item" items="${cart.items}" varStatus="loop">
	  				<div class="prodotto__cart">
	  					<div style="display:flex;">
		  					<div class="prodotto__image">
					  			<img id="productImage"
									src="${pageContext.request.contextPath}/getPicture?id1=${item.prodotto.IDProdotto}&id2=${item.specifiche.IDSpecifiche}"
									alt="prova">
							</div>
			                <div class="prodotto__details">
			                    <h3>${item.prodotto.nomeProdotto} - ${item.specifiche.colore}</h3>
			                    <p>HDD: ${item.specifiche.hdd} GB<br>
			                    RAM: ${item.specifiche.ram} GB<br>
			                    Quantità: <span id="item-${loop.index + 1}">${item.quantity}</span><br></p>
			                </div>
		                </div>
		                <div class="actions">
							<select class="quantity-select" onChange=onChangeQuantity(this)
								data-index-id="${loop.index + 1}"
								data-product-id="${item.prodotto.IDProdotto}"
								data-spec-id="${item.specifiche.IDSpecifiche}"
								data-price="${item.specifiche.prezzo}"
								data-quantity="${item.quantity}">
								
								<c:forEach var="quantity" begin="1" end="9">	
									<c:choose>
										<c:when test="${quantity == item.quantity}">	<!-- Per impostare di default la quantità corrente -->
											<option selected="selected">${quantity}</option>
										</c:when>
										<c:otherwise>
											<option>${quantity}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
	
							<form id="deleteForm" method="post" action="${pageContext.request.contextPath}/common/CartControl">
								<input type="hidden" name="action" value="delete"> 
								<input type="hidden" name="del1" value="${item.prodotto.IDProdotto}">
								<input type="hidden" name=del2 value="${item.specifiche.IDSpecifiche}">
								<button type="submit">Rimuovi</button>
							</form>
						</div>
		        	</div>
	            </c:forEach>
            </div>
        </div>
        <div class="right">
        	<h1>Riepilogo</h1>
        	<div class="riepilogo__cart">
        		<c:forEach var="item" items="${cart.items}" varStatus="loop">
		        	<div class="prodotto__riepilogo">
		        		<div style="display:flex;width: 100%;">
		  					<div class="prodotto__image">
					  			<img id="productImageRiep"
									src="${pageContext.request.contextPath}/getPicture?id1=${item.prodotto.IDProdotto}&id2=${item.specifiche.IDSpecifiche}"
									alt="prova">
							</div>
			                <div class="prodotto__details-rep">
			                    <p style="font-weight: 500;float: left;line-height: 1.5;">${item.prodotto.nomeProdotto}<br>${item.specifiche.hdd}gb - ${item.specifiche.ram}gb</p>
			                    
			                    <p id="prod-${loop.index + 1}" style="font-weight: 300;float: right;"> 
			                    	${item.quantity} x <c:out value="${item.specifiche.prezzo}"/>€
			                    </p>
			                    					
			                </div>
		                </div>
		        	</div>
		        	
	        	</c:forEach>
	        	<hr style="width: 90%;border: 0.2px solid #D5D6D8;">
				<div class="riepilogo-totale">
					<p>Totale</p>

					<p id="totale-cart">
						<c:set var="total" value="0" />
						<c:forEach var="item" items="${cart.items}">
							<c:set var="total" value="${total + (item.specifiche.prezzo * item.quantity)}" />
						</c:forEach>
						<c:out value="${total}" />€
					</p>
				</div>
				<form id="confermaOrdine" method="get" action="./Pagamento">
					<input type="hidden" name="action" value="conferma"> 
					<button type="submit">Conferma il tuo ordine</button>
				</form>
				
			</div>
        </div>
    </div>
<script>
function onChangeQuantity(selectElement) {
    var productId = selectElement.getAttribute("data-product-id");
    var specId = selectElement.getAttribute("data-spec-id");
    var selectedQuantity = selectElement.value;
    var price = selectElement.getAttribute("data-price");
    var indiceProdotto = selectElement.getAttribute("data-index-id");

    // Aggiorna il testo della quantità visualizzata per questo elemento
    var quantityElement = document.getElementById("item-"+indiceProdotto);
    if (quantityElement) {
        quantityElement.innerText = selectedQuantity;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "${pageContext.request.contextPath}/common/CartControl?action=updateQuantity", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log("Quantità aggiornata");
            updateTotalCart(selectedQuantity, price, indiceProdotto);
        }
    };
    xhr.send("productId=" + productId + "&specId=" + specId + "&quantity=" + selectedQuantity);
}

function updateTotalCart(quantity, price, index) {
	
    function updateTotalCart() {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "${pageContext.request.contextPath}/common/CartControl", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var total = JSON.parse(xhr.responseText);
                document.getElementById("totale-cart").textContent = total.toFixed(2) + " €";
            }
        };
        xhr.send("action=getTotal");
    }
    
    // Chiamata per aggiornare il totale del carrello all'avvio della pagina
    updateTotalCart();
    document.getElementById("prod-"+index).innerHTML = quantity + " x " + price + " €";
}
</script>
  
</body>
</html>