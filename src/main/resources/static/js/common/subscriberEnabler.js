
isSSEEnabled = function () {
	if (window.EventSource == null) {
		alert('Ce navigateur ne prends pas en charge le SSE et ne peux donc pas consulter cette page correctement.');
		return false;
	} else {
		return true;
	}
}
if (isSSEEnabled) {
	// const eventSource = new EventSource('/flux/reactor/changements');
	// var max = 1, cmp = 0;
	// eventSource.onmessage = function (event) {
	// 	console.log(event.data)
	// };
}
