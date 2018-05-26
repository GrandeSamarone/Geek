package com.example.fulanoeciclano.geek.helper;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fulanoeciclano.geek.Config.ConfiguracaoFirebase;
import com.example.fulanoeciclano.geek.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by fulanoeciclano on 21/05/2018.
 */

public class UsuarioFirebase {


    public static String getIdentificadorUsuario(){
        FirebaseUser usuario = ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser();
        String tel =usuario.getPhoneNumber();
        String identificadorUsuario= Base64Custom.codificarBase64(tel);

        return identificadorUsuario;
    }

    public static FirebaseUser getUsuarioAtual(){

        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return  usuario.getCurrentUser();
    }

    public static boolean atualizarNomeUsuario(String nome){

        try{
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(!task.isSuccessful()){
                        Log.d("Perfil","erro ao atualizar o nome do perfil");
                    }
                }
            });
            return true;
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }

    }


    public static boolean atualizarFotoUsuario(Uri url){

        try{
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(url)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(!task.isSuccessful()){
                        Log.d("Perfil","erro ao atualizar a foto do perfil");
                    }
                }
            });
            return true;
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }

    }
    public static Usuario getDadosUsuarioLogado(){
        FirebaseUser firebaseuser = getUsuarioAtual();
        Usuario usuario = new Usuario();
        usuario.setNome(firebaseuser.getDisplayName());

        if(firebaseuser.getPhotoUrl()==null){
            usuario.setFoto("");
        }else{
            usuario.setFoto(firebaseuser.getPhotoUrl().toString());
        }
        return usuario;

    }
}
