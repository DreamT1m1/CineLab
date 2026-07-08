let receiverId = null;

const messageInput = document.getElementById("messageInput");
const sendButton = document.getElementById("send-button");

if (messageInput) {
    receiverId = messageInput.dataset.receiverId;

    if (!receiverId) {
        messageInput.disabled = true;

        if (sendButton) {
            sendButton.disabled = true;
        }
    }
}

document.querySelectorAll(".chat-item").forEach(item => {
    item.addEventListener("click", () => {

        const userId = item.dataset.userId;

        openChat(userId);

    });
})

function openChat(userId) {

    receiverId = userId;

    history.pushState(
        {},
        "",
        `/chat/${userId}`
    );

    document.getElementById("messages").innerHTML = "";

    document.getElementById("messageInput").disabled = false;
    document.getElementById("send-button").disabled = false;

    loadMessages();
}