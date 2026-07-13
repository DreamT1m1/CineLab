function renderFriendControls(state, eventId) {

    const el = document.getElementById("friend-controls");
    if (!el) return;

    const username = document.getElementById("user_info")?.dataset.username;

    if (state === "NONE" || state === "REJECTED" || state === "DELETE_RELATION") {
        el.innerHTML = `
            <button id="add-friend-button" data-username="${username}">
                Send friend request
            </button>
        `;
    }

    if (state === "REQUEST_SENT") {
        el.innerHTML = `
            <button disabled>
                Request sent
            </button>
        `;
    }

    if (state === "REQUEST_RECEIVED") {
        el.innerHTML = `
            <button id="accept-friend-button" data-id="${eventId}">
                Accept
            </button>
            <button id="reject-friend-button" data-id="${eventId}">
                Reject
            </button>
        `;
    }

    if (state === "FRIENDS") {
        const profileId = el.dataset.profileId;
        const currentUserId = el.dataset.currentUserId;

        el.innerHTML = `
            <button disabled>
                Friends
            </button>
            <button id="delete-friend-button" data-profile-id="${profileId}" data-current-user-id="${currentUserId}">
                Delete friend
            </button>
        `;
    }
}