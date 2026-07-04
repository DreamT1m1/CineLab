document.addEventListener("click", function (e) {

    if (e.target.id === "add-friend-button") {

        const username = document.getElementById("user_info").dataset.username;

        fetch(`/${username}/send_friend_invite`, {
            method: "POST"
        }).then(() =>
            renderFriendControls("REQUEST_SENT"));
    }

    if (e.target.id === "accept-friend-button") {

        const inviteId = e.target.dataset.id;

        fetch(`/friend_invites/${inviteId}/accept`, {
            method: "POST"
        }).then(() =>
            renderFriendControls("FRIENDS"));
    }

    if (e.target.id === "reject-friend-button") {

        const inviteId = e.target.dataset.id;

        fetch(`/friend_invites/${inviteId}/reject`, {
            method: "POST"
        }).then(() =>
            renderFriendControls("NONE"));
    }

});