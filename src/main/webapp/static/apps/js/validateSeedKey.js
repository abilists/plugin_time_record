function validateSeedKey(passwd, showTxt) {
	var intScore   = 0
	var pwdId = document.getElementById(showTxt);

	if(passwd.value.length > 15) {
		// console.log("seedKey must contain at least 16 characters!");
		intScore = (intScore+5)
    }
    re = /[0-9]/;
    if(re.test(passwd.value)) {
    	// console.log("seedKey must contain at least one number (0-9)!");
    	intScore = (intScore+5)
    }
    re = /[a-z]/;
    if(re.test(passwd.value)) {
    	// console.log("seedKey must contain at least one lowercase letter (a-z)!");
    	intScore = (intScore+5)
    }
    re = /[A-Z]/;
    if(re.test(passwd.value)) {
    	// console.log("seedKey must contain at least one uppercase letter (A-Z)!");
    	intScore = (intScore+5)
    }
    re = /[!,@,#,$,%,^,&,*,?,_,~]/;
    if(re.test(passwd.value)) {
    	// console.log("seedKey must contain at least one uppercase letter (!,@,#,$,%,^,&,*,?,_,~)!");
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
	else if (intScore > 15 && intScore < 17)
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

function validateConfirmeSeedKey() {
	var currentPwdId = document.getElementById("currentPwdId");
	var seedKeyId = document.getElementById("seedKeyId");
	var seedKey2Id = document.getElementById("seedKey2Id");

	if(isEmpty(currentPwdId.value)){
		currentPwdId.style.border = "1px solid red";
        return false;
	} else {
		currentPwdId.style.border = "";
	}

	if(isEmpty(seedKeyId.value)){
		seedKeyId.style.border = "1px solid red";
        return false;
	} else {
		seedKeyId.style.border = "";
	}

	if(isEmpty(seedKey2Id.value)){
		seedKey2Id.style.border = "1px solid red";
        return false;
	} else {
		seedKeyId.style.border = "";
	}

	if(seedKeyId.value != seedKey2Id.value){
		// how to convert javascript object to jquery object
		var $inputs = $(seedKey2Id);

        $inputs.css("border", "1px solid red");
        $inputs.popover('show');
        return false;
	}
	return true;
}
