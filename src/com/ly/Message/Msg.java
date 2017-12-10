package com.ly.Message;

public class Msg {
    private String ClientId;
    private String ClientName;
    private String text;

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String groupId) {
        this.ClientId = ClientId;
    }

    public String getUserName() {
        return ClientName;
    }

    public void setUserName(String userName) {
        this.ClientName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
