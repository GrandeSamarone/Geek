package com.example.fulanoeciclano.geek.Model;

/**
 * Created by fulanoeciclano on 20/05/2018.
 */

public class Comentario {
    public String uid;
    public String author;
    public String text;

    public Comentario() {
    }

    public Comentario(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }
}
