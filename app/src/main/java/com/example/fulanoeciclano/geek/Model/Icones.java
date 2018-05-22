package com.example.fulanoeciclano.geek.Model;

import java.util.UUID;

/**
 * Created by fulanoeciclano on 20/05/2018.
 */

public class Icones {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String url;

    public Icones(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }
}
