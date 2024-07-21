const nameOrLastnamePattern = /^[A-Za-z]{2,}$/;
const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
const phonePattern = /^[0-9]{10}$/;

let count = 1;

const nameErrorMessage = "Fomrato nome non valido";
const lastnameErrorMessage = "Formato cognome non valido";
const emailErrorMessage = "Formato email non valido";
const passwordErrorMessage = "Formato password non valido";
const phoneErrorMessage = "Formato telefono non valido"; 


function validate() {
	let valid = true;
	let form = document.getElementById("regForm");

	let spanName = document.getElementById("errorName");
	if (!validateFormElem(form.name, nameOrLastnamePattern, spanName, nameErrorMessage)) {
		valid = false;
	}
	
	let spanLastname = document.getElementById("errorLastname");
	if (!validateFormElem(form.lastname, nameOrLastnamePattern, spanLastname, lastnameErrorMessage)) {
		valid = false;
	}

	let spanEmail = document.getElementById("errorEmail");
	if (!validateFormElem(form.email, emailPattern, spanEmail, emailErrorMessage)) {
		valid = false;
	}

	let spanPassword = document.getElementById("errorPassword");
	if (!validateFormElem(form.password, passwordPattern, spanPassword, passwordErrorMessage)) {
		valid = false;
	}
	
	let spanPhone = document.getElementById("errorPhone");
	if (!validateFormElem(form.phone, phonePattern, spanPhone, phoneErrorMessage)) {
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
}