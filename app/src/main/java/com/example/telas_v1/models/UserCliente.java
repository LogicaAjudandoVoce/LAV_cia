package com.example.telas_v1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserCliente implements Parcelable {

    private String id, email, nome, senha, tipoUser, dataNasc, urlFotoPerfil, urlFotoFundo, sobremim, contatos;
    private float stars;
    private int countStar;

    public UserCliente() {

    }

    protected UserCliente(Parcel in) {
        id = in.readString();
        email = in.readString();
        nome = in.readString();
        senha = in.readString();
        tipoUser = in.readString();
        dataNasc = in.readString();
        urlFotoPerfil = in.readString();
        urlFotoFundo = in.readString();
        sobremim = in.readString();
        contatos = in.readString();
        stars = in.readFloat();
        countStar = in.readInt();
    }

    public static final Creator<UserCliente> CREATOR = new Creator<UserCliente>() {
        @Override
        public UserCliente createFromParcel(Parcel in) {
            return new UserCliente(in);
        }

        @Override
        public UserCliente[] newArray(int size) {
            return new UserCliente[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public String getUrlFotoFundo() {
        return urlFotoFundo;
    }

    public void setUrlFotoFundo(String urlFotoFundo) {
        this.urlFotoFundo = urlFotoFundo;
    }

    public String getSobremim() {
        return sobremim;
    }

    public void setSobremim(String sobremim) {
        this.sobremim = sobremim;
    }

    public String getContatos() {
        return contatos;
    }

    public void setContatos(String contatos) {
        this.contatos = contatos;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public int getCountStar() {
        return countStar;
    }

    public void setCountStar(int countStar) {
        this.countStar = countStar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(nome);
        parcel.writeString(senha);
        parcel.writeString(tipoUser);
        parcel.writeString(dataNasc);
        parcel.writeString(urlFotoPerfil);
        parcel.writeString(urlFotoFundo);
        parcel.writeString(sobremim);
        parcel.writeString(contatos);
        parcel.writeFloat(stars);
        parcel.writeInt(countStar);
    }
}
