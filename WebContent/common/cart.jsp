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
  			<c:forEach var="item" items="${cart.items}">
                <div class="prodotto__cart">
                    <p>${item.prodotto.nomeProdotto}</p>
                    <p>Colore: ${item.specifiche.colore}</p>
                    <p>HDD: ${item.specifiche.hdd} GB</p>
                    <p>RAM: ${item.specifiche.ram} GB</p>
                    <p>Prezzo unitario: €${item.specifiche.prezzo}</p>
                    <p>Quantità: ${item.quantity}</p>
                    <p>Prezzo totale: €<c:out value="${item.specifiche.prezzo * item.quantity}" /></p>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>