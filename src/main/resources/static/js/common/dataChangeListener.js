
suivi.updateDl1Infos = function () {
    $.ajax({
        url: 'ajax/datable/dl1ControlInfo',
        success: function(data) {
            const renderFiles = `${data.filesCount} / ${data.filesTotal}`;
            const renderLogs = `${data.logCount} / ${data.logTotal}`;
            $('#dl1FilesNombre').html(renderFiles);
            // pour faire un seul call ajax on va changé le contenu du fragment-dl1-controlelogs.html
            $('#dl1LogsNombre').html(renderLogs);
        }
    });
}


suivi.updateDb2Infos = function (){
    $.ajax({
        url: 'ajax/datable/db2ControlInfo',
        success: function(data) {
            $('.count').html(data.count);
            $('.total').html(data.total);
            $('.refreshDate').html(data.refreshDate);
            if(data.count === data.total)
                $('#blockInfo').removeClass('suivi-migration-error').addClass('suivi-migration-succes');
            else
                $('#blockInfo').removeClass('suivi-migration-succes').addClass('suivi-migration-error');
        }
    });

}

suivi.datatable.allDataChangeListener = function () {
    if (window.EventSource != null) {
        const eventSource = new EventSource('/flux/reactor/changements');
        eventSource.onmessage = function (event) {
            suivi.datatable.loadDb2Table.ajax.reload();
            suivi.datatable.controlDb2Table.ajax.reload();
            suivi.datatable.converDb2Table.ajax.reload();
            suivi.updateDb2Infos();
            suivi.datatable.controlLogsDl1Table.ajax.reload();
            suivi.datatable.volumetrieOracleDl1Table.ajax.reload();
            suivi.datatable.controlFilesDl1Table.ajax.reload();
            suivi.updateDl1Infos();
        };
    }
}