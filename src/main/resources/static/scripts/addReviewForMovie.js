document.querySelectorAll(".dropdown-review-text").forEach(form => {

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const formData = new FormData(form);

        await fetch("/add_review", {
            method: "POST",
            body: formData
        });
    });

});