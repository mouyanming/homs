package jp.co.hyron.ope;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/reverse")
public class ReverseWebSocketEndpoint {
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    static {
        // ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // service.scheduleWithFixedDelay(ReverseWebSocketEndpoint::broadcast, 5, 5, TimeUnit.SECONDS);
    }

    @OnOpen
    public void connect(Session session) {
        System.out.println(session.getUserPrincipal().getName());
        sessions.add(session);
    }

    @OnClose
    public void remove(Session session) {
        sessions.remove(session);
    }

    public static void broadcast() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sessions.forEach(session -> {
            session.getAsyncRemote().sendText("Broadcast : " + formatter.format(now));
        });
    }

    @OnMessage
    public void handleMessage(Session session, String message) throws IOException {
        // session.getOpenSessions().forEach(s -> {
        // s.getAsyncRemote().sendText("Reversed: " + new StringBuilder(message));
        // });
        sessions.forEach(s -> {
            if (s.getId() != session.getId()) {
                s.getAsyncRemote().sendText(new StringBuilder(message).toString());
            }
        });
    }

    @OnError
    public void onError(Session session, Throwable cause) {
        System.out.println(session.getId());
    }
}
