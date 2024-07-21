const nomeProdottoPattern = /^[a-zA-Z0-9\s]{2,}$/;
const brandPattern = /^[a-zA-Z0-9\s]{2,}$/;
const categoriaPattern = /^[a-zA-Z\s]+$/;
const descrizionePattern = /^.{1,70}$/;
const dettagliPattern = /^.{1,45}$/;
const colorePattern = /^[a-zA-Z\s]{2,}$/;
const hddPattern = /^\d{1,4}$/;
const ramPattern = /^\d{1,4}$/;
const quantitaPattern = /^\d+$/;
const prezzoPattern = /^\d+(\.\d{1,2})?$/;

let count = 1;

const nomeProdottoErrorMessage = "Formato nome prodotto non valido";
const brandErrorMessage = "Formato brand non valido";
const categoriaErrorMessage = "Formato categoria non valido";
const descrizioneErrorMessage = "Formato descrizione non valido";
const dettagliErrorMessage = "Formato dettagli non valido"; 
const coloreErrorMessage = "Formato colore prodotto non valido";
const hddErrorMessage = "Formato hdd non valido";
const ramErrorMessage = "Formato ram non valido";
const quantitaErrorMessage = "Formato quantita'' non valido";
const prezzoErrorMessage = "Formato prezzo non valido"; 


function validate() {
	let valid = true;
	let form = document.getElementById("regForm");

	let spanNomeProdotto = document.getElementById("errorNomeProdotto");
	if (!validateFormElem(form.name, nomeProdottoPattern, spanNomeProdotto, nomeProdottoErrorMessage)) {
		valid = false;
	}
	
	let spanbrand = document.getElementById("errorBrand");
	if (!validateFormElem(form.lastname, brandPattern, spanbrand, brandErrorMessage)) {
		valid = false;
	}

	let spanCategoria = document.getElementById("errorCategoria");
	if (!validateFormElem(form.email, categoriaPattern, spanCategoria, categoriaErrorMessage)) {
		valid = false;
	}

	let spanDescrizione = document.getElementById("errorDescrizione");
	if (!validateFormElem(form.password, descrizionePattern, spanDescrizione, descrizioneErrorMessage)) {
		valid = false;
	}
	
	let spanDettagli = document.getElementById("errorDettagli");
	if (!validateFormElem(form.phone, dettagliPattern, spanDettagli, dettagliErrorMessage)) {
		valid = false;
	}
	
	let spanColore = document.getElementById("errorColore");
	if (!validateFormElem(form.phone, colorePattern, spanColore, coloreErrorMessage)) {
		valid = false;
	}
	
	let spanHdd = document.getElementById("errorHdd");
	if (!validateFormElem(form.phone, hddPattern, spanHdd, hddErrorMessage)) {
		valid = false;
	}
	
	let spanRam = document.getElementById("errorRam");
	if (!validateFormElem(form.phone, ramPattern, spanRam, ramErrorMessage)) {
		valid = false;
	}
	
	let spanQuantita = document.getElementById("errorQuantita");
	if (!validateFormElem(form.phone, quantitaPattern, spanQuantita, quantitaErrorMessage)) {
		valid = false;
	}

	let spanPrezzo = document.getElementById("errorPrezzo");
	if (!validateFormElem(form.phone, prezzoPattern, spanPrezzo, prezzoErrorMessage)) {
		valid = false;
	}

	return valid;
}

function validateFormElem(formElem, pattern, span, message) {
	if (formElem.value.match(pattern)) {
		formElem.style.border = "1px solid green";
		formElem.classList.remove("error");
		span.innerHTML = "";
		return true;
	}
	formElem.style.border = "1px solid red";
	formElem.classList.add("error");
	span.innerHTML = message;
	span.style.marginTop = '5px';
	span.style.fontSize = '13px';
	span.style.color = 'red';
	return false;
}/**
 * 
 */