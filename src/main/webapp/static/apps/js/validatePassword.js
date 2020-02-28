function validatePassword(passwd, showTxt) {
	var intScore   = 0
	var pwdId = document.getElementById(showTxt);

	if(passwd.value.length > 6) {
		// console.log("Password must contain at least six characters!");
		intScore = (intScore+5)
    }
    re = /[0-9]/;
    if(re.test(passwd.value)) {
    	// console.log("Password must contain at least one number (0-9)!");
    	intScore = (intScore+5)
    }
    re = /[a-z]/;
    if(re.test(passwd.value)) {
    	// console.log("Password must contain at least one lowercase letter (a-z)!");
    	intScore = (intScore+5)
    }
    re = /[A-Z]/;
    if(re.test(passwd.value)) {
    	// console.log("Password must contain at least one uppercase letter (A-Z)!");
    	intScore = (intScore+5)
    }
    // Not used
    re = /[!,@,#,$,%,^,&,*,?,_,~]/;
    if(re.test(passwd.value)) {
    	// console.log("Password must contain at least one uppercase letter (!,@,#,$,%,^,&,*,?,_,~)!");
		intScore = (intScore+2)
	}
	if(intScore < 6)
	{
	   strVerdict = "very weak"
	}
	else if (intScore > 6 && intScore < 11)
	{
	   strVerdict = "weak"
	}
	else if (intScore > 11 && intScore < 16)
	{
	   strVerdict = "mediocre"
	}
	else if (intScore > 16 && intScore < 21)
	{
	   strVerdict = "strong"
	}
	else
	{
	   strVerdict = "stronger"
	}

	pwdId.innerHTML = strVerdict;
	pwdId.style.display = "block";

	return true;
}

function validateConfirmePassword() {
	var currentPwdId = document.getElementById("currentPwdId");
	var passwordId = document.getElementById("passwordId");
	var password2Id = document.getElementById("password2Id");

	if(isEmpty(currentPwdId.value)){
		currentPwdId.style.border = "1px solid red";
        return false;
	} else {
		currentPwdId.style.border = "";
	}

	if(isEmpty(passwordId.value)){
		passwordId.style.border = "1px solid red";
        return false;
	} else {
		passwordId.style.border = "";
	}

	if(isEmpty(password2Id.value)){
		password2Id.style.border = "1px solid red";
        return false;
	} else {
		password2Id.style.border = "";
	}

	if(passwordId.value != password2Id.value){
		// how to convert javascript object to jquery object
		var $inputs = $(password2Id);

        $inputs.css("border", "1px solid red");
        $inputs.popover('show');
        return false;
	}
	return true;
}