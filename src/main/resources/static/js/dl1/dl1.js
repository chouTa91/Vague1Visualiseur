var suivi = suivi || {};
suivi.dl1 = suivi.dl1 || {};
suivi.dl1.volumetrie = suivi.dl1.volumetrie || {};

suivi.dl1.volumetrie.openModal = function(logFileName) {

	console.debug("[DL1][VOLUMETRIE][MODAL] Ouverture de la modale pour : ", logFileName);

	let endpoint = `/dl1/volumetrie/logstable/` + logFileName;
	fetch(endpoint)
		.then((response) => {
			return response.text();
		}).then((body) => {
			document.querySelector("#tir1-dl1-volumetrie-oracle-logs-modal").outerHTML = body;
			document.querySelectorAll("#tir1-dl1-volumetrie-oracle-logs-modal script").forEach(element => eval(element.innerText))
			$("#tir1-dl1-volumetrie-oracle-logs-modal").modal('toggle');
		}).catch((e) => {
			console.error("[DL1][VOLUMETRIE][MODAL] Erreur de récupération des données. Détail : " + e);
		});
}


suivi.dl1.volumetrie.openModalAll = function(logFileName) {

	console.debug("[DL1][VOLUMETRIE][MODAL] Ouverture de la modale pour : ", logFileName);

	let endpoint = `/dl1/volumetrie/logs/` + logFileName;
	fetch(endpoint)
		.then((response) => {
			return response.text();
		}).then((body) => {
			document.querySelector("#tir1-dl1-volumetrie-oracle-logs-modal").outerHTML = body;
			document.querySelectorAll("#tir1-dl1-volumetrie-oracle-logs-modal script").forEach(element => eval(element.innerText))
			$("#tir1-dl1-volumetrie-oracle-logs-modal").modal('toggle');
		}).catch((e) => {
			console.error("[DL1][VOLUMETRIE][MODAL] Erreur de récupération des données. Détail : " + e);
		});
}


suivi.dl1.volumetrie.openModalOracle = function(logFileName) {

	console.debug("[DL1][VOLUMETRIE ORACLE][MODAL] Ouverture de la modale pour : ", logFileName);

	let endpoint = `/dl1/volumetrie/oracle/logs/` + logFileName;
	fetch(endpoint)
		.then((response) => {
			return response.text();
		}).then((body) => {
			document.querySelector("#tir1-dl1-volumetrie-oracle-logs-modal").outerHTML = body;
			document.querySelectorAll("#tir1-dl1-volumetrie-oracle-logs-modal script").forEach(element => eval(element.innerText))
			$("#tir1-dl1-volumetrie-oracle-logs-modal").modal('toggle');
		}).catch((e) => {
			console.error("[DL1][VOLUMETRIE][MODAL] Erreur de récupération des données. Détail : " + e);
		});
}