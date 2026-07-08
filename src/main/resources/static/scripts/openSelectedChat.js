let receiverId = null;

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

    loadMessages();
}