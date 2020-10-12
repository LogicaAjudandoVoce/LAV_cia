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
        myPreco = in.readFloat();
        urlFotoPerfil = in.readString();
        urlFotoFundo = in.readString();
    }

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

    public float getMyPreco() {
        return myPreco;
    }

    public void setMyPreco(float myPreco) {
        this.myPreco = myPreco;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(nome);
        dest.writeString(senha);
        dest.writeString(tipoUser);
        dest.writeString(dataNasc);
        dest.writeString(urlFotoPerfil);
        dest.writeString(urlFotoFundo);
        dest.writeFloat(myPreco);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
