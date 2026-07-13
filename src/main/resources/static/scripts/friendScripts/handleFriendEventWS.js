function handleFriendEventWS(event) {

    const profile = document.getElementById("user_info");
    if (!profile) return;

    if (event.type === "FRIEND_REQUEST") {
        renderFriendControls("REQUEST_RECEIVED", event.eventId);
    }

    if (event.type === "FRIEND_ACCEPTED") {
        renderFriendControls("FRIENDS", event.eventId);
    }

    if (event.type === "FRIEND_REJECTED") {
        renderFriendControls("REJECTED", event.eventId);
    }

    if (event.type === "DELETE_RELATION") {
        renderFriendControls("DELETE_RELATION");
    }
}