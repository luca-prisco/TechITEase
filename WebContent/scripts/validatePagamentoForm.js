const nomeCartaPattern = /^[a-zA-Z\s]{2,}$/;
const cognomeCartaPattern = /^[a-zA-Z\s]{2,}$/;
const numeroCartaPattern = /^\d{13,19}$/;
const scadenzaCartaPattern = /^(0[1-9]|1[0-2])\/\d{2}$/;
const cvvPattern = /^\d{3,4}$/;
const viaPattern = /^[a-zA-Z0-9\s]{2,}$/;
const civicoPattern = /^[0-9a-zA-Z]+$/;
const capPattern = /^\d{5}$/;
const cittaPattern = /^[a-zA-Z\s]{2,}$/;

let count = 1;

const nomeCartaErrorMessage = "Fomrato nome non valido";
const cognomeCartaErrorMessage = "Formato cognome non valido";
const numeroCartaErrorMessage = "Formato numero non valido";
const scadenzaCartaErrorMessage = "Formato scadenza non valido";
const cvvErrorMessage = "Formato cvv non valido"; 
const viaErrorMessage = "Fomrato via non valido";
const civicoErrorMessage = "Formato civico non valido";
const capErrorMessage = "Formato cap non valido";
const cittaErrorMessage = "Formato citta' non valido"; 


function validate() {
	let valid = true;
	let form = document.getElementById("regForm");

	let spanNomeCarta = document.getElementById("errorNomeCarta");
	if (!validateFormElem(form.nomeCarta, nomeCartaPattern, spanNomeCarta, nomeCartaErrorMessage)) {
		valid = false;
	}
	
	let spanCognomeCarta = document.getElementById("errorCognomeCarta");
	if (!validateFormElem(form.cognomeCarta, cognomeCartaPattern, spanCognomeCarta, cognomeCartaErrorMessage)) {
		valid = false;
	}

	let spanNumeroCarta = document.getElementById("errorNumeroCarta");
	if (!validateFormElem(form.numeroCarta, numeroCartaPattern, spanNumeroCarta, numeroCartaErrorMessage)) {
		valid = false;
	}

	let spanScadenzaCarta = document.getElementById("errorScadenzaCarta");
	if (!validateFormElem(form.scadenzaCarta, scadenzaCartaPattern, spanScadenzaCarta, scadenzaCartaErrorMessage)) {
		valid = false;
	}
	
	let spanCvv = document.getElementById("errorCvv");
	if (!validateFormElem(form.cvv, cvvPattern, spanCvv, cvvErrorMessage)) {
		valid = false;
	}
	
	let spanVia = document.getElementById("errorVia");
	if (!validateFormElem(form.via, viaPattern, spanVia, viaErrorMessage)) {
		valid = false;
	}
	
	let spanCivico = document.getElementById("errorCivico");
	if (!validateFormElem(form.civico, civicoPattern, spanCivico, civicoErrorMessage)) {
		valid = false;
	}
	
	let spanCap = document.getElementById("errorCap");
	if (!validateFormElem(form.cap, capPattern, spanCap, capErrorMessage)) {
		valid = false;
	}
	
	let spanCitta = document.getElementById("errorCitta");
	if (!validateFormElem(form.citta, cittaPattern, spanCitta, cittaErrorMessage)) {
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