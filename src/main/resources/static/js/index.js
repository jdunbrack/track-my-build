$("#sessid-input").hide();
$(document).ready(function() {

    $("#get-started").click(function(e) {
        e.preventDefault();
        $("#sessid-input").slideToggle();
        $("#poesessid-input").focus();
    })
});