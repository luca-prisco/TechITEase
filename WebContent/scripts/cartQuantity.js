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

// Chiamata per aggiornare il totale del carrello all'avvio della pagina
updateTotalCart();