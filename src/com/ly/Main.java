package com.ly;

import com.ly.Client.ChatClient;
import com.ly.Server.ChatServer;

public class Main {

    public static void main(String[] args) {
        ChatServer r = new ChatServer();
        r.start();

        ChatClient c1 = new ChatClient("001", "小红");
        ChatClient c2 = new ChatClient("001", "小绿");
        ChatClient c3 = new ChatClient("001", "小黑");
    }
}
