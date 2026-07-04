function renderFriendControls(state, inviteId) {

    const el = document.getElementById("friend-controls");
    if (!el) return;

    const username = document.getElementById("user_info")?.dataset.username;

    if (state === "NONE") {
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
            <button id="accept-friend-button" data-id="${inviteId}">
                Accept
            </button>
            <button id="reject-friend-button" data-id="${inviteId}">
                Reject
            </button>
        `;
    }

    if (state === "FRIENDS") {
        el.innerHTML = `
            <button disabled>
                Friends
            </button>
        `;
    }
}