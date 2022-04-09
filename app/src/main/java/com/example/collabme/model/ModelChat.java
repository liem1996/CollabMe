package com.example.collabme.model;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ModelChat {

    public static final ModelChat instance2 = new ModelChat();

    Socket socket;

    public interface ListentoSocke {
        void onComplete(int code);
    }

    public interface returnMessege {
        void onComplete(String messege);
    }

    public void ChatFuctionWithSocke(String username, ListentoSocke listentoSocke) {

        try {

            socket = IO.socket("http://10.0.2.2:5000");
            //create connection
            socket.connect();

            // emit the event join along side with the nickname
            socket.emit("join", username);



            listentoSocke.onComplete(200);

        } catch (URISyntaxException e) {
            e.printStackTrace();

        }

    }

    public void textingbetwen(String username,ListentoSocke listentoSocke){
        socket.emit("messagedetection",username);

    }


}
