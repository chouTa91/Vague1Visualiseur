
var suiviScript;
var suivi = suivi || {};
suivi.db2 = suivi.db2 || {};
suivi.db2.logs = suivi.db2.logs || {};


suivi.db2.logs.initModal = function() {
    const textArea = document.getElementById('codemirrorElemt');
    suiviScript = CodeMirror.fromTextArea(textArea, {
        readOnly: true,
        mode: "shell",
        styleActiveLine: true,
        theme: "3024-day",
        lineNumbers: true,
        autoRefresh: true,
    });
}

suivi.db2.logs.openModal = function(logTabName, operationType) {
    console.debug("[DB2][LOGS][MODAL] Ouverture de la modale pour : ", logTabName);
    let endpoint = `/db2/log/${operationType}/${logTabName}`;
    fetch(endpoint)
        .then((response) => {
            return response.text();
        }).then((body) => {
        document.querySelector("#db2-logs-modal").innerHTML = body;
        $("#db2-logs-modal").modal('toggle');
        suivi.db2.logs.initModal();
    }).catch((e) => {
        console.error("[DB2][LOGS][MODAL] Erreur de récupération des données. Détail : " + e);
    });
}

suivi.db2.logs.activeLi = function(me) {
    document.querySelectorAll(".menu__item").forEach(e=>{
        e.classList.remove('active');
    });
    me.classList.add('active');
    suiviScript.getDoc().setValue(me.dataset.content);
}

suivi.datatable.initLoadDb2Table = function() {

    suivi.datatable.loadDb2Table = $('#loadTable').DataTable({
        dom: "Bfrltip",
        "ajax": "/ajax/datable/db2LoadTable",
        "language": suivi.datatable.i18nMessages(),
        columns: [
            { data: "table", title: "Table"},
            { data: "nbPreTraitement", title: "Nombre pré-traitement" },
            { data: "nbPostTraitement", title: "Nombre post-traitement" },
            { data: "elapsedTime", title: "Temps de chargement" },
            {
                title: "Status",
                render: function (data, type, row) {
                    return row.status === 'OK' ? '<span class="badge bg-success rounded-pill d-inline">OK</span>' : `<span class="badge bg-danger rounded-pill d-inline">KO</span>`;
                }
            },
            {
                render: function (data, type, row) {
                    return `<div class="btn-wrap"><button title="Voir les logs de Conversion" style="border: none;color: #069;background: none;" onclick="suivi.db2.logs.openModal('${row.table}', 'load')" class="openModalButton"><i class="fa-regular fa-file-code"></i></button></div>`;
                }
            }
        ],
        rowCallback: function (row, data, index) {
            if (data['status'] === 'KO') {
                $(row).addClass('table-danger');
            }
        },
        buttons: [
            { extend: 'excelHtml5', className: 'btn btn-secondary btn-sm' },
            { extend: 'csvHtml5', className: 'btn btn-secondary btn-sm' },
            { extend: 'pdfHtml5', className: 'btn btn-secondary btn-sm' },
        ],
    });
}

suivi.datatable.initControlDb2Table = function() {

    suivi.datatable.controlDb2Table = $('#controlTable').DataTable({
        dom: "B",
        "ajax": "/ajax/datable/db2ControlTable",
        "language": suivi.datatable.i18nMessages(),
        columns: [
            { data: "count", title: "Nombre de fichiers"},
            {
                data: "missingFiles", title: "Fichiers manquants",
                render: function (data, type, row) {
                    let result = "";
                    $.each(row.missingFiles, function( index, value ) {
                        result += `<div>${value}</div>`;
                    });
                    return result;
                }
            }
        ],
        rowCallback: function (row, data, index) {
            if (!$.isEmptyObject(data['missingFiles'])) {
                $(row).addClass('table-danger');
            }
        },
        buttons: [
            { extend: 'excelHtml5', className: 'btn btn-secondary btn-sm' },
            { extend: 'csvHtml5', className: 'btn btn-secondary btn-sm' },
            { extend: 'pdfHtml5', className: 'btn btn-secondary btn-sm' },
        ],
    });

}

suivi.datatable.initConverDb2Table = function() {

    suivi.datatable.converDb2Table = $('#conversionTable').DataTable({
        dom: "Bfrltip",
        "ajax": "/ajax/datable/db2ConversionTable",
        "language": suivi.datatable.i18nMessages(),
        columns: [
            { data: "tableName", title: "Table"},
            { data: "nbRecProcessed", title: "Nombre d'entrée calculées" },
            { data: "nbRecsRead", title: "Nombre d'entrée lues" },
            { data: "nbRecsWritten", title: "Nombre d'entrée écrites" },
            { data: "maxInputRecLength", title: "Taille maximale d'entrée" },
            { data: "maxInputRecLengthLF", title: "Taille maximale d'entrée (LF)" },
            { data: "elapsedTime", title: "Temps de conversion" },
            {
                title: "Status",
                render: function (data, type, row) {
                    return row.status === 'OK' ? '<span class="badge bg-success rounded-pill d-inline">OK</span>' : `<span class="badge bg-danger rounded-pill d-inline">KO</span>`;
                }
            },
            {
                render: function (data, type, row) {
                    return `<div class="btn-wrap"><button title="Voir les logs de Conversion" style="border: none;color: #069;background: none;" onclick="suivi.db2.logs.openModal('${row.tableName}', 'conversion')" class="openModalButton "><i class="fa-regular fa-file-code"></i></button></div>`;
                }
            }
        ],
        rowCallback: function (row, data, index) {
            if (data['status'] === 'KO') {
                $(row).addClass('table-danger');
            }
        },
        buttons: [
            { extend: 'excelHtml5', className: 'btn btn-secondary btn-sm' },
            { extend: 'csvHtml5', className: 'btn btn-secondary btn-sm' },
            { extend: 'pdfHtml5', className: 'btn btn-secondary btn-sm' },
        ],
    });
}