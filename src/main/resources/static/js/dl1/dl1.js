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

suivi.datatable.initControlFilesDl1Table = function() {

	suivi.datatable.controlFilesDl1Table = $('#dl1FilesID').DataTable({
		dom: "Bfrltip",
		"ajax": "/ajax/datable/dl1Files",
		"language": suivi.datatable.i18nMessages(),
		columns: [
			{ data: "code", title: "Direction"},
			{ data: "count", title: "Nombre de fichiers"},
			{ data: "missingFiles", title: "Fichiers manquants"},
			{ data: "alertsMessages", title: "Précisions"},
		],
		buttons: [
			{ extend: 'excelHtml5', className: 'btn btn-secondary btn-sm' },
			{ extend: 'csvHtml5', className: 'btn btn-secondary btn-sm' },
			{ extend: 'pdfHtml5', className: 'btn btn-secondary btn-sm' },
		],
	});
}


suivi.datatable.initControlLogsDl1Table = function() {

	suivi.datatable.controlLogsDl1Table = $('#tir1-dl1-controle-fichier-logs-table').DataTable({
		dom: "Bfrltip",
		"ajax": "/ajax/datable/controlelogs",
		"language": suivi.datatable.i18nMessages(),
		columns: [
			{ data: "code", title: "Direction"},
			{ data: "count", title: "Nombre de fichiers"},
			{ data: "missingFiles", title: "Fichiers manquants"},
			{ data: "alertsMessages", title: "Précisions"},
		],
		buttons: [
			{ extend: 'excelHtml5', className: 'btn btn-secondary btn-sm' },
			{ extend: 'csvHtml5', className: 'btn btn-secondary btn-sm' },
			{ extend: 'pdfHtml5', className: 'btn btn-secondary btn-sm' },
		],
	});
}

suivi.datatable.initVolumetrieOracleDl1Table = function() {

	suivi.datatable.volumetrieOracleDl1Table = $('#tir1-dl1-volumetrie-oracle')
		.on( 'page.dt', function (){
			setTimeout(()=>{
				[...document.querySelectorAll('[data-bs-toggle="tooltip"]')].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
			},1000)
		})
		.DataTable({
			"initComplete": function(settings, json) {
				[...document.querySelectorAll('[data-bs-toggle="tooltip"]')].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
			},
			dom: "Bfrltip",
			"ajax": "/ajax/datable/dl1FilesVolumetrieOracle",
			"language": suivi.datatable.i18nMessages(),
			columns: [
				{ data: "tableName", title: "Table"},
				{ data: "volumeLog", title: "Nombre d'entrées attendues"},
				{ data: "volumeOracle", title: "Nombre d'entrées Oracle"},
				{
					title: "Écart",
					render: function (data, type, row) {
						return row.volumeLog - row.volumeOracle
					}
				},
				{
					data: "totalelapsedTime", title: 'Temps de chargement total'
				},
				{
					data: "Statut",
					render: function (data, type, row) {
						return row.volumeLog == row.volumeOracle ? '<span class="badge bg-success rounded-pill d-inline">OK</span>' : `<span class="badge bg-danger rounded-pill d-inline"   data-bs-toggle="tooltip" data-bs-placement="top" title="${row.alertsMessages}">KO</span>`;
					}
				},
				{
					render: function (data, type, row) {
						return `
							<div class="btn-wrap">
								<button title="Voir les logs de volume"
									onclick="suivi.dl1.volumetrie.openModal('${row.tableName}')"
									data-bs-target="#tir1-dl1-volumetrie-oracle--logs-modals"
									class="btn">
									<i class="fa-regular fa-file-lines"></i>
								</button>
								<button title="Voir les logs de chargement" class="btn"
									onclick="suivi.dl1.volumetrie.openModalOracle('${row.tableName}')"
									data-bs-target="#tir1-dl1-volumetrie-oracle--logs-modals">
									<i class="fa-regular fa-file-code"></i>
								</button>
							</div>
							`;
					}
				},
			],
			rowCallback: function (rowDom, data, index) {
				if (data.volumeLog === data.volumeOracle) return;
				$(rowDom).addClass('table-danger');
			},
			buttons: [
				{ extend: 'excelHtml5', className: 'btn btn-secondary btn-sm' },
				{ extend: 'csvHtml5', className: 'btn btn-secondary btn-sm' },
				{ extend: 'pdfHtml5', className: 'btn btn-secondary btn-sm' },
			],
		});
}