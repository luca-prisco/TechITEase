document.addEventListener("DOMContentLoaded", function() {
    var specificheArray = JSON.parse(specificheJson);

    function updateProductPage() {
        var selectedColor = document.querySelector('input[name="colori"]:checked').value;
        var selectedHDD = document.querySelector('input[name="hdds"]:checked').value;
        var selectedRAM = document.querySelector('input[name="rams"]:checked').value;

        var prodotto = specificheArray.find(function(spec) {
            return (spec.colore == selectedColor) && (spec.hdd == selectedHDD) && (spec.ram == selectedRAM);
        });

        if (prodotto) {
            var url = './getPicture?id1=' + prodotto.IDProdotto + '&id2=' + prodotto.IDSpecifiche;
            var xhr = new XMLHttpRequest();
            xhr.open('GET', url, true);
            xhr.responseType = 'blob';

            xhr.onload = function() {
                if (xhr.status === 200) {
                    var blob = xhr.response;
                    var imageUrl = URL.createObjectURL(blob);
                    document.getElementById('productImage').src = imageUrl;
                } else {
                    console.error('Errore durante il recupero dell\'immagine:', xhr.statusText);
                }
            };
            
            xhr.send();
            
            document.querySelector('input[name="idProdCart"]').value = prodotto.IDProdotto;
            document.querySelector('input[name="idSpecCart"]').value = prodotto.IDSpecifiche;
        }
    }

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
});

