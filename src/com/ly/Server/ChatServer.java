package com.ly.Server;

import com.google.gson.Gson;
import com.ly.Client.ChatClient;
import com.ly.Message.Msg;

import javax.sound.midi.Receiver;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * @jsphlim
 * 2017.12.10
 */

public class ChatServer extends Thread {
    private static int PORT = 10005;
    private static DatagramSocket server = null;
    private static HashMap<String,ArrayList<ChatClient>> homes = new HashMap<String, ArrayList<ChatClient>>();

    public ChatServer(){
        try {
            server = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public static void loginHome(String groupID, ChatClient client) {
        // 通过聊天室ID，获取该聊天室的所有在线用户
        ArrayList<ChatClient> clients = homes.get(groupID);
        if (clients == null) {
            clients = new ArrayList<ChatClient>();
        }
        // 将此次进入聊天室的用户登记
        clients.add(client);
        // 更新聊天室信息
        homes.put(groupID, clients);
    }

    public void run(){
        while(true){
            ReceiveMsg();
        }
    }

    private void ReceiveMsg(){
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf,buf.length);
        while(true){
            try {
                server.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            String content = new String(packet.getData(), 0, packet.getLength());
            System.out.print(content);
            Msg msg = gson.fromJson(content,Msg.class);

            ArrayList<ChatClient> clients =homes.get(msg.getClientId());

            for (ChatClient client : clients) {
                client.CallBackMsg(msg);
            }
        }
    }

}
