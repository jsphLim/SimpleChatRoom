package com.ly.Client;

import com.ly.Message.Msg;
import com.ly.Server.ChatServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;

public class ChatClient extends Frame{

    private String clientID;
    private String ClientName;
    private DatagramSocket msg;
    private int PORT = 10005;

    private InetAddress ip;

    TextField textField = new TextField(15);
    TextArea textArea = new TextArea();
    Button send = new Button("Send");

    public ChatClient(String clientID,String clientName){
        super("Chat:"+ clientID+" "+clientName);
        this.clientID=clientID;
        this.ClientName=clientName;
        this.add("North",textField);
        this.add("Center",textArea);
        this.add("South",send);
        this.setSize(400,300);
        this.show();
        InitClient();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                msg.close();
                System.exit(0);
            }
        });


        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = textField.getText();
                SendMsg(content);
                textField.setText("");
            }
        });
    }

    private void SendMsg(String content){
        String message = messageFormat(content);
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf,buf.length,ip,PORT);

        try {
            msg.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("消息发送异常");
        }
    }

    private void InitClient(){
        ChatServer.loginHome(clientID,this);

        try {
            msg = new DatagramSocket();
            ip = InetAddress.getByName("127.0.0.1");

        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("连接异常....");
        } catch (UnknownHostException e) {
            System.out.println("host异常....");
            e.printStackTrace();
        }
    }

    public void CallBackMsg(Msg msg){
        textArea.append(msg.getUserName()+":"+msg.getText());
        textArea.append("\n");
    }

    private String messageFormat(String content) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"ClientId\":").append("\"").append(clientID).append(
                "\",");
        buffer.append("\"ClientName\":\"").append(ClientName).append("\",");
        buffer.append("\"text\":\"").append(content).append("\"}");
        System.out.print(buffer);
        return buffer.toString();

    }


}
