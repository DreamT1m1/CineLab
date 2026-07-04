document.querySelectorAll(".dropdown-review-button").forEach(button => {
    button.addEventListener("click", () => {

        const form = button
            .closest(".watched-movie")
            .querySelector(".dropdown-review-text-form");

        form.classList.toggle("open");
    });
});