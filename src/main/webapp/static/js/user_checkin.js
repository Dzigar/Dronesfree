$(function() {
		function checkin() {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
			var geocoder = new google.maps.Geocoder();
			var latitude;
			var longitude;
			var address;
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {
					geocoder.geocode({
						latLng : new google.maps.LatLng(
								position.coords.latitude,
								position.coords.longitude)
					}, function(responses) {
						if (responses && responses.length > 0) {
							latitude = position.coords.latitude;
							longitude = position.coords.longitude;
							address = responses[0].formatted_address;
							$.ajax({
								url : 'checkin',
								type : 'POST',
								method : 'post',
								dataType : 'json',
								data : {
									latitude : latitude,
									longitude : longitude,
									address : address
								},
								success : function(response) {
									alert(response);
								}
							})
						} else {
							alert('Error!');
						}
					});
				}, function() {
					alert("Cannot locate your position");
				});
			} else {
				alert("Browser doesn't support Geolocation");
			}
		}

		$('#checkinBtn').click(function() {
			checkin();
		});
	});