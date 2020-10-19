package com.example.telas_v1.postagemcliente;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

import java.util.List;

public class Postagem implements Parcelable {

    private String idCliente, idPost, nomeAutor, titulo, descricao, data, email;
    private double preco, latitude, longitude;
    private List<String> uri;

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
        preco = in.readDouble();
        latitude = in.readDouble();
        longitude = in.readDouble();
        uri = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCliente);
        dest.writeString(idPost);
        dest.writeString(nomeAutor);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(email);
        dest.writeDouble(preco);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeStringList(uri);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public List<String> getUri() {
        return uri;
    }

    public void setUri(List<String> uri) {
        this.uri = uri;
    }
}
