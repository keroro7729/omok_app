package com.example.omokproject2.api;

import android.util.Log;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.net.URI;
import java.util.StringTokenizer;

@ClientEndpoint
public class WebSocket {

    @OnOpen
    public void onOpen(Session session) {
        try {
            //
            session.getBasicRemote().sendText("my data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        StringTokenizer st = new StringTokenizer(message);
        switch(st.nextToken()){
            case "Wait": return;
            case "Success":
                // get game data and make event to listener
            case "Fail": // destroy
            default: Log.e("makeMatch", "unknown message: "+message);
        }
    }

    public static void main(String[] args) {
        try {
            URI uri = new URI("ws://192.168.50.61:8080/make-match");
            javax.websocket.ContainerProvider.getWebSocketContainer().connectToServer(WebSocket.class, uri);
        } catch (Exception e) {
            Log.e("ws", e.getLocalizedMessage());
        }
    }
}