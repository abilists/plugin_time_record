'use strict';

window.chartColors = {
	red: 'rgba(255, 99, 132,0.7)',
	orange: 'rgba(255, 159, 64,0.7)',
	yellow: 'rgba(255, 205, 86,0.7)',
	green: 'rgba(75, 192, 192,0.7)',
	blue: 'rgba(54, 162, 235,0.7)',
	purple: 'rgba(153, 102, 255,0.7)',
	grey: 'rgba(201, 203, 207,0.7)'
};

/*
function checkSizeTextarea(obj, maxLength){
	$(obj).textcounter({
		type                     : "character",            // "character" or "word"
		max                      : maxLength,                    // maximum number of characters/words, -1 for unlimited, 'auto' to use maxlength attribute
		countContainerElement    : "div",        		   // HTML element to wrap the text count in
		countContainerClass      : "text-count-wrapper",   // class applied to the countContainerElement
		textCountClass           : "text-count",
		counterText              : "Total Count: %d/" + maxLength,      // counter text
		maximumErrorText         : "Maximum exceeded",     // error message for maximum range exceeded,
		stopInputAtMaximum       : false,                  // stop further text input if maximum reached
		countSpaces              : true,                   // count spaces as character (only for "character" type)
		twoCharCarriageReturn    : false,				   // count carriage returns/newlines as 2 characters
		countExtendedCharacters  : true                    // count extended UTF-8 characters as 2 bytes (such as Chinese characters)
	});
}
*/

/**
 *  Check max length as byte.
 * 
 * @param id : tag id 
 * @param title : tag title
 * @param maxLength : max byte
 * @returns {Boolean}
 */
function maxLengthCheck(id, title, maxLength){
     var obj = $("#"+id);
     if(maxLength == null) {
         maxLength = obj.attr("maxLength") != null ? obj.attr("maxLength") : 1000;
     }
     
     if(Number(byteCheck(obj)) > Number(maxLength)) {
         console.log (title + "Maximum exceeded.\n(English, Number, nomarl special charaters : " + maxLength + " / Hangul, Hanja, etc special charaters : " + parseInt(maxLength/2, 10) + ").");
         obj.focus();
         return false;
     } else {
         return true;
    }
}

/**
 * Count byte size
 * 
 * @param el : tag jquery object
 * @returns {Number}
 */
function byteCheck(el){
    var codeByte = 0;
    for (var idx = 0; idx < el.val().length; idx++) {
        var oneChar = escape(el.val().charAt(idx));
        if ( oneChar.length == 1 ) {
            codeByte ++;
        } else if (oneChar.indexOf("%u") != -1) {
            codeByte += 2;
        } else if (oneChar.indexOf("%") != -1) {
            codeByte ++;
        }
    }
    return codeByte;
}
