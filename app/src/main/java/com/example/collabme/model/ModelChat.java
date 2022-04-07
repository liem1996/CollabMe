package com.example.collabme.model;

import com.example.collabme.objects.tokensrefresh;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ModelChat {

    public static final ModelChat instance2 = new ModelChat();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();


    public interface ListentoSocke {
        void onComplete(int code);
    }

    public void ChatFuctionWithSocke(String username,ListentoSocke listentoSocke){
         Socket socket;

        try {
        //if you are using a phone device you should connect to same local network as your laptop and disable your pubic firewall as well

            socket = IO.socket("http://10.0.2.2:4000");
            //create connection
            socket.connect();

        // emit the event join along side with the nickname
           socket.emit("join",username);

        } catch (URISyntaxException e) {
            e.printStackTrace();

        }

    }



}
