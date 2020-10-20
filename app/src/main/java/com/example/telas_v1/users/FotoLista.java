package com.example.telas_v1.users;

public class FotoLista {
    private String foto, id;

    public FotoLista(){
    }

    public FotoLista(String id, String foto) {
        this.id = id;
        this.foto = foto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
