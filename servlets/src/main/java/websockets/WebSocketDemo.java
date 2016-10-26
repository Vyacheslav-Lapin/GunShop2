package websockets;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/echo")
public class WebSocketDemo {

//    @OnMessage
//    public String echoTextMessage(String msg) {
//        return msg + " from WebSocket!";
//    }

    private static Set<Session> userSessions = Collections.newSetFromMap(
            new ConcurrentHashMap<Session, Boolean>());

    @OnOpen
    public void onOpen(Session userSession) {
        userSessions.add(userSession);
        userSession.getAsyncRemote().sendText("Hi!");
    }

    @OnClose
    public void onClose(Session userSession) {
        userSessions.remove(userSession);
    }

    @OnMessage
    public void onMessage(String message) {
        broadcast(message + " from WebSocket!");
    }

    public static void broadcast(String msg) {
        userSessions.stream()
                .map(Session::getAsyncRemote)
                .forEach(async -> async.sendText(msg));
    }
}
