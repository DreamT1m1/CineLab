const socket = new SockJS("/ws");

const client = new StompJs.Client({
    webSocketFactory: () => socket,

    onConnect: () => {

        client.subscribe("/topic/messages", message => {
            console.log(message.body);
        });

        client.publish({
            destination: "/app/hello",

            body: "Hello spring"
        })

        client.subscribe("/user/queue/notifications", message => {

        });

    }
});

client.activate();