/**
 * 
 */
function loginValidation()
{
	
const name = document.getElementById("name").value;
const email = document.getElementById("email").value;
const password = document.getElementById("password").value;
if(name==""){
	alert("Invalid name")
}

if(email=="" || !email.includes("@")){
	alert('Invalid email!!!')
}
if(password==""){
	alert('Invalid password!!!')
}
}