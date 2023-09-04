
$(".toggle").click(function(){
    const navigation  = document.querySelector(".t__navigation");
    const main  = document.querySelector(".main");
    navigation.classList.toggle("t__active");
    main.classList.toggle("t__active");
})

document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll(".redirectLink").forEach(element => {
        element.addEventListener("click",function(event){
            // Empêchez la redirection par défaut
            event.preventDefault();
            if(suivi.eventSource !== undefined){
                if(suivi.eventSource.length > 0){
                    suivi.eventSource.forEach(element => {
                        element.close();
                    });
                }
            }
            window.location.href = event.currentTarget.href;
        });
    });
});