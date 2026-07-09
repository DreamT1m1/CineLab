document.querySelectorAll(".sub-header-menu-element").forEach(function (item) {
    item.addEventListener("mouseover", function () {
        if (item.id === "sub-header-menu-movies") {
            const moviesHeaderSubMenu = document.getElementById("sub-header-menu-sub-movies");
            moviesHeaderSubMenu.classList.remove("hidden");
        }
    });

    item.addEventListener("mouseleave", function () {
        if (item.id === "sub-header-menu-movies") {
            const moviesHeaderSubMenu = document.getElementById("sub-header-menu-sub-movies");
            moviesHeaderSubMenu.classList.add("hidden");
        }
    });
});