var suivi = suivi || {};
suivi.datatable = suivi.datatable || {};
suivi.storeId = suivi.storeId || [];

suivi.datatable.i18nMessages = function() {
	return {
		"lengthMenu": "Afficher _MENU_ lignes par page",
		"search": "Recherche:",
		"zeroRecords": "Aucune correspondance",
		"emptyTable": "Aucune donnée dans ce tableau",
		"info": "Visualisation de la page _PAGE_ sur _PAGES_",
		"paginate": {
			"next": "Suivant",
			"previous": "Précédent"
		}
	}
}

/**
 * Destory un tableau DataTable.
 * 
 * @param	tableId l'identifiant html du tableau à magnifier.
 */

suivi.datatable.destroyDataTable = function (tableId) {
	$(tableId).destroy();
}


/**
 * Initialisation d'un tableau DataTable.
 * 
 * @param	tableId l'identifiant html du tableau à magnifier.
 */
suivi.datatable.initDataTable = function (tableId) {
	if (suivi.storeId.indexOf(tableId) === -1) {
		suivi.storeId.push(tableId);
	}
	console.debug("[DATATABLE] Initialisation du tableau " + tableId);

	$(tableId).DataTable({
		lengthMenu: [10, 25, 50, 100], //https://datatables.net/reference/option/lengthMenu
		"dom": 'Blfrtip',
		"ordering": true,
		retrieve: true,
		scrollX: true,// https://datatables.net/examples/basic_init/scroll_x.html
		"language": {//https://datatables.net/reference/option/language
			"lengthMenu": "Afficher _MENU_ lignes par page",
			"search": "Recherche:",
			"zeroRecords": "Aucune correspondance",
			"emptyTable": "Aucune donnée dans ce tableau",
			"info": "Visualisation de la page _PAGE_ sur _PAGES_",
			"paginate": {
				"next": "Suivant",
				"previous": "Précédent"
			}
		}, buttons: [
			{ extend: 'excelHtml5', className: 'btn btn-secondary btn-sm' },
			{ extend: 'csvHtml5', className: 'btn btn-secondary btn-sm' },
			{ extend: 'pdfHtml5', className: 'btn btn-secondary btn-sm' },
		],

	});
}

suivi.datatable.initDataTableSingleRow = function (tableId) {

	console.debug("[DATATABLE] Initialisation du tableau " + tableId);

	window[tableId] = $(tableId).DataTable({
		"dom": 'B',
		scrollX: true,// https://datatables.net/examples/basic_init/scroll_x.html
		"language": {//https://datatables.net/reference/option/language
			"lengthMenu": "Afficher _MENU_ lignes par page",
			"search": "Recherche:",
			"zeroRecords": "Aucune correspondance",
			"emptyTable": "Aucune donnée dans ce tableau",
			"info": "Visualisation de la page _PAGE_ sur _PAGES_",
			"paginate": {
				"next": "Suivant",
				"previous": "Précédant"
			}
		}, buttons: [
			{ extend: 'excelHtml5', className: 'btn btn-secondary btn-sm' },
			{ extend: 'csvHtml5', className: 'btn btn-secondary btn-sm' },
			{ extend: 'pdfHtml5', className: 'btn btn-secondary btn-sm' },
		],
	});
}