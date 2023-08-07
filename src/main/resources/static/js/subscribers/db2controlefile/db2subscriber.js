var suivi = suivi || {};
suivi.db2 = suivi.db2 || {};
suivi.db2.subscriber = suivi.db2.subscriber || {};

/**
 * Raffraichissement du fragment du contrôle de fichier de DB2.
 */
suivi.db2.subscriber.refresh = function() {
	
	console.debug("[DB2] [CONTROLE] Rafraichissement du fragment.");
	
	let endpoint = `/db2/controle`;
	fetch(endpoint)
		.then((response) => {
			return response.text();
		}).then((body) => {
			document.querySelector("#fragment-db2-controlefichier").outerHTML = body;
			window["#tir1-dl1-controle-fichier-table"].destroy();
			suivi.datatable.initDataTable("#tir1-db2-controle-fichier-table");
		}).catch((e) => {
			console.error("[DB2] [CONTROLE] Erreur de récupération des données. Détail : " + e);
		});
}

/**
 * Initialisation et souscription au SSE concernant le contrôle de fichier de DB2.
 */
suivi.db2.subscriber.initSubscribe = function() {

	if (isSSEEnabled) {
		
		var eventSource = new EventSource('/db2/reactor/controle'); eventSource.onopen = function() {
			console.debug("[DB2] [CONTROLE][SUBSCRIBER] Ecoute des modifications des fichiers de DB2.");
		}; eventSource.onerror = function(error) {
			console.error("[DB2] [CONTROLE][SUBSCRIBER] Erreur de connexion au service, état : " + eventSource.readyState + "\n Détail : " + event);
		}; eventSource.onmessage = function(event) {
			console.debug("[DB2] [CONTROLE] Notification d'un changement des données");
			suivi.db2.subscriber.refresh();
		};
	}

}