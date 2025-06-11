const titles = document.getElementsByClassName("movie-title");

for (let title of Array.from(titles)) {
    if (title.scrollWidth <= 168) {
        title.classList.add("movie-title-fits");
    } else {
        const movieBlock = title.parentElement;
        movieBlock.addEventListener("mouseover", () => {
            title.classList.add("movie-title-does-not-fit");
        });
        movieBlock.addEventListener("mouseout", () => {
            title.classList.remove("movie-title-does-not-fit");
        });
    }
}
