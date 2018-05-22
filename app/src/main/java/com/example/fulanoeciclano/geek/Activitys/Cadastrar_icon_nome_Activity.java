package com.example.fulanoeciclano.geek.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.fulanoeciclano.geek.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cadastrar_icon_nome_Activity extends AppCompatActivity {

    private CircleImageView imageNick;
    private AlertDialog alerta;
    public static final int SELECAO_GALERIA = 12;
    public static final int SELECAO_ICONES = 34;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_icon_nome_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configuracao Inicial
        imageNick = findViewById(R.id.circleImageViewFotoPerfil);

        //ao clicar na iimagem abre dialig para escolher entre icone ou galeria
        imageNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Escolher_Foto_Perfil();
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //recebendo a imagem escolhida da pagina de icone
        final Bundle it = getIntent().getExtras();
        if(it!=null){

            Picasso.with(this)
                    .load(it.getString("caminho_foto"))
                    .placeholder(R.drawable.padrao)   // optional
                    .error(R.drawable.carregando)      // optional
                    .resize(400, 400)                        // optional
                    .into(imageNick);

        }else{
            Toast.makeText(this, "Erro Tente Novamente", Toast.LENGTH_SHORT).show();
        }


    }
    //recebe a imagem da galeia
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if(resultCode == RESULT_OK){

            Bitmap imagem =null;

            if(requestCode == SELECAO_GALERIA){
                Uri selectedImage = data.getData();
                Toast.makeText(getApplicationContext(),"Carregando...", Toast.LENGTH_SHORT).show();

                Intent it = new Intent(Cadastrar_icon_nome_Activity.this, Cadastrar_icon_nome_Activity.class);
                it.putExtra("caminho_foto", selectedImage.toString());
                startActivity(it);
            }

        }
    }

    //dialog de opcoes
    private void  Escolher_Foto_Perfil() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout tela_opcao_foto.xml_foto.xml na view
        View view = li.inflate(R.layout.tela_opcao_foto, null);
        //definimos para o botão do layout um clickListener
        view.findViewById(R.id.botaogaleria).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(it.resolveActivity(getPackageManager())!=null)  {
                    startActivityForResult(it, SELECAO_GALERIA);
                }
                //desfaz o tela_opcao_foto.
                alerta.dismiss();
            }
        });
        view.findViewById(R.id.botaoicones).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //exibe um Toast informativo.
                Intent it = new Intent(Cadastrar_icon_nome_Activity.this, PageIcon.class);
                startActivity(it);
                //desfaz o tela_opcao_foto.
                alerta.dismiss();
            }
        });

       //Dialog de tela
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Foto de Perfil");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();

    }

}
