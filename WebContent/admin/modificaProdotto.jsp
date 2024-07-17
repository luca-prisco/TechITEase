<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
						<input id="nomeProdotto" type="text" name="nomeProdotto" value="<%= nomeProdotto %>"> 
					</div>
				</div>
				<div class="tableRow">
					<label for="brand"> Brand </label> 
					<div>
						<input type="text" id="brand" name="brand" value="<%= brand %>">
					</div>
				</div>
				<div class="tableRow">
					<label for="categoria"> Categoria </label> 
					<div>
						<input type="text" id="categoria" name="categoria" value="<%= categoria %>">
					</div>
				</div>
				<div class="tableRow">
					<label for="descrizione"> Descrizione </label> 
					<div>
						<input type="text" id="descrizione" name="descrizione" value="<%= descrizione %>">
					</div>
				</div>
				<div class="tableRow">
					<label for="dettagli"> Dettagli </label>
					<div>
						<textarea id="dettagli" name="dettagli"><%=dettagli%></textarea>
					</div>
				</div>



			<div id="additionalSpecs"> <!-- Contenitore per specifiche aggiuntive -->
				    <h4>Specifica </h4>
                    <div class="tableRow">
                        <label for="colore">Colore</label>
                        <div>
                            <input type="text" id="colore" name="colore" value="<%= colore %>" required>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="hdd">Memoria HDD</label>
                        <div>
                            <input type="number" id="hdd" name="hdd" value="<%= hdd %>" required>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="ram">Memoria RAM</label>
                        <div>
                            <input type="number" id="ram" name="ram" value="<%= ram %>" required>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="quantita">Quantità</label>
                        <div>
                            <input type="number" id="quantita" name="quantita" value="<%= quantita %>" required>
                        </div>
                    </div>
                    <div class="tableRow">
                        <label for="prezzo">Prezzo</label>
                        <div>
                            <input type="text" id="prezzo" name="prezzo" value="<%= prezzo %>" required>
                        </div>
                    </div>
				</div> 
	         
				<div class="tableRow">
					<label></label>
					<div>
						<input type="hidden" name="idProdotto" value="<%= idProdotto %>">
						<input type="hidden" name="idSpecifiche" value="<%= idSpecifiche %>">
						<input type="submit" value="Modifica">
					</div>
				</div>
			</div>
	</form>
</body>
</html>