function handleFriendEvent(event) {

    const profile = document.getElementById("user_info");
    if (!profile) return;

    if (event.type === "FRIEND_REQUEST") {
        renderFriendControls("REQUEST_RECEIVED", event.inviteId);
    }

    if (event.type === "FRIEND_ACCEPTED") {
        renderFriendControls("FRIENDS", event.inviteId);
    }

    if (event.type === "FRIEND_REJECTED") {
        renderFriendControls("REJECTED", event.inviteId);
    }
}