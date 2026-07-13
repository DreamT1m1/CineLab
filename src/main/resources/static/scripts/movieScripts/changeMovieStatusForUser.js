document.getElementById("status").addEventListener("change", function () {

    const movieId = this.dataset.movieId;
    const movieTitle = this.dataset.movieTitle;
    const year = this.dataset.year;
    const status = this.value;

    fetch("/change_movie_status", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body:
            "movieId=" + movieId +
            "&movieTitle=" + movieTitle +
            "&status=" + status +
            "&year=" + year
    });

});