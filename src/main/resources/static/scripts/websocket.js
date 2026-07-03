const socket = new SockJS("/ws");

const client = new StompJs.Client({
    webSocketFactory: () => socket,

    onConnect: () => {

        console.log("CONNECTED");

        client.subscribe("/user/queue/notifications", message => {

            console.log("MESSAGE RECEIVED");

            console.log(message.body);

        });

    }
});

client.activate();