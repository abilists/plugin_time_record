window.onscroll = function() {headerMenu()};

var header = document.getElementById("myNavbar");
var sticky = header.offsetTop;

function headerMenu() {
	if (window.pageYOffset >= sticky) {
		 header.classList.add("sticky");
	} else {
		 header.classList.remove("sticky");
	}
}
