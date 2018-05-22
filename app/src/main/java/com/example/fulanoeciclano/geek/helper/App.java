package com.example.fulanoeciclano.geek.helper;

import android.app.Application;

import com.example.fulanoeciclano.geek.Config.ConfiguracaoFirebase;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by fulanoeciclano on 19/05/2018.
 */

public class App extends Application {
    private FirebaseDatabase data;
    @Override
    public void onCreate() {
        super.onCreate();
        data = ConfiguracaoFirebase.getDatabase();
    }
}