<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="model.bean.*" %>

<%
    PagamentoBean pagamento = (PagamentoBean) request.getAttribute("pagamento");
    Collection<AcquistoBean> acquisti = (Collection<AcquistoBean>) request.getAttribute("acquisti");
    String via = request.getParameter("via");
    String civico = request.getParameter("civico");
    String cap = request.getParameter("cap");
    String citta = request.getParameter("citta");
    String emailUtente = request.getParameter("email");
    String idOrdine = request.getParameter("idOrdine");
    String totale = request.getParameter("total");
    String risolvi = "res";

    if (acquisti == null || acquisti.isEmpty() || pagamento == null) {
        response.sendRedirect(request.getContextPath() + "/OrdineControl?action=dettagli");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, width=device-width">
<title>Take IT Ease</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/dettagliOrdine.css" />
</head>
<body>
    <jsp:include page="/include/headerAdmin.jsp"/>
    <div class="page">
    	<h1 style="margin-left: 40px;">Dettagli ordine</h1>
    	<div class="info-ordine">
    		<p style="font-weight: 500;font-size: 15px;margin: 0;">Id ordine: <%=idOrdine %> <br>Email utente: <%=emailUtente %></p>	
    	</div>
    	<div class="content">
    		
	        <div class="acquisti-ordine">
	       		<h3 style="margin-bottom: 0;">Prodotti</h3>
	            <c:forEach var="item" items="${acquisti}">
	                <div class="prodotto__cart">
	                    <div style="display:flex;">
	                        <div class="prodotto__details">
	                            <h4>${item.nome} - ${item.colore}</h4>
	                            <p>HDD: ${item.hdd} GB - RAM: ${item.ram}<br>
	                            	Quantità: ${item.quantita}<br>
	                            	Prezzo unitario: ${item.prezzoUnitario}€</p>
	                        </div>
	                    </div>
	                </div>          
	            </c:forEach>
	            <p style="font-weight: 500;font-size: 18px;margin-bottom: 0;">Totale: <%=totale %>€</p>
	        </div>
	        <div class="indirizzo-consegna">
	            <h3>Indirizzo</h3>
	            <p>Via: <%=via %>, <%=civico %><br>
	               CAP: <%=cap %><br>
	               Città: <%=citta %></p>
	             <form id="risolviOrdine" method="post" action="${pageContext.request.contextPath}/OrdineControl">
	             	<input type="hidden" name="action" value="risolvi"> 
					<input type="hidden" name="idOrdine" value="<%=idOrdine%>"> 
					<button type="submit">Risolvi Ordine</button>
				</form>
	        </div>
	    </div>
    </div>
</body>
</html>