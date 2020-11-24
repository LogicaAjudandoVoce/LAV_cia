package com.example.telas_v1.models;

public class FotoLista {
    private String link, id;

    public FotoLista(){
    }

    public FotoLista(String id, String link) {
        this.id = id;
        this.link = link;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
