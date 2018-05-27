package com.example.fulanoeciclano.geek.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.fulanoeciclano.geek.Model.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.phone.CountryListSpinner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fulanoeciclano on 13/05/2018.
 */

public class Autenticacao extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseAuth;
    private Toolbar toolbar;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private CountryListSpinner mCountryListSpinner;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;
    private String mUsername;
    private Usuario usuario;


    private Intent cadastrocomSucesso;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicia o banco do firebase

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
      Log.i("ss", String.valueOf(mCountryListSpinner));
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //onSignedInInitialize(user.getDisplayName());
                    onSignedInInitialize(user.getPhoneNumber());

                    cadastrocomSucesso= new Intent(Autenticacao.this,Cadastrar_icon_nome_Activity.class);
                    startActivity(cadastrocomSucesso);
                    //Toast.makeText(Autenticacao.this, "bem vindo"+mUsername, Toast.LENGTH_SHORT).show();

                } else {


                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                   // List<AuthUI.IdpConfig> providers = Collections.singletonList(
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                          //  new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()

                    );
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)


                                    //.setTheme(R.style.AppTheme)

                                    .build(),
                            RC_SIGN_IN);

                }


            }
        };

    }



    private void onSignedInInitialize(String username) {
        mUsername = username;

        Toast.makeText(this, "legal "+mUsername, Toast.LENGTH_SHORT).show();

        //attachDatabaseReadListener();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Cadastro com Sucesso!", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    protected void onStart(){
        super.onStart();
        //recupera usuario atual
        FirebaseUser usuarioAtual= mFirebaseAuth.getCurrentUser();
        if(usuarioAtual!=null ){
            Intent it = new Intent(Autenticacao.this,MainActivity.class);
            startActivity(it);
            //recuperando o email
            Log.i("tst",usuarioAtual.getPhoneNumber());
        }
    }
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener!=null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        //  detachDatabaseReadListener();
        // mMessageAdapter.clear();
    }


}