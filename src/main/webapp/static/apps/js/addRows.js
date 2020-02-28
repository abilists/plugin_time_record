
/*
 * Delete a input element which is selected by user
 */
function removeElement(tableName, cntName, trNum) {

	  var d = document.getElementById(tableName);
	  d.deleteRow(trNum);

	  var numi = document.getElementById(cntName);
	  var num = numi.value - 1;
	  numi.value = num;

	  // Reset numbers of TR tag.
	  resetId(tableName, cntName);
}

/*
 * Order the number on TR of the table
 */
function resetId(tableName, cntName) {

	  var table = document.getElementById(tableName);
	  var tr = table.getElementsByTagName("tr");
	  var rows = tr.length - 1;

	  var tagDivStart;
	  var tagDivEnd;
	  var inputHtml;
	  for(i=0; i < rows; i++) {
		  tr[i+1].id = i+1;
		  table.rows[i+1].cells[4].innerHTML = '<button type="button" onclick=\'removeElement("'+tableName+'", "'+cntName+'", "'+(i+1)+'")\'>Remove</button>';
	  }
}

/*
 * Make the table on the Modal for confirming the data.
 */
function confirmData(tableName,rowCnt) {

	  if(!validateForm(tableName)) {
		  return;
	  }

	  // Call the modal
	  $(window).ready(function(){
	  	$('#submitFormUserSettings').modal('show')
	  });

	  // The row count
	  var cntElements = document.getElementById(rowCnt);
	  var cnt = cntElements.value;

	  var table1 = document.getElementById(tableName);
	  // Create a table on Motal 
	  var table3 = document.getElementById("t03");

	  var selectSkillHtml;
	  var selectKindHtml, mtechs;
	  var selectLevelHtml;
	  var textareaTag, detailHtml;

	  
	  var row1, row3;
	  var cell31, cell32, cell33, cell34;
	  var selectLevelElement;

	  for(i=1; i <= cnt; i++) {
		  row1 = table1.rows[i];
		  row3 = table3.insertRow(i);

		  // Create cells
		  cell31 = row3.insertCell(0);
		  cell32 = row3.insertCell(1);
		  cell33 = row3.insertCell(2);
		  cell34 = row3.insertCell(3);

		  selectSkillHtml = row1.cells[0].getElementsByTagName("select")[0].value;
		  // Be mSkillsList in the page
		  mtechs = mSkillsList[selectSkillHtml];

		  selectKindHtml = row1.cells[1].getElementsByTagName("select")[0].value;
		  var j;
		  for (j = 0; j < mtechs.length; j++) {
			  if(selectKindHtml == mtechs[j].msNo) {
				  selectKindHtml = mtechs[j].msName;
				  break;
			  }
		  }

		  selectLevelElement = row1.cells[2].getElementsByTagName("select")[0];
		  selectLevelHtml = selectLevelElement.options[selectLevelElement.selectedIndex].text;

		  textareaTag = row1.cells[3].getElementsByTagName("textarea");
		  detailHtml = textareaTag[0].value;

		  cell31.innerHTML = selectSkillHtml;
		  cell32.innerHTML = selectKindHtml;
		  cell33.innerHTML = selectLevelHtml;
		  cell34.innerHTML = detailHtml;
	  }

}

/*
 * Check the validity of the data inputed.
 */
function validateForm(tableName) {

	var blnPopover = true;
	var isError = true;

	var table = document.getElementById(tableName);
	var inputTag = table.getElementsByTagName("select");

	console.log("inputTag size >>> " + inputTag.length);

	if(inputTag.length < 1) {
		alert("At least you have to add your skills");
		return false;
	}

	for (i=0; i < inputTag.length; i++) {
	    var x = inputTag[i].value;
	    if (x==null || x=="" || x=="0") {
	    	if(isError) {
	    		inputTag[i].focus();
	    		inputTag[i].scrollIntoView();
	    	}
	    	inputTag[i].style.border = "1px solid red";
	        isError = false;
	    } else {
	    	inputTag[i].style.border = "";
	    }
	}

	return isError;
}

/*
 * Submit form
 */
function submitform() {
	document.formUserTechSkill.submit();
}
