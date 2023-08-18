
isSSEEnabled = function () {
	if (window.EventSource == null) {
		alert('Ce navigateur ne prends pas en charge le SSE et ne peux donc pas consulter cette page correctement.');
		return false;
	} else {
		return true;
	}
}