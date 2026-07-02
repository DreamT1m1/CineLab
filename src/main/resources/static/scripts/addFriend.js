document.getElementById("add-friend-button").addEventListener("click", function(){
    const userName = this.dataset.username;

    fetch(`/${userName}/send_friend_invite`, {
        method: "POST"
    })
        .then(() => {
            this.innerText = "Request sent";
            this.disabled = true;
        })
})