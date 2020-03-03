<script type="text/javascript">

$( function() {
  $( "#utrWorkDayId" ).datepicker({
      showButtonPanel: true, 
      currentText: '오늘 날짜', 
      closeText: '닫기',
      dateFormat: "yy-mm-dd",
      nextText: '다음 달',
      prevText: '이전 달',
      dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
      dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
  });
});

function validateForm(table) {

	var blnPopover = true;
	var isError = true;

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

	var table = document.getElementById(tableName);
	
	if(!validateForm(table)) {
		return;
	}
	table.submit();
}

var utrNoInput = document.getElementById("utrNoId");
var utrKindInput = document.getElementById("utrKindId");
var utrWorkDayInput = document.getElementById("utrWorkDayId");
var utrStartTimeInput = document.getElementById("utrStartTimeId");
var utrEndTimeInput = document.getElementById("utrEndTimeId");
var utrCommentInput = document.getElementById("utrCommentId");
var tokenInput = document.getElementById("tokenId");

function selectTimeRecord(x, utrWorkDay) {

	$("#udtMdataFormId").insertAfter($(x));

	$(document).ready(function() {

        $.ajax({
            type: 'POST',
            url: 'timerecord/sltTimeRecordAjax',
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
            		utrNoInput.value = data.utrNo;
            		utrKindInput.value = data.utrKind;

            		// Work Day
            		var workDay = new Date(data.utrWorkDay);
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
            		utrWorkDayInput.value = today;

            		// Start time
            		var startDay = new Date(data.utrStartTime);
            		var startHour = startDay.getHours();
            		var startMinutes = startDay.getMinutes();
            		var startSeconds = startDay.getSeconds();
            		if(startHour < 10){
            			startHour = '0' + startHour;
            		}
            		if(startMinutes < 10){
            			startMinutes = '0' + startMinutes;
            		}
            		if(startSeconds < 10){
            			startSeconds = '0' + startSeconds;
            		}
            		startDay = startHour + ":" + startMinutes + ":" + startSeconds;
            		utrStartTimeInput.value = startDay;
            		
            		// End time
            		var endDay = new Date(data.utrEndTime);
            		var endHour = endDay.getHours();
            		var endMinutes = endDay.getMinutes();
            		var endSeconds = endDay.getSeconds();
            		if(endHour < 10){
            			endHour = '0' + endHour;
            		}
            		if(endMinutes < 10){
            			endMinutes = '0' + endMinutes;
            		}
            		if(endSeconds < 10){
            			endSeconds = '0' + endSeconds;
            		}
            		endDay = endHour + ":" + endMinutes + ":" + endSeconds;
            		utrEndTimeInput.value = endDay;

            		// comment
            		utrCommentInput.value = data.utrComment;
            		tokenInput.value = data.token;
            	}
            },
            complete: function(xhr, textStatus) {
            	console.log("complete");
            	formSlideDown();

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

function formSlideDown() {
	$("#udtMdataFormId").slideDown("slow");
}

function updateFormCancel() {
	$("#udtMdataFormId").slideUp("slow");
	clearBackGroundColorUl("timeTableId");
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