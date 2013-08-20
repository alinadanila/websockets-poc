// connect to the string info socket  
function WebSocketString() {
	if ("WebSocket" in window) {
		var info;

		// open the web socket to the string info
		var stringSocket = new WebSocket(
				"ws://localhost:8080/websockets-poc/string");
		stringSocket.onopen = function() {
			// send an initial message
			updateStatus('connected');
			stringSocket.send("hello info system!");
		};
		stringSocket.onmessage = function(event) {
			var received_msg = event.data;
			info = JSON.parse(received_msg);
			updateInfo(info);
		};
		stringSocket.onclose = function() {
			// socket disconnected
			updateStatus('disconnected');
		};
	} else {
		alert("WebSocket NOT supported by your Browser!");
	}
}

// connect to the binary socket
function WebSocketBinary() {
	if ("WebSocket" in window) {
		var info;
		imageheight = 200;
		imagewidth = 200;

		// open the web socket to the binary info
		var binarySocket = new WebSocket(
				"ws://localhost:8080/websockets-poc/binary");
		binarySocket.binaryType = 'arraybuffer';
		binarySocket.onopen = function() {
			// send an initial message
			updateStatus('connected');
			binarySocket.send("hello info system!");
		};
		binarySocket.onmessage = function(messageEvent) {

			if (event.data instanceof ArrayBuffer) {
				var bytearray = new Uint8Array(event.data);

			    var tempcanvas = document.createElement('canvas');
			    tempcanvas.height = imageheight;
			    tempcanvas.width = imagewidth;
			    var tempcontext = tempcanvas.getContext('2d');

			    var imgdata = tempcontext.getImageData(0, 0, imagewidth,imageheight);
			    var imgdatalen = imgdata.data.length;

			    for ( var i = 8; i < imgdatalen; i++) {
			        imgdata.data[i] = bytearray[i];
			    }

			    tempcontext.putImageData(imgdata, 0, 0);

			    var img = document.createElement('img');
			    img.height = imageheight;
			    img.width = imagewidth;
			    img.src = tempcanvas.toDataURL('image/jpeg');
			    chatdiv = document.getElementById('chatdiv');
			    chatdiv.appendChild(img);
			    chatdiv.innerHTML = chatdiv.innerHTML + "<br />";
			}
		};
		binarySocket.onclose = function() {
			// socket disconnected
			updateStatus('disconnected');
		};
	} else {
		alert("WebSocket NOT supported by your Browser!");
	}
}

function updateStatus(status) {
	document.getElementById('status').innerHTML = status;
}

function updateInfo(info) {
	document.getElementById('usedCpu').innerHTML = info.usedCpu + '%';
	document.getElementById('idleCpu').innerHTML = info.idleCpu + '%';
	document.getElementById('freeMem').innerHTML = info.freeMem + 'Mb';
	document.getElementById('usedMem').innerHTML = info.usedMem + 'Mb';
	document.getElementById('totalMem').innerHTML = info.totalMem + 'Mb';
}







/*
 * binarySocket.onmessage = function(event) { if (event.data instanceof
 * ArrayBuffer) { var bytearray = new Uint8Array(event.data); var tempcanvas =
 * document.createElement('canvas'); tempcanvas.height = imageheight;
 * tempcanvas.width = imagewidth; var tempcontext = tempcanvas.getContext('2d');
 * var imgdata = tempcontext.getImageData(0, 0, imageheight, imagewidth); var
 * imgdatalen = imgdata.data.length; for ( var i = 8; i < imgdatalen; i++) {
 * imgdata.data[i] = bytearray[i]; } tempcontext.putImageData(imgdata, 0, 0);
 * var img = document.getElementById('chart'); img.height = imageheight;
 * img.width = imagewidth; img.src = tempcanvas.toDataURL(); //
 * chatdiv.appendChild(img); chatdiv.innerHTML = // chatdiv.innerHTML + "<br />"; } };
 */
/*
 * if (messageEvent.data instanceof ArrayBuffer) { var destinationCanvas =
 * document.getElementById('destination'); var destinationContext =
 * destinationCanvas.getContext('2d'); var image = new Image(); image.onload =
 * function() { destinationContext.clearRect(0, 0, destinationCanvas.width,
 * destinationCanvas.height); destinationContext.drawImage(image, 0, 0); }
 * image.src = URL.createObjectURL(messageEvent.data); }
 */