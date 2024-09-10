function toggleDropdown() {
    var dropdown = document.getElementById("profileDropdown");
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";

    // var icon = document.getElementById("profileicon")
    // icon.style.boxShadow = "1px 1px 10px rgba(0, 0, 0, 0.6)"
    
    
    var icon = document.getElementById("profileicon")
    icon.addEventListener("mouseenter", function( event ) {   
        event.target.style.boxShadow = "1px 1px 10px rgba(0, 0, 0, 0.6)";
    }, false);
    icon.addEventListener("mouseleave", function( event ) {   
        event.target.style.boxShadow = "";
    }, false);
}

window.onclick = function(event) {
    if (!event.target.matches('.profile-icon img')) {
        var dropdown = document.getElementById("profileDropdown");
        var icon = document.getElementById("profileicon")
        if (dropdown && dropdown.style.display === "block") {
            dropdown.style.display = "none";
            icon.style.boxShadow = "none"
        }
    }
}

document.addEventListener("DOMContentLoaded", function() {
    var icon = document.getElementById("profileicon");

    if (icon) {
        icon.addEventListener("mouseenter", function(event) {
            event.target.style.boxShadow = "1px 1px 10px rgba(0, 0, 0, 0.6)";
        }, false);

        icon.addEventListener("mouseleave", function(event) {
            event.target.style.boxShadow = "";
        }, false);
    }
});