package com.example.telas_v1.users;

import android.os.Parcel;
import android.os.Parcelable;

public class UserTrabalhador implements Parcelable {

    private String id, email, nome, senha, tipoUser, dataNasc, urlFotoPerfil, urlFotoFundo;
    float myPreco;

    public UserTrabalhador(){}

    protected UserTrabalhador(Parcel in) {
        id = in.readString();
        email = in.readString();
        nome = in.readString();
        senha = in.readString();
        tipoUser = in.readString();
        dataNasc = in.readString();
        urlFotoPerfil = in.readString();
        urlFotoFundo = in.readString();
        myPreco = in.readFloat();
    }

    public static final Creator<UserTrabalhador> CREATOR = new Creator<UserTrabalhador>() {
        @Override
        public UserTrabalhador createFromParcel(Parcel in) {
            return new UserTrabalhador(in);
        }

        @Override
        public UserTrabalhador[] newArray(int size) {
            return new UserTrabalhador[size];
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

    public float getMyPreco() {
        return myPreco;
    }

    public void setMyPreco(float myPreco) {
        this.myPreco = myPreco;
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
        parcel.writeFloat(myPreco);
    }
}
