$(document).ready(function(){
    if($('.splashscreen').length > 0 ){
        $(".splashscreen").delay(2300).fadeOut(250);
    }
});

function toggleMenu() {
    $(".hamburger").toggleClass("is-active");
    $(".sidebar").toggleClass("active");
    $(".menu-item").toggleClass("active");
    $(".content-overlay").toggleClass("active");
};