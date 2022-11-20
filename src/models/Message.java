package models;

import java.io.Serializable;

public class Message implements Serializable {
    private String content;
    private String clientType;

    public Message(String message, String type) {
        this.content = message;
        this.clientType = type;
    }

    public String getMessage() {
        return this.content;
    }

    public String getClientConnected() {
        return this.clientType;
    }
}
