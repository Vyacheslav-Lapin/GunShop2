'use strict';

const WS_URI = "ws://localhost:8080/echo";

class MyWebSocket {

    constructor(outputElementId = "output", inputElementId = "message") {
        /** @type HTMLDivElement */
        this.output = document.getElementById(outputElementId);

        /** @type HTMLInputElement */
        this.input = document.getElementById(inputElementId);

        this.input.form.addEventListener("submit", evt => {
            this.doSend(this.input.value);
            this.input.value = "";
            evt.preventDefault();
        }, true);

        //noinspection JSUnusedGlobalSymbols,SpellCheckingInspection
        this.webSocket = Object.assign(new WebSocket(WS_URI), {
            onopen: () => this.writeToScreen(`Connected to Endpoint!`),
            onmessage: evt => this.writeToScreen(`Message Received: ${evt.data}`),
            onerror: evt => this.writeToScreen(`ERROR: ${evt.data}`)
        });

        //noinspection SpellCheckingInspection
        addEventListener("beforeunload",
            this.webSocket.close
                .bind(this.webSocket));
    }

    writeToScreen(message) {
        const paragraph = document.createElement("p");
        paragraph.appendChild(document.createTextNode(message));
        this.output.appendChild(paragraph);
    }

    doSend(message) {
        this.webSocket.send(message);
        this.writeToScreen("Message Sent: " + message);
    }
}
