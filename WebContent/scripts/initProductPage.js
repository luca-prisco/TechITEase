document.addEventListener("DOMContentLoaded", function() {
    var specificheArray = JSON.parse(specificheJson);
    
    function initProductPage() {
        var idProdotto = document.getElementById('idProdotto').value;
        var idSpecCart = document.getElementById('idSpecifiche').value;

        var specifiche = specificheArray.find(function(spec) {
            return (spec.IDProdotto == idProdotto) && (spec.IDSpecifiche == idSpecCart);
        });

        if (specifiche) {
            if (specifiche.colore) {
                document.querySelector('input[name="colori"][value="' + specifiche.colore + '"]').checked = true;
            }
            if (specifiche.hdd) {
                document.querySelector('input[name="hdds"][value="' + specifiche.hdd + '"]').checked = true;
            }
            if (specifiche.ram) {
                document.querySelector('input[name="rams"][value="' + specifiche.ram + '"]').checked = true;
            }
        }
    }
    
    initProductPage();
});

