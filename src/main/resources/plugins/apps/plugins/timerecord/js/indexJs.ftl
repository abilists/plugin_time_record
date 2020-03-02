<script type="text/javascript">

$(document).ready(function(){
    $("#flip").click(function(){
        $("#newMdataFormId").slideUp("slow");
    });
});

function removeReports() {
	// Call the modal for deleting
	$(window).ready(function(){
		$('#sbtFormDltSurvey').modal('show')
	});
}

function validateForm(tableName) {

	var blnPopover = true;
	var isError = true;

	var table = document.getElementById(tableName);

	var inputTag = table.getElementsByTagName("input");
	if(!validateField(inputTag)) {
		return false;
	}
	var textareaTag = table.getElementsByTagName("textarea");
	if(!validateField(textareaTag)) {
		return false;
	}

	return true;
}

/*
 * Make the table on the Modal for confirming the data.
 */
function confirmData(tableName) {

	if(!validateForm(tableName)) {
		return;
	}

	// Call the modal
	$(window).ready(function(){
		$('#sbtFormUserSurvey').modal('show')
	});

	if(tableName == "newFormId") {
		document.getElementById("submitForm").setAttribute( "onClick", "javascript: submitNewFormUserReports();" );
	} else {
		document.getElementById("submitForm").setAttribute( "onClick", "javascript: submitUpdateFormUserReports();" );
	}

}

function clearSurvey() {
	var urSurveyId = document.getElementById("urSurveyId");
	urSurveyId.value = "";
}

var ajaxLastNum = 0;

var table = document.getElementById("userSurveyId");
var tr = table.getElementsByTagName("tr");

function clearBackGroundColor() {
	for(var j=0; j< tr.length; j++) {
		tr[j].style.backgroundColor = "";
	}
}

var urNoInput = document.getElementById("urNoId");
var tokenInput = document.getElementById("tokenId");

function selectTimeRecord(x, utrWorkDay) {

	clearBackGroundColorUl("userTimeRecordId");
	$("#udtMdataFormId").insertAfter($(x));

	$(document).ready(function() {

		var availableKeys;
		var currentAjaxNum = ajaxLastNum;

        $.ajax({
            type: 'POST',
            url: 'sltTimeRecordAjax',
            contentType: "application/json",
            dataType: "json",
            data: '{ "utrWorkDay" : "' + utrWorkDay + '"}',
            cache: false,
            beforeSend: function(xhr, settings) {
            	console.log("before send");
            },
            success: function(data, textStatus, request) {
            	if(!isBlank(data)) {
            		// Sequence Number
            		// urNoInput.value = data.urNo;
            		
            		
            		// Sequence Number
            		urNoInput.value = data.urNo;

            		// Work Day
            		var workDay = new Date(data.urWorkDay);
            		// urWorkDayInput.value = workDay.toISOString().substr(0,10);
            		var dd = workDay.getDate();
            		// January is 0!
            		var mm = workDay.getMonth()+1;
            		var yyyy = workDay.getFullYear();
            		if(dd<10){
            		    dd='0'+dd
            		} 
            		if(mm<10){
            		    mm='0'+mm
            		} 
            		var today = yyyy + '-' + mm + '-' + dd;
            		urWorkDayInput.value = today;
            		// Working hours
            		urWorkHourInput.value = data.urWorkHour;

            		// Reports for report
            		urReportsInput.value = data.urReport;
            		tokenInput.value = data.token;

            	}
            },
            complete: function(xhr, textStatus) {
            	console.log("complete");

            	formSlideDown();
            	newFormCancel();

            	x.style.backgroundColor = "#9999CC";
            	x.style.color= "#fff";
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
	});
}

function newFormToggle() {
	$("#newMdataFormId").slideToggle("slow");
	$("#newToggleId").toggleClass('glyphicon-chevron-down glyphicon-chevron-up');

//	urSurveyInput.value = "";

	clearBackGroundColorUl("userSurveyId");
	updateFormCancel();
}

function formSlideDown() {
	$("#udtMdataFormId").slideDown("slow");
}

function newFormCancel() {
	$("#newMdataFormId").slideUp("slow");
	$("#newToggleId").addClass('glyphicon-chevron-down').removeClass('glyphicon-chevron-up');
	clearBackGroundColorUl("userSurveyId");
}

function updateFormCancel() {
	$("#udtMdataFormId").slideUp("slow");
	clearBackGroundColorUl("userSurveyId");
}

function submitNewFormUserReports() {
	document.newForm.submit();
}

function submitUpdateFormUserReports() {
	document.updateForm.submit();
}

/*
 * Remove a survey
 */
function submitDeleteFormReports() {
	document.updateForm.action = "/plugins/dltSurvey";
	document.updateForm.submit();
}

</script>