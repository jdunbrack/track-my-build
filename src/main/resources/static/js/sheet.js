$(document).ready(function () {
    $('[data-toggle="popover"]').popover({
        html: true
    });
    
    $('[data-toggle="popover"]').mouseenter(function() {
        $(this).popover('show');
    });
    
    $('[data-toggle="popover"]').mouseleave(function() {
        $(this).popover('hide');
    });

    $('.pane-toggle').click(function(e) {
        e.preventDefault();
        $(this).next('.equip-div').slideToggle();
    })
})