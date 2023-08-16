
let suiviScript;
var suivi = suivi || {};
suivi.db2 = suivi.db2 || {};
suivi.db2.logs = suivi.db2.logs || {};

suivi.db2.logs.initModal = function() {
    const elm = document.querySelector('#tir1-db2-conversion-logs-modal .active');
    const textArea = document.getElementById('codemirrorElemt');
    textArea.textContent = elm.dataset.content;
    suiviScript = CodeMirror.fromTextArea(textArea, {
        readOnly: true,
        mode: "shell",
        styleActiveLine: true,
        theme: "3024-day",
        lineNumbers: true,
        autoRefresh: true,
    });
}

suivi.db2.logs.openModal = function(logTabName) {
    console.debug("[DB2][LOGS][MODAL] Ouverture de la modale pour : ", logTabName);

    let endpoint = `/db2/logs/` + logTabName;
    fetch(endpoint)
        .then((response) => {
            return response.text();
        }).then((body) => {
        document.querySelector("#tir1-db2-conversion-logs-modal").innerHTML = body;
        $("#tir1-db2-conversion-logs-modal").modal('toggle');
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