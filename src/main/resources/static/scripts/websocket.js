const socket = new SockJS("/ws");

const client = new StompJs.Client({
    webSocketFactory: () => socket,

    onConnect: () => {

        client.subscribe("/user/queue/notifications", message => {

            const event = JSON.parse(message.body);

            showNotification(event);
            handleFriendEventWS(event);

        });

    }
});

client.activate();

//---------------------------------------------------------------

function showNotification(event) {

    const container = document.getElementById("notifications");

    if (!container) return;

    const div = document.createElement("div");
    div.className = "notification";

    if (event.type === "FRIEND_REQUEST") {
        div.innerText = `${event.userName} sent you a friend request`;
    } else if (event.type === "FRIEND_ACCEPTED") {
        div.innerText = `You're now friends with ${event.userName}`;
    } else if (event.type === "FRIEND_REJECTED") {
        return;
    } else if (event.type === "DELETE_RELATION") {
        div.innerText = `You're no longer friends with ${event.userName}`;
    }

    container.appendChild(div);

    setTimeout(() => {
        div.remove();
    }, 7000);

}