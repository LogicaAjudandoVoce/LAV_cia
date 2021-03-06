package com.example.telas_v1.models;

public class Mensagem {
    private String text;
    private long timestamp;
    private String fromId;
    private String toId;
    private PostagemAux post;

    public PostagemAux getPost() {
        return post;
    }

    public void setPost(PostagemAux post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}
