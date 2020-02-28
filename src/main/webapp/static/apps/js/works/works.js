var resizeButton = document.getElementById("resizeButtonId");

/* Extend size on the textarea */
function fullScreen(inputId, stringSizeId, height) {
	// The object of the textarea tag.
	var inputObj = document.getElementById(inputId);
	// Extend the input window size.
	inputObj.style.height = "550px";
    // Move it to the modal window
    $("#" + inputId).appendTo($("#taModalId"));

    // Change parameter of the javascript function
    resizeButton.setAttribute( "onClick", "javascript: originScreen('" + inputId + "','" + stringSizeId + "', '" + height + "');" );

	// Call the modal
	$(window).ready(function(){
		$('#fullScreenModal').modal('show');
	});

}

/* Return the original size on the textarea */
function originScreen(inputId, stringSizeId, height) {
	// The object of the textarea tag.
	var inputObj = document.getElementById(inputId);
	// Resize the input window size.
	inputObj.style.height = height;
	// Return it to the original place.
    $("#" + inputId).insertAfter($("#" + stringSizeId));
}
