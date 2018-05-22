package com.example.fulanoeciclano.geek.Model;

import java.io.Serializable;

/**
 * Created by fulanoeciclano on 19/05/2018.
 */

public class Gibi  implements Serializable {

    private String id;
    private String nome;
    private String iconegibi;

    public Gibi() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIconegibi() {
        return iconegibi;
    }

    public void setIconegibi(String iconegibi) {
        this.iconegibi = iconegibi;
    }
}
