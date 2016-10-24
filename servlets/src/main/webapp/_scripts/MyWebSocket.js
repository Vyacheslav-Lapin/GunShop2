'use strict';

const WS_URI = "ws://localhost:8080/echo";

class MyWebSocket {

    constructor(outputElementId = "output", inputElementId = "message", senderElementId = "textID") {
        /** @type HTMLDivElement */
        this.output = document.getElementById(outputElementId);

        /** @type HTMLInputElement */
        this.input = document.getElementById(inputElementId);

        document.getElementById(senderElementId).addEventListener("click",
            () => this.doSend(this.input.value));

        this.websocket = new WebSocket(WS_URI);
        this.websocket.onopen = () => this.writeToScreen(`Connected to Endpoint!`);
        this.websocket.onmessage = evt => this.writeToScreen(`Message Received: ${evt.data}`);
        this.websocket.onerror = evt => this.writeToScreen(`ERROR: ${evt.data}`);

        addEventListener("beforeunload", this.websocket.close.bind(this.websocket));
    }

    writeToScreen(message) {
        const pre = document.createElement("p");
        pre.appendChild(document.createTextNode(message));
        this.output.appendChild(pre);
    }

    doSend(message) {
        this.writeToScreen("Message Sent: " + message);
        this.websocket.send(message);
    }
}
