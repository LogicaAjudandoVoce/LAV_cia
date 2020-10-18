package com.example.telas_v1.postagemcliente;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

import java.util.List;

public class Postagem implements Parcelable {

    private String idCliente, idPost, titulo, descricao;
    private double preco, latitude, longitude;
    private List<Uri> uri;

    public Postagem() {
    }

    protected Postagem(Parcel in) {
        idCliente = in.readString();
        idPost = in.readString();
        titulo = in.readString();
        descricao = in.readString();
        preco = in.readDouble();
        latitude = in.readDouble();
        longitude = in.readDouble();
        uri = in.createTypedArrayList(Uri.CREATOR);
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

    public List<Uri> getUri() {
        return uri;
    }

    public void setUri(List<Uri> uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idCliente);
        parcel.writeString(idPost);
        parcel.writeString(titulo);
        parcel.writeString(descricao);
        parcel.writeDouble(preco);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeTypedList(uri);
    }
}
