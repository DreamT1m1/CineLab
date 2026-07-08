const socket = new SockJS("/ws");

const client = new StompJs.Client({
    webSocketFactory: () => socket,

    onConnect: () => {

        client.subscribe("/user/queue/notifications", message => {

            const event = JSON.parse(message.body);

            showNotification(event);
            handleFriendEventWS(event);

        });

        client.subscribe("/user/queue/messages", function (message) {

            const data = JSON.parse(message.body);

            showMessage(data);
        })

        loadMessages();
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

function sendMessage() {

    const messageInput = document.getElementById("messageInput");
    let text = messageInput.value;

    let message = {
        receiverId: receiverId,
        text: text
    };

    client.publish({
        destination: "/app/chat.send",
        body: JSON.stringify(message)
    });

    showMessage({
        text: messageInput.value,
        senderId: messageInput.dataset.currentUserId
    });

    messageInput.value = "";
}

function showMessage(message) {
    let div = document.createElement("div");

    div.textContent = message.text;

    document.getElementById("messages").appendChild(div);
}

function loadMessages() {

    if (!receiverId) {
        return;
    }

    fetch(`/chat/${receiverId}/messages`)
        .then(response => response.json())
        .then(messages => {
            messages.forEach(message => {
                showMessage(message);
            });
        })
        .catch(error => {
            console.error("Failed to load messages: ", error);
        });
}