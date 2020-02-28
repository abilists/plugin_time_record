function exceptionKeyByObj(e) {
    if(e.keyCode==37 || e.keyCode==38 || 
    		e.keyCode==39 || e.keyCode==40 ) {
        return false;
    }
    return true;
}

var timeout;
var ajaxLastNum = 0;

/*
// Multi search function
// 1, Event
// 2. row number in autocomplete
// 3. Url for the local object
// 4. Page(different title)
// 5. This
// 6. Column name
*/
function interverKeystroke(e, num, url, kind, ojb, optionId) {
	clearTimeout(timeout);
	timeout = setTimeout(function() {
		autoSrhItem(e, num, url, kind, ojb, optionId);
	}, 500);

}


/*
// Multi search function
// 1, Event
// 2. row number in autocomplete
// 3. Url for the local object
// 4. Page(different title)
// 5. This
// 6. Column name
*/
var showStringSize = 30;
function autoSrhItem(e, num, url, kind, ojb, optionId) {

	if(!exceptionKeyByObj(e)) {
		return false;
	}

	$(document).ready(function() {

		// Option Item
		var optionItem = "";

		if (!isEmpty(optionId)) {
			optionItem = document.getElementById(optionId);
		}

		// Make a object
		var $inputSname = $('#searchForm').find('input[name=' + ojb.name + ']:eq(' + num + ')');
		// Validate value's length
		if($inputSname.val().length < 2) {
			return false;
		}
		$inputSname.popover('destroy');

		var availableTags = [];
		var currentAjaxNum = ajaxLastNum;

		var para = '{"' + optionItem.name + '" : "' + optionItem.value + '", "' + 
			$inputSname.attr("name") + '" : "' + $inputSname.val().replace(/"/g, '\\"') + '"}';

        $.ajax({
            type: 'POST',
            url: url,
            contentType: "application/json",
            dataType: "json",
            data: para,
            cache: false,
            beforeSend: function(xhr, settings) {
            	ajaxLastNum = ajaxLastNum + 1;
            	$('#search' + num).addClass('input-spinner');
            },
            success: function(data, textStatus, request) {
            	$('#search' + num).removeClass('input-spinner');
            	if(currentAjaxNum == ajaxLastNum - 1) {
                	if(!isBlank(data)) {
                		// console.log(data);
                		availableTags = data;
                		$('#statuses').html('<li>' + data + '</li>');
                	}
                	var availableNames = [];
                	for (var i in availableTags) {
                		availableNames[i] = availableTags[i].map1;
                	}
                	$inputSname.autocomplete();
                    // Close if already visible
                	if ($inputSname.autocomplete("widget").is(":visible")) {
                		$inputSname.autocomplete("close");
                		return false;
                	}
                	$inputSname.autocomplete({
                		source: availableTags,
                		autoFocus: false, 
                		minLength: 0,
                		close: function( event, ui ) {},
                		open: function( event, ui ) {return true;},
                		search: function( event, ui ) {return true;},
                		focus: function( event, ui ) {return false;},
                		select: function( event, ui ) {
                			if(kind == "users") {
                    			if(optionItem.value == "userId") {
                    				$inputSname.val( ui.item.userId );
                    			}
                    			if(optionItem.value == "userName") {
                    				$inputSname.val( ui.item.userName );
                    			}
                    			if(optionItem.value == "userEmail") {
                    				$inputSname.val( ui.item.userEmail );
                    			}
                    		} else if(kind == "projects") {
                    			if(optionItem.value == "userId") {
                    				$inputSname.val( ui.item.userId );
                    			}
                    			if(optionItem.value == "upName") {
                    				$inputSname.val( ui.item.upName );
                    			}
                    		} else if(kind == "projectTech") {
                    			if(optionItem.value == "userId") {
                    				$inputSname.val( ui.item.userId );
                    			}
                    			if(optionItem.value == "uptKind") {
                    				$inputSname.val( ui.item.uptKind );
                    			}
                    			if(optionItem.value == "uptDetail") {
                    				var str = ui.item.uptDetail;
                    				var res = str.substring(0, showStringSize);
                    				$inputSname.val( res );
                    			}
                    		} else if(kind == "tasks") {
                    			if(optionItem.value == "userId") {
                        			$inputSname.val( ui.item.userId );
                    			}
                    			if(optionItem.value == "utProgress") {
                        			$inputSname.val( ui.item.utProgress );
                    			}
                    			if(optionItem.value == "utTitle") {
                    				$inputSname.val( ui.item.utTitle );
                    			}
                    		} else if(kind == "tech") {
                    			if(optionItem.value == "msSkill") {
                    				$inputSname.val( ui.item.msSkill );
                    			}
                    			if(optionItem.value == "msKind") {
                    				$inputSname.val( ui.item.msKind );
                    			}
                    			if(optionItem.value == "msName") {
                    				$inputSname.val( ui.item.msName );
                    			}
                    			if(optionItem.value == "msVersion") {
                    				$inputSname.val( ui.item.msVersion );
                    			}
                    			if(optionItem.value == "msExplain") {
                    				$inputSname.val( ui.item.msExplain );
                    			}
                    		} else if(kind == "role") {
                    			if(optionItem.value == "mrName") {
                    				$inputSname.val( ui.item.mrName );
                    			}
                    			if(optionItem.value == "mrCode") {
                    				$inputSname.val( ui.item.mrCode );
                    			}
                    			if(optionItem.value == "mrSkills") {
                    				$inputSname.val( ui.item.mrSkills );
                    			}
                    			if(optionItem.value == "mrPrefers") {
                    				$inputSname.val( ui.item.mrPrefers );
                    			}
                    		} else if(kind == "industry") {
                    			if(optionItem.value == "miLargeCategory") {
                        			$inputSname.val( ui.item.miLargeCategory );
                    			}
                    			if(optionItem.value == "miMiddleCategory") {
                    				$inputSname.val( ui.item.miMiddleCategory );
                    			}
                    			if(optionItem.value == "miCode") {
                    				$inputSname.val( ui.item.miCode );
                    			}
                    		} else if(kind == "reports") {
                    			if(optionItem.value == "userId") {
                        			$inputSname.val( ui.item.userId );
                    			}
                    		} else if(kind == "memo") {
                    			if(optionItem.value == "userId") {
                        			$inputSname.val( ui.item.userId );
                    			}
                    		} else {
                    			console.log("Error search in select");
                    		}
                			return false;
                		}
                	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
                		var dataValue;
                		if(kind == "users") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.userId );
                			uiAuto.append('<img src="' + item.userImgAvatar + '" height="50">');
                			if(optionItem.value == "userId") {
                    			uiAuto.append('<span>' + item.userId + '</span>');
                			}
                			if(optionItem.value == "userName") {
                				uiAuto.append('<span>' + item.userName + '</span>');
                			}
                			if(optionItem.value == "userEmail") {
                				uiAuto.append('<span>' + item.userEmail + '</span>');
                			}
                		} else if(kind == "projects") {

                			var uiAuto = $( "<li>" ).attr( "data-value", item.userId );

                			uiAuto.append('<img src="' + item.userImgAvatar + '" height="50">');
                			if(optionItem.value == "userId") {
                				uiAuto.append('<span>' + item.userId + '</span>');
                			}
                			if(optionItem.value == "upName") {
                				uiAuto.append('<span>' + item.upName + '</span>');
                			}
                			if(optionItem.value == "upExplain") {
                				var str = item.upExplain;
                				var res = str.substring(0, showStringSize);
                				uiAuto.append('<span>' + res + '</span>');
                			}
                		} else if(kind == "projectTech") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.userId );
                			uiAuto.append('<img src="' + item.userImgAvatar + '" height="50">');
                			if(optionItem.value == "userId") {
                				uiAuto.append('<span>' + item.userId + '</span>');
                			}
                			if(optionItem.value == "uptKind") {
                				uiAuto.append('<span>' + item.uptKind + '</span>');
                			}
                			if(optionItem.value == "uptDetail") {
                				var str = item.uptDetail;
                				var res = str.substring(0, showStringSize);
                				uiAuto.append('<span>' + res + '</span>');
                			}
                			if(optionItem.value == "uptStatus") {
                				uiAuto.append('<span>' + item.uptStatus + '</span>');
                			}
                		} else if(kind == "tasks") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.userId );
                			uiAuto.append('<img src="' + item.userImgAvatar + '" height="50">');
                			if(optionItem.value == "userId") {
                				uiAuto.append('<span>' + item.userId + '</span>');
                			}
                			if(optionItem.value == "utProgress") {
                				uiAuto.append('<span>' + item.utProgress + '</span>');
                			}
                			if(optionItem.value == "utTitle") {
                				uiAuto.append('<span>' + item.utTitle + '</span>');
                			}
                			if(optionItem.value == "utTask") {
                				var str = item.utTask;
                				var res = str.substring(0, showStringSize);
                				uiAuto.append('<span>' + res + '</span>');
                			}
                		} else if(kind == "tech") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.msSkill );
                			if(optionItem.value == "msSkill") {
                				uiAuto.append('<span>' + item.msSkill + '</span>');
                			}
                			if(optionItem.value == "msKind") {
                				uiAuto.append('<span>' + item.msKind + '</span>');
                			}
                			if(optionItem.value == "msName") {
                				uiAuto.append('<span>' + item.msName + '</span>');
                			}
                			if(optionItem.value == "msVersion") {
                				uiAuto.append('<span>' + item.msVersion + '</span>');
                			}
                			if(optionItem.value == "msExplain") {
                				uiAuto.append('<span>' + item.msExplain + '</span>');
                			}
                		} else if(kind == "role") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.mrName );
                			if(optionItem.value == "mrName") {
                				uiAuto.append('<span>' + item.mrName + '</span>');
                			}
                			if(optionItem.value == "mrCode") {
                				uiAuto.append('<span>' + item.mrCode + '</span>');
                			}
                			if(optionItem.value == "mrSkills") {
                				uiAuto.append('<span>' + item.mrSkills + '</span>');
                			}
                			if(optionItem.value == "mrPrefers") {
                				uiAuto.append('<span>' + item.mrPrefers + '</span>');
                			}
                		} else if(kind == "industry") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.miLargeCategory );
                			if(optionItem.value == "miLargeCategory") {
                				uiAuto.append('<span>' + item.miLargeCategory + '</span>');
                			}
                			if(optionItem.value == "miMiddleCategory") {
                				uiAuto.append('<span>' + item.miMiddleCategory + '</span>');
                			}
                			if(optionItem.value == "miCode") {
                				uiAuto.append('<span>' + item.miCode + '</span>');
                			}
                		} else if(kind == "reports") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.userId );
                			uiAuto.append('<img src="' + item.userImgAvatar + '" height="50">');
                			if(optionItem.value == "userId") {
                				uiAuto.append('<span>' + item.userId + '</span>');
                			}
                			if(optionItem.value == "urReport") {
                				var str = item.urReport;
                				var res = str.substring(0, showStringSize);
                				uiAuto.append('<span>' + res + '</span>');
                			}
                		} else if(kind == "memo") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.userId );
                			uiAuto.append('<img src="' + item.userImgAvatar + '" height="50">');
                			if(optionItem.value == "userId") {
                				uiAuto.append('<span>' + item.userId + '</span>');
                			}
                			if(optionItem.value == "umMemo") {
                				var str = item.umMemo;
                				var res = str.substring(0, showStringSize);
                				uiAuto.append('<span>' + res + '</span>');
                			}
                		} else {
                			console.log("Error search in data");
                		}
            			uiAuto.append('</li>');
            			dataValue = uiAuto.appendTo( ul );

                        return dataValue;
                    };
    	            // fire search event
                	$inputSname.autocomplete("search", "");
                	$inputSname.focus();
            	}
            },
            complete: function(xhr, textStatus) {
            	//$inputSname.attr('disabled', false);
            },
            error: function(xhr, status) {
                var contentType = xhr.getResponseHeader("Content-Type");
                if (xhr.status === 200 && contentType.toLowerCase().indexOf("text/html") >= 0) {
                    // Login has expired - reload our current page
                    window.location.reload();
                }
            }
        });
	});
}
