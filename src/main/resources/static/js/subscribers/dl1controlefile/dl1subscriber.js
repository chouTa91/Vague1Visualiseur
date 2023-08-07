var suivi = suivi || {};
suivi.dl1 = suivi.dl1 || {};
suivi.dl1.subscriber = suivi.dl1.subscriber || {};

/**
 * Raffraichissement du fragment du contrôle de fichier de DL1.
 */
suivi.dl1.subscriber.refreshControleFichier = function() {
	
	console.debug("[DL1][CONTROLE][FICHIERS] Rafraichissement du fragment.");
	
	let endpoint = `/dl1/controlefichiers`;
	fetch(endpoint)
		.then((response) => {
			return response.text();
		}).then((body) => {
			document.querySelector("#fragment-dl1-controlefichier").outerHTML = body;
			window["#tir1-dl1-controle-fichier-table"].destroy();
			suivi.datatable.initDataTable("#tir1-dl1-controle-fichier-table");
		}).catch((e) => {
			console.error("[DL1][CONTROLE][FICHIERS] Erreur de récupération des données. Détail : " + e);
		});
}


/**
 * Raffraichissement du fragment du contrôle de fichier de logs de DL1.
 */
suivi.dl1.subscriber.refreshControleLogs = function() {
	
	console.debug("[DL1][CONTROLE][LOGS] Rafraichissement du fragment.");
	
	let endpoint = `/dl1/controlelogs`;
	fetch(endpoint)
		.then((response) => {
			return response.text();
		}).then((body) => {
			document.querySelector("#fragment-dl1-controlelogs").outerHTML = body;
			window["#tir1-dl1-controle-fichier-logs-table"].destroy();
			suivi.datatable.initDataTable("#tir1-dl1-controle-fichier-logs-table");
		}).catch((e) => {
			console.error("[DL1][CONTROLE][FICHIERS] Erreur de récupération des données. Détail : " + e);
		});
}

/**
 * Initialisation et souscription au SSE concernant le contrôle de fichier de DL1.
 */
suivi.dl1.subscriber.initSubscribe = function() {

	if (isSSEEnabled) {
		
		// Event du contrôle des fichiers de DL1.
		var eventSourceControleFichier = new EventSource('/dl1/files/reactor/controlefichiers'); eventSourceControleFichier.onopen = function() {
			console.debug("[DL1][CONTROLE][FICHIERS]SUBSCRIBER] Ecoute des modifications des fichiers de DL1.");
		}; eventSourceControleFichier.onerror = function(error) {
			console.error("[DL1][CONTROLE][FICHIERS][SUBSCRIBER] Erreur de connexion au service, état : " + eventSourceControleFichier.readyState + "\n Détail : " + error);
		}; eventSourceControleFichier.onmessage = function(event) {
			console.debug("[DL1][CONTROLE][FICHIERS] Notification d'un changement des données" + "\n Détail : " + event);
			suivi.dl1.subscriber.refreshControleFichier();
		};
		
		// Event du contrôle des fichiers de logs de DL1.
		var eventSourceControleLogs = new EventSource('/dl1/logs/reactor/controlelogs'); eventSourceControleLogs.onopen = function() {
			console.debug("[DL1][CONTROLE][LOGS][SUBSCRIBER] Ecoute des modifications des fichiers de logs de DL1.");
		}; eventSourceControleLogs.onerror = function(error) {
			console.error("[DL1][CONTROLE][LOGS][SUBSCRIBER] Erreur de connexion au service, état : " + eventSourceControleFichier.readyState + "\n Détail : " + error);
		}; eventSourceControleLogs.onmessage = function(event) {
			console.debug("[DL1][CONTROLE][LOGS] Notification d'un changement des données" + "\n Détail : " + event);
			suivi.dl1.subscriber.refreshControleLogs();
		};
	}

}