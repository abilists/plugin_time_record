
/* Show more contents */
function showMore(self, contentsId) {
	self.style.display = 'none';

	var showContentsId = document.getElementById(contentsId);
	showContentsId.style.height = 'auto';

	/*
	var lineHeight = showContentsId.offsetHeight;
	console.log("lineHeight >>> " + lineHeight);
	if (lineHeight > 100) {
		showContentsId.style.maxHeight = '100px';
	}
	*/
}

/* Validate */
function isEmpty(str) {
    return (!str || 0 === str.length);
}
function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}
function isImageFile(file) {
    if (file.type) {
        return /^image\/\w+$/.test(file.type);
      } else {
        return /\.(jpg|jpeg|png|gif)$/.test(file);
    }
}
/**
 * How to use 
 * onkeypress="return isNumber(event)"
 * 
 * @param {*} evt 
 */
function isNumber(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    	return false;
    }
    return true;
}

/**
 * Check and Get byte length of string.
 * 
 * @param x
 * @param showTagId
 * @param limitByte
 * @returns
 */
function checkByteLength(x, showTagId, limitByte) {
	var showTag = document.getElementById(showTagId);
	
	strByteLength = getByteLength(x.value,3,1);
	showTag.innerHTML = strByteLength;
	
	if(strByteLength >= limitByte) {
		showTag.style.color= "red";
	} else {
		showTag.style.color= "#707070";
	}
}
/*
 * https://programmingsummaries.tistory.com/239
 * https://gist.github.com/mathiasbynens/1010324
 * 
 * ex) getByteLength(x.value,3); 3 is byte.
 * */
function getByteLength(s,b,i,c){
    for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
    return b;
}
function getByteLength(s){
    return s.replace(/[\0-\x7f]|([0-\u07ff]|(.))/g,"$&$1$2").length;
}

function addLoadEvent(func){
	var oldonload = window.onload;
	if(typeof window.onload != 'function'){
		window.onload = func;
	}else{
		window.onload = function(){
			oldonload();
			func();
		};
	}
}

function fadeOut(element) {
    var op = 1;  // initial opacity
    var timer = setInterval(function () {
        if (op <= 0.1){
            clearInterval(timer);
            element.style.display = 'none';
        }
        element.style.opacity = op;
        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
        op -= op * 0.1;
    }, 150);
}

function captureReturnKey(evt) {
	
	var type;

    // Get type
    if (document.all) {
        type = evt.srcElement.type;
    } else {
    	type = evt.target.type;
    }

    if(evt.keyCode==13 && type != 'textarea') {
    	console.log("Entered in input tag.");
        return false;
    }

} 

function exceptionKey(e) {
    if(e.keyCode==37 || e.keyCode==38 || e.keyCode==39 || e.keyCode==40) {
        return false;    	
    }
    return true;
}

/* release popover */
function releasPopover(event) {
	$jevent = $(event);
	$jevent.popover('destroy');
}

/* Ajax with jquery
 * 
 * How to use
 *  
 * function udtUserSummaryAjax() {
 * 	var userSummaryTa = document.getElementById("userSummaryId");
 * 	var cdata = '{ "userSummary" : "' + userSummaryTa.value + '"}';
 * 	var curl = "/users/udtUserSummaryAjax";
 * 	var cresult = requestbyAjax(curl, cdata);
 * }
 * 
 */
function requestbyAjax(curl, cdata) {
	var result;
    $.ajax({
        type: 'POST',
        url: curl,
        contentType: "application/json",
        dataType: "json",
        data: cdata,
        cache: false,
        async: false,
        beforeSend: function(xhr, settings) {
        	xhr.setRequestHeader("Accept", "application/json");
        	xhr.setRequestHeader("Cache-Control","no-cache, must-revalidate");
        	xhr.setRequestHeader("Pragma","no-cache");
        },
        success: function(data, textStatus, request) {
        	// Set data to out side.
        	result = data;
        },
        complete: function(xhr, textStatus) {
        	console.log("complete");
        },
        error: function(xhr, status) {
        	console.log("error >> " + xhr.responseText);
            var contentType = xhr.getResponseHeader("Content-Type");
            if (xhr.status === 200 && contentType.toLowerCase().indexOf("text/html") >= 0) {
                // Login has expired - reload our current page
                window.location.reload();
            }
        }
    });

    return result;
}

/*
 * Validate input or select tags
 * isZero if can check 0
 */
function validateField(inputTag, isZero) {

	var isError = true;

	if(inputTag.length < 1) {
		alert("At least you have to add your skills");
		return false;
	}

	for (i=0; i < inputTag.length; i++) {
	    var x = inputTag[i].value;
	    if (x==null || x=="") {
	    	if(isError) {
	    		console.log("tag name in errors. name=" + inputTag[i].name);
	    		inputTag[i].focus();
	    		inputTag[i].scrollIntoView();
	    	}
	    	inputTag[i].style.border = "1px solid red";
	    	// inputTag[i].style.cssText = 'border:1px solid red !important';

	        isError = false;
	    } else {
	    	inputTag[i].style.border = "";
	    }

	    if (isZero && x=="0") {
    		console.log("0 is error. name=" + inputTag[i].name);
    		inputTag[i].focus();
    		inputTag[i].scrollIntoView();
	    	inputTag[i].style.border = "1px solid red";
	    	isError = false;
	    }

	}

	return isError;
}

/*
 * Validate a tag for single
 * isZero if can check 0
 */
function validateTag(inputTag, isZero) {

	var isError = true;
    var x = inputTag[0].value;

    if (x==null || x=="") {
    	if(isError) {
    		console.log("tag name in errors. name=" + inputTag[0].name);
    		inputTag[0].focus();
    		inputTag[0].scrollIntoView();
    	}
    	inputTag[0].style.border = "1px solid red";
        isError = false;
    } else {
    	inputTag[0].style.border = "";
    }

    if (isZero && x=="0") {
		console.log("0 is error. name=" + inputTag[0].name);
		inputTag[0].focus();
		inputTag[0].scrollIntoView();
		inputTag[0].style.border = "1px solid red";
		isError = false;
    }

    return isError;
}

/*
 * Validate number a tag for single
 * isZero if can check 0
 */
function validateNumTag(inputTag) {

	var isError = true;
    var x = inputTag[0].value;

    if (isNaN(x)) {
    	if(isError) {
    		inputTag[0].focus();
    		inputTag[0].scrollIntoView();
    	}
    	inputTag[0].style.border = "1px solid red";
        isError = false;
    } else {
    	inputTag[0].style.border = "";
    }

    return isError;
}

/*
 * Validate input or select by ID
 * isZero if can check 0
 */
function validateId(inputId, isZero) {

	var isError = true;
    var x = inputId.value;

    if (x==null || x=="") {
    	if(isError) {
    		inputId.focus();
    		inputId.scrollIntoView();
    	}
    	inputId.style.border = "1px solid red";
        isError = false;
    } else {
    	inputId.style.border = "";
    }

    if (isZero && x=="0") {
		console.log("0 is error. name=" + inputId.name);
		inputId.focus();
		inputId.scrollIntoView();
		inputId.style.border = "1px solid red";
		isError = false;
    }

    return isError;
}

/**
 * Clear input and select
 * @param {Id of table} table 
 */
function clearSearching(table) {
	var searchTable = document.getElementById(table);

	var inputTag = searchTable.getElementsByTagName("input");
	clearField(inputTag);

	var selectTag = searchTable.getElementsByTagName("select");
	clearField(selectTag);
}
function clearField(inputTag) {
	for (i=0; i < inputTag.length; i++) {
	    inputTag[i].value = "";
	}
}

/* 
 * tagNameId : a tag
 * x : this (select tag)
 * When a Select Tag is selected, It works  
 */

function sltDataList(x) {
	// var sltMtSkillId = document.getElementById(tagNameId);
	// sltMtSkillId.value = x.value;
	document.sltForm.submit();
}

function overChangeColor(obj) {
	obj.style.cursor = "pointer";
}

function outChangeColor(obj) {
	obj.style.cursor = "pointer";
}

function overChangeColor(obj, color) {
	obj.style.cursor = "pointer";
	obj.style.backgroundColor = color;
}

function outChangeColor(obj, color) {
	obj.style.cursor = "pointer";
	obj.style.backgroundColor = color;
}


/*
 * Delete all rows of the modal table
 */
function deleteTr(tableNameId) {
	var table = document.getElementById(tableNameId);
	var tableRows = table.getElementsByTagName('tr');

	for(var i = 1; i < tableRows.length; i++){
		tableRows[i].parentNode.removeChild(tableRows[i]);
		i--;
	}
}

function clearBackGroundColorTr(tableId) {
	var table = document.getElementById(tableId);
	var tr = table.getElementsByTagName("tr");

	for(var j=0; j< tr.length; j++) {
		tr[j].style.backgroundColor = "";
		tr[j].style.color = "";
	}
}

function clearBackGroundColorDiv(divId) {
	var div = document.getElementById(divId);
	var divRow = div.getElementsByTagName("div");

	for(var j=0; j< divRow.length; j++) {
		divRow[j].style.backgroundColor = "";
		divRow[j].style.color = "";
	}
}

function clearBackGroundColorUl(divId) {
	var divTable = document.getElementById(divId);
	var ulRow = divTable.getElementsByTagName("ul");

	for(var j=0; j< ulRow.length; j++) {
		ulRow[j].style.backgroundColor = "";
		ulRow[j].style.color = "";
	}
}

function rgbToHex(col)
{
    if(col.charAt(0)=='r')
    {
        col=col.replace('rgb(','').replace(')','').split(',');
        var r=parseInt(col[0], 10).toString(16);
        var g=parseInt(col[1], 10).toString(16);
        var b=parseInt(col[2], 10).toString(16);
        r=r.length==1?'0'+r:r; g=g.length==1?'0'+g:g; b=b.length==1?'0'+b:b;
        var colHex='#'+r+g+b;
        return colHex;
    }
}

/*
 * nameId -> tagId
 * tag -> child tag name
 * 
 * */
function clearStyle(nameId, tag) {
	var parentObj = document.getElementById(nameId);
	var childObj = parentObj.getElementsByTagName(tag);

	for(var j=0; j< childObj.length; j++) {
		childObj[j].style.border = "";
	}
}

/* Tool tip functions */
function showPopover(local, tagName) {
	$jlocal = $(local);
	$jlocal.popover('show');
}

function destroyPopover(event) {
	$jevent = $(event);
	$jevent.popover('destroy');
}

/*
function overChangeColor(obj) {
	obj.style.cursor = "pointer";
	obj.style.borderColor = "#e0e6f9";
	var deep1 = obj.childNodes;
	deep1[5].style.backgroundColor = "#e0e6f9";
}

function outChangeColor(obj) {
	obj.style.cursor = "pointer";
	obj.style.borderColor = "#ccc";
	var deep1 = obj.childNodes;
	deep1[5].style.backgroundColor = "#eee";
}
*/
