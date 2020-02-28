function exceptionKeyByObj(e) {
    if(e.keyCode==37 || e.keyCode==38 || 
    		e.keyCode==39 || e.keyCode==40 ) {
        return false;
    }
    return true;
}

var timeout;
var ajaxLastNum = 0;

function interverKeystroke(e, num, url, ojb) {

	clearTimeout(timeout);
	timeout = setTimeout(function() {
		autoSrhItem(e, num, url, ojb);
	}, 500);

}

/*
// Multi search function
// 1, Local object
// 2. Array number
// 3. Url for the local object
// 4. This
*/
function autoSrhItem(e, num, url, ojb) {

	if(!exceptionKeyByObj(e)) {
		return false;
	}

	$(document).ready(function() {
		// Make a object
		var $inputSname = $('#searchForm').find('input[name=' + ojb.name + ']:eq(' + num + ')');
		// Validate value's length
		if($inputSname.val().length < 2) {
			return false;
		}
		$inputSname.popover('destroy');

		var availableTags = [];
		var currentAjaxNum = ajaxLastNum;

        $.ajax({
            type: 'POST',
            url: url,
            contentType: "application/json",
            dataType: "json",
            data: '{"' + $inputSname.attr("name") + '" : "' + $inputSname.val() + '"}',
            cache: false,
            beforeSend: function(xhr, settings) {
            	ajaxLastNum = ajaxLastNum + 1;
            	$('#search' + num).addClass('input-spinner');
            },
            success: function(data, textStatus, request) {
            	$('#search' + num).removeClass('input-spinner');
            	if(currentAjaxNum == ajaxLastNum - 1) {
                	if(!isBlank(data)) {
                		console.log(data);
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
                	$inputSname.autocomplete({source: availableTags, 
                		autoFocus: false, 
                		minLength: 0,
                		create: function( event, ui ) {
                		    if($(this).autocomplete('widget').is(':visible')) {
                		    	console.log(" create >> visible");
                		    } else {
                		    	console.log(" create >> desable");
                		    }
                			return true;
                		},
                		close: function( event, ui ) {},
                		open: function( event, ui ) {return true;},
                		search: function( event, ui ) {return true;},
                		focus: function( event, ui ) {return false;},
                		select: function( event, ui ) {
                    		if(ojb.name == "srhContents") {
                				var str = ui.item.umMemo;
                				var res = str.substring(0, 30).replace(/<br\s*\/?>/mg, "\n");
                				$inputSname.val( res );
                    		} else {
                    			console.log("Error search in select");
                    		}
                			return false;
                		}
                	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
                		var uiAuto;
                		if(ojb.name == "srhContents") {
                			var uiAuto = $( "<li>" ).attr( "data-value", item.userId );
            				var str = item.umMemo;
            				var res = str.substring(0, 30).replace(/<br\s*\/?>/mg, "\n");
            				uiAuto.append('<span>' + res + '</span>');

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

