document.getElementById("accept-friend-button")?.addEventListener("click", function () {
    const inviteId = this.dataset.inviteId;

    fetch(`/friend_invites/${inviteId}/reject`, {
        method: "POST"
    })
        .then(() => {
            location.reload();
        })
})