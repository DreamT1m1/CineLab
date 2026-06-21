document.querySelectorAll(".rating").forEach(select => {

    select.addEventListener("change", function () {

        const movieId = this.dataset.movieId;
        const userId = this.dataset.userId;
        const rating = this.value;

        fetch("/add_rating_in_profile", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body:
                "movieId=" + movieId +
                "&userId=" + userId +
                "&rating=" + rating
        });

    });

});