
	$notifications = $('.notifications');
	function notify(text) {
		var $notification = $('<li />').text(text).css({
			left: 320
		})
	    $notifications.append($notification)
	    $notification.animate({
	      left: 0
	    }, 300, function() {
	      $(this).delay(10000).animate({
	        left: 320
	      }, 200, function() {
	        $(this).slideUp(100, function() {
	          $(this).remove()
	        })
	      })
	    })
	}