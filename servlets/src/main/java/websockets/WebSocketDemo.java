package websockets;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class WebSocketDemo {

    @OnMessage
    public String echoTextMessage(String msg) {
        return msg + " from WebSocket!";
    }
}
