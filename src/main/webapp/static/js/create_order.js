var geocoder = new google.maps.Geocoder();

	function geocodePosition(pos) {
		geocoder
				.geocode(
						{
							latLng : pos
						},
						function(responses) {
							if (responses && responses.length > 0) {
								updateMarkerAddress(responses[0].formatted_address);
							} else {
								updateMarkerAddress('Cannot determine address at this location.');
							}
						});
	}

	function updateMarkerPosition(latLng) {
		document.getElementById('latitude').value = latLng.lat();
		document.getElementById('longitude').value = latLng.lng();
	}

	function updateMarkerAddress(str) {
		document.getElementById('address').value = str;
	}

	function changeMarkerPosition(marker, latLng) {
		marker.setPosition(latLng);
		updateMarkerPosition(latLng);
		geocodePosition(latLng);
	}

	function handleLocationError(browserHasGeolocation, infoWindow, pos) {
		infoWindow.setPosition(pos);
		infoWindow.setContent(browserHasGeolocation ?
			'Error: The Geolocation service failed.' :
			'Error: Your browser doesn\'t support geolocation.');
	}

	function initialize(pos) {
		var latLng = new google.maps.LatLng(49.233083,28.468215);
		var map = new google.maps.Map(document.getElementById('mapCanvas'), {
			zoom : 14,
			center : latLng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		});
		var marker = new google.maps.Marker({
			position : latLng,
			map : map,
			draggable : true
		});
		
		// Update current position info.
		updateMarkerPosition(latLng);
		geocodePosition(latLng);

		// Add dragging event listeners.
		google.maps.event.addListener(marker, 'dragstart', function() {
			updateMarkerAddress('...');
		});

		google.maps.event.addListener(marker, 'drag', function() {
			updateMarkerPosition(marker.getPosition());
		});

		google.maps.event.addListener(marker, 'dragend', function() {
			geocodePosition(marker.getPosition());
		});

		document.getElementById('codeAddress').addEventListener('click',
				function() {
					geocodeAddress(geocoder, map, marker);
				});

		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				var pos = {
        			lat: position.coords.latitude,
        			lng: position.coords.longitude
      			};
				map.setCenter(pos);
				marker.setPosition(pos);
				geocodePosition(new google.maps.LatLng(pos.lat, pos.lng));
			}, function showError(error) {
			switch(error.code) {
				case error.PERMISSION_DENIED:
					console.dir("User denied the request for Geolocation.");
					break;
				case error.POSITION_UNAVAILABLE:
					console.dir("Location information is unavailable.");
					break;
				case error.TIMEOUT:
					console.dir("The request to get user location timed out.");
					break;
				case error.UNKNOWN_ERROR:
					console.dir("An unknown error occurred.");
					break;
				default: 
					console.dir(error.code);
					break;
			} 
		});
		}
	}

	function geocodeAddress(geocoder, resultsMap, marker) {
		var address = document.getElementById('address1').value;
		geocoder.geocode({
			'address' : address
		}, function(results, status) {
			if (status === google.maps.GeocoderStatus.OK) {
				resultsMap.setCenter(results[0].geometry.location);
				changeMarkerPosition(marker, results[0].geometry.location);
			} else {
				alert('Geocode was not successful for the following reason: '
						+ status);
			}
		});
	}

	// Onload handler to fire off the app.
	google.maps.event.addDomListener(window, 'load', initialize);