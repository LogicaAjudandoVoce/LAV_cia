package com.example.telas_v1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserTrabalhador implements Parcelable {

    private String id, email, nome, senha, tipoUser, dataNasc, urlFotoPerfil, urlFotoFundo, sobreMim, contatos;
    private String profUm, profDois, profTres;
    float myPreco;

    public UserTrabalhador() {
    }

    protected UserTrabalhador(Parcel in) {
        id = in.readString();
        email = in.readString();
        nome = in.readString();
        senha = in.readString();
        tipoUser = in.readString();
        dataNasc = in.readString();
        urlFotoPerfil = in.readString();
        urlFotoFundo = in.readString();
        sobreMim = in.readString();
        contatos = in.readString();
        profUm = in.readString();
        profDois = in.readString();
        profTres = in.readString();
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

    public String getSobreMim() {
        return sobreMim;
    }

    public void setSobreMim(String sobreMim) {
        this.sobreMim = sobreMim;
    }

    public String getContatos() {
        return contatos;
    }

    public void setContatos(String contatos) {
        this.contatos = contatos;
    }

    public String getProfUm() {
        return profUm;
    }

    public void setProfUm(String profUm) {
        this.profUm = profUm;
    }

    public String getProfDois() {
        return profDois;
    }

    public void setProfDois(String profDois) {
        this.profDois = profDois;
    }

    public String getProfTres() {
        return profTres;
    }

    public void setProfTres(String profTres) {
        this.profTres = profTres;
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
        parcel.writeString(sobreMim);
        parcel.writeString(contatos);
        parcel.writeString(profUm);
        parcel.writeString(profDois);
        parcel.writeString(profTres);
        parcel.writeFloat(myPreco);
    }
}