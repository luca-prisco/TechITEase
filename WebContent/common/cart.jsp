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

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" />
</head>
<body>
	<jsp:include page="/include/header.jsp"/>
	
    <div class="page">
        <div class="left">
        	<h1>Il tuo carrello</h1>
        	<div class="prodotti__container">
	  			<c:forEach var="item" items="${cart.items}">
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
			                    Quantità: ${item.quantity}<br></p>
			                    <p style="font-size: 16px;font-weight: 400; margin:0;"><c:out value="${item.specifiche.prezzo * item.quantity}" /> €</p>
			                </div>
		                </div>
		                <div class="quantity">
		                	<select>
		                		<option selected="selected">1</option>	
		                		<option>2</option>	
		                		<option>3</option>	
		                		<option>4</option>	
		                		<option>5</option>	
		                		<option>6</option>	
		                		<option>7</option>	
		                		<option>8</option>	
		                		<option>9</option>	
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
        		<c:forEach var="item" items="${cart.items}">
		        	<div class="prodotto__riepilogo">
		        		<div style="display:flex;width: 100%;">
		  					<div class="prodotto__image">
					  			<img id="productImageRiep"
									src="${pageContext.request.contextPath}/getPicture?id1=${item.prodotto.IDProdotto}&id2=${item.specifiche.IDSpecifiche}"
									alt="prova">
							</div>
			                <div class="prodotto__details-rep">
			                    <p style="font-weight: 500;">${item.prodotto.nomeProdotto} - ${item.specifiche.hdd}gb - ${item.specifiche.ram}gb</p>
			                    
			                    <p style="font-weight: 300;"><c:out value="${item.specifiche.prezzo * item.quantity}"/>€</p>
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
							<c:set var="total"
								value="${total + (item.specifiche.prezzo * item.quantity)}" />
						</c:forEach>
						<c:out value="${total}" />
						€
					</p>
				</div>
				<form id="confermaOrdine" method="get" action="ProdottoControl">
					<input type="hidden" name="action" value="delete"> 
					<input type="hidden" name="del1" value="${prodotto.IDProdotto}">
					<input type="hidden" name="del2" value="${specifiche.IDSpecifiche}">
					<button type="submit">Conferma il tuo ordine</button>
				</form>
				
			</div>
        </div>
    </div>
</body>
</html>