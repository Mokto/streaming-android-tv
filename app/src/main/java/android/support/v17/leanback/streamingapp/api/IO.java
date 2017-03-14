package android.support.v17.leanback.streamingapp.api;

import android.util.Log;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class IO {

    private static IO instance;

    public Socket socket;


    public static Emitter emit(final String event, final String data, final Ack callback) {
        return getInstance().socket.emit(event, data, callback);
    }
    public static Emitter emit(final String event, final JSONObject params, final Ack callback) {
        return getInstance().socket.emit(event, params, callback);
    }
    public static Emitter emit(final String event, final Ack callback) {
        return getInstance().socket.emit(event, callback);
    }

    private IO() {
        try {
            socket = io.socket.client.IO.socket("http://192.168.1.103:8080/");

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.i("socket-io", "Socket IO connected");
                }

            }).on("event", new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.i("socket-io", "Socket IO disconnected");
                }
            });

            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public static IO getInstance() {
        if(instance == null) {
            instance = new IO();
        }
        return instance;
    }
}
