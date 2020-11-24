package com.example.telas_v1.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserTrabalhador implements Parcelable {

    private String id, email, nome, senha, tipoUser, dataNasc, urlFotoPerfil, urlFotoFundo, sobreMim, contatos;
    private String profUm, profDois, profTres, filtoFixo;
    private float stars, myPreco;
    private int countStars;
    private List<String> keys;

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
        filtoFixo = in.readString();
        stars = in.readFloat();
        myPreco = in.readFloat();
        countStars = in.readInt();
        keys = in.createStringArrayList();
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
        dest.writeString(sobreMim);
        dest.writeString(contatos);
        dest.writeString(profUm);
        dest.writeString(profDois);
        dest.writeString(profTres);
        dest.writeString(filtoFixo);
        dest.writeFloat(stars);
        dest.writeFloat(myPreco);
        dest.writeInt(countStars);
        dest.writeStringList(keys);
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

    public String getFiltoFixo() {
        return filtoFixo;
    }

    public void setFiltoFixo(String filtoFixo) {
        this.filtoFixo = filtoFixo;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public float getMyPreco() {
        return myPreco;
    }

    public void setMyPreco(float myPreco) {
        this.myPreco = myPreco;
    }

    public int getCountStars() {
        return countStars;
    }

    public void setCountStars(int countStars) {
        this.countStars = countStars;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }
}