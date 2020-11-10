package com.example.telas_v1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Postagem implements Parcelable {

    private String idCliente, idPost, nomeAutor, titulo, descricao, data, email, miniDescricao;
    private boolean conclusao;
    private double preco, latitude, longitude;

    public Postagem() {
    }

    protected Postagem(Parcel in) {
        idCliente = in.readString();
        idPost = in.readString();
        nomeAutor = in.readString();
        titulo = in.readString();
        descricao = in.readString();
        data = in.readString();
        email = in.readString();
        miniDescricao = in.readString();
        conclusao = in.readByte() != 0;
        preco = in.readDouble();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Postagem> CREATOR = new Creator<Postagem>() {
        @Override
        public Postagem createFromParcel(Parcel in) {
            return new Postagem(in);
        }

        @Override
        public Postagem[] newArray(int size) {
            return new Postagem[size];
        }
    };

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMiniDescricao() {
        return miniDescricao;
    }

    public void setMiniDescricao(String miniDescricao) {
        this.miniDescricao = miniDescricao;
    }

    public boolean isConclusao() {
        return conclusao;
    }

    public void setConclusao(boolean conclusao) {
        this.conclusao = conclusao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idCliente);
        parcel.writeString(idPost);
        parcel.writeString(nomeAutor);
        parcel.writeString(titulo);
        parcel.writeString(descricao);
        parcel.writeString(data);
        parcel.writeString(email);
        parcel.writeString(miniDescricao);
        parcel.writeByte((byte) (conclusao ? 1 : 0));
        parcel.writeDouble(preco);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}