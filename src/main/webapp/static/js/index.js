var ws = new WebSocket('ws://' + location.host + '/broadcast/websocket');
var video;
var webRtcPeer;
var stompClient = null;
var roomId;
var customerName;
var performerName;
var selectedOrder;
var orderLng;
var orderLat;


window.onload = function() {
	video = document.getElementById('video');
	connect();
}

window.onbeforeunload = function() {
	ws.close();
}

ws.onmessage = function(message) {
	var parsedMessage = JSON.parse(message.data);
	console.info('Received message: ' + message.data);

	switch (parsedMessage.id) {
	case 'presenterResponse':
		presenterResponse(parsedMessage);
		break;

	case 'viewerResponse':
		viewerResponse(parsedMessage);
		break;

	case 'iceCandidate':
		webRtcPeer.addIceCandidate(parsedMessage.candidate, function(error) {
			if (error)
				return console.error('Error adding candidate: ' + error);
		});
		break;

	case 'stopCommunication':
		dispose();
		break;
	default:
		console.error('Unrecognized message', parsedMessage);
	}
}

function presenterResponse(message) {
	if (message.response != 'accepted') {
		var errorMsg = message.message ? message.message : 'Unknow error';
		console.info('Call not accepted for the following reason: ' + errorMsg);
		dispose();
	} else {
		webRtcPeer.processAnswer(message.sdpAnswer, function(error) {
			if (error)
				return console.error(error);
		});
	}
}

function viewerResponse(message) {
	if (message.response != 'accepted') {
		var errorMsg = message.message ? message.message : 'Unknow error';
		console.info('Call not accepted for the following reason: ' + errorMsg);
		dispose();
	} else {
		webRtcPeer.processAnswer(message.sdpAnswer, function(error) {
			if (error)
				return console.error(error);
		});
	}
}

function presenter(i) {
	if (navigator.geolocation) {
		setLocalVarFromIndex(i);
		navigator.geolocation.getCurrentPosition(function(position) {
			
			var distance = google.maps.geometry.spherical.computeDistanceBetween(
				new google.maps.LatLng(position.coords.latitude, position.coords.longitude), new google.maps.LatLng(orderLat, orderLng));
			
			if (distance == 0) {
				alert("ERROR! Too much distance between you and order.");			
			} else {
				if (!webRtcPeer) {
					var options = {
						localVideo : video,
						onicecandidate : onIceCandidate
					}
					webRtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options,
						function(error) {
							if (error) {
								return console.error(error);
							}
							webRtcPeer.generateOffer(onOfferPresenter);
						});
				}
			}
		}, function showError(error) {
			switch(error.code) {
				case error.PERMISSION_DENIED:
				alert("User denied the request for Geolocation.");
				break;
				case error.POSITION_UNAVAILABLE:
				alert("Location information is unavailable.");
				break;
				case error.TIMEOUT:
				alert("The request to get user location timed out.");
				break;
				case error.UNKNOWN_ERROR:
				alert("An unknown error occurred.");
				break;
			} 
		});
	} else { 
		alert("Geolocation is not supported by this browser.");
	}
}

function onOfferPresenter(error, offerSdp) {
	if (error)
		return console.error('Error generating the offer');

	console.info('Invoking SDP offer callback function ' + location.host);

	var message = {
		id : 'presenter',
		sdpOffer : offerSdp,
		roomId : roomId,
		userName : userAuthName
	}
	sendMessage(message);
	call(customerName);
}

function viewer() {
	if (!webRtcPeer) {
		document.getElementById('modalBody').style.display = 'inline';
		var options = {
			remoteVideo : video,
			onicecandidate : onIceCandidate
		}
		webRtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
				function(error) {
					if (error) {
						return console.error(error);
					}
					this.generateOffer(onOfferViewer);
				});
	}
}

function onOfferViewer(error, offerSdp) {
	if (error)
		return console.error('Error generating the offer');
	console.info('Invoking SDP offer callback function ' + location.host);
	var message = {
		id : 'viewer',
		sdpOffer : offerSdp,
		roomId : roomId,
		userName : userAuthName
	}
	sendMessage(message);
}

function onIceCandidate(candidate) {
	console.log("Local candidate" + JSON.stringify(candidate));

	var message = {
		id : 'onIceCandidate',
		candidate : candidate,
		roomId : roomId,
		userName : userAuthName
	};
	sendMessage(message);
}

function stop() {
	var message = {
		id : 'stop'
	}
	sendMessage(message);
	dispose();
}

function dispose() {
	if (webRtcPeer) {
		webRtcPeer.dispose();
		webRtcPeer = null;
	}
	hideSpinner(video);
}

function sendMessage(message) {
	var jsonMessage = JSON.stringify(message);
	console.log('Senging message: ' + jsonMessage);
	ws.send(jsonMessage);
}

function showSpinner() {
	for (var i = 0; i < arguments.length; i++) {
		arguments[i].poster = '/static/static/img/transparent-1px.png';
		arguments[i].style.background = 'center transparent url("/static/static/img/spinner.gif") no-repeat';
	}
}

function hideSpinner() {
	for (var i = 0; i < arguments.length; i++) {
		arguments[i].src = '';
		// arguments[i].poster = '/static/static/img/webrtc.png';
		arguments[i].style.background = '';
	}
}

function connect() {	
	var socket = new SockJS('/call');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/notify/' + userAuthName, function(
				notifyMessage) {
			setLocalVarFromResponse(notifyMessage);
			displayViewerForms();
		});
	});
}

function displayViewerForms() {
	$("#notificationModal").modal({
		backdrop : "static"
	});
	changeModalTitle(performerName + " is calling you");
	document.getElementById('ok').style.display = 'inline';
	document.getElementById('completed').style.display = 'inline';
}

function displayPresenterForms() {
	$("#notificationModal").modal({
			backdrop : "static"
	});
	document.getElementById('modalBody').style.display = 'inline';		
	changeModalTitle("Call " + customerName);
}

function changeModalTitle(title) {
	document.getElementById('modal-title').innerHTML = title;
}

function disconnect() {
	stompClient.disconnect();
	setConnected(false);
	console.log("Disconnected");
}

function call() {
	displayPresenterForms();
	var notifyMessage =  {
		customerName	: customerName,
		performerName	: userAuthName,
		roomId			: roomId
	}
	stompClient.send("/app/call", {}, JSON.stringify(notifyMessage));
}

function setLocalVarFromIndex(i) {
	if (typeof orders != "undefined") {
		i = orders[i].id;
	}
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'get',
		url : 'order/get/'+i,
		dataType : 'json',
		success : function(result) {
			orderLat = result.geolocation.latitude;
			orderLng = result.geolocation.longitude;
			customerName = result.user.username;
			roomId = result.id;
		},
		error : function(result, status, er) {
			console.log(er);
		}
	});
}

function setLocalVarFromResponse(notifyMessage) {
	performerName = JSON.parse(notifyMessage.body).performerName;
	customerName = JSON.parse(notifyMessage.body).customerName;
	roomId = JSON.parse(notifyMessage.body).roomId;
}

function completeOrder() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	$.ajax({
		url : '/order/'+roomId+'/complete',
		type : 'post',
	}).then (stop());
}
