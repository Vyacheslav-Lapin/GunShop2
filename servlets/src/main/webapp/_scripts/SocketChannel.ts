//noinspection JSUnusedGlobalSymbols
class SocketChannel {

    private static readonly WS_URI = `ws://localhost:8080/echo`;

    private input: HTMLInputElement;
    private output: HTMLDivElement;
    private webSocket: WebSocket;

    //noinspection JSUnusedGlobalSymbols
    constructor(outputElementId = `output`, inputElementId = `message`) {

        this.output = document.getElementById(outputElementId) as HTMLDivElement;
        this.input = document.getElementById(inputElementId) as HTMLInputElement;

        this.input.form.addEventListener(`submit`, (evt: Event) => {
            this.doSend(this.input.value);
            this.input.value = "";
            evt.preventDefault();
        }, true);

        // noinspection JSUnusedGlobalSymbols,SpellCheckingInspection
        this.webSocket = Object.assign(new WebSocket(SocketChannel.WS_URI), {
            onerror: (evt: MessageEvent) => this.writeToScreen(`ERROR: ${evt.data}`),
            onmessage: (evt: MessageEvent) => this.writeToScreen(`Message Received: ${evt.data}`),
            onopen: () => this.writeToScreen(`Connected to Endpoint!`),
        });

        // noinspection SpellCheckingInspection
        addEventListener(`beforeunload`,
            this.webSocket.close
                .bind(this.webSocket));
    }

    public writeToScreen(message: string) {
        const paragraph = document.createElement(`p`);
        paragraph.appendChild(document.createTextNode(message));
        this.output.appendChild(paragraph);
    }

    public doSend(message: string) {
        this.webSocket.send(message);
        this.writeToScreen(`Message Sent: ${message}`);
    }
}
