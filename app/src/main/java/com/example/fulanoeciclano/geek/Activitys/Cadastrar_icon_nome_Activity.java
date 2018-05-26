package com.example.fulanoeciclano.geek.Activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.fulanoeciclano.geek.Config.ConfiguracaoFirebase;
import com.example.fulanoeciclano.geek.Model.Usuario;
import com.example.fulanoeciclano.geek.R;
import com.example.fulanoeciclano.geek.helper.UsuarioFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cadastrar_icon_nome_Activity extends AppCompatActivity {

    private CircleImageView imageNick;
    private AlertDialog alerta;
    public static final int SELECAO_GALERIA = 12;
    public static final int SELECAO_ICONE = 34;
    private StorageReference storageReference;
    private DatabaseReference database;
    private String identificadorUsuario;
    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_icon_nome_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configuracao Inicial
        imageNick = findViewById(R.id.circleImageViewFotoPerfil);
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();
        database = ConfiguracaoFirebase.getDatabase().getReference().child("usuarios");
        identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        usuarioLogado=UsuarioFirebase.getDadosUsuarioLogado();

        //ao clicar na iimagem abre dialig para escolher entre icone ou galeria
        imageNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Escolher_Foto_Perfil();
            }
        });

        //Recuperar a imagem Icones da pagina Icones
         RecuperarIcone();


       //Botao Cadastrar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_cadastrar_icone);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            CadastrarIcone();
            }
        });




    }
    //Recebendo Icone
    private void RecuperarIcone() {
        Bitmap imagem =null;
        // final Bundle it = getIntent().getExtras();


        FirebaseStorage storage = FirebaseStorage.getInstance();

        if (getIntent().hasExtra("caminho_foto")) {
            final Uri url = Uri.parse(((getIntent().getStringExtra("caminho_foto"))));

            final StorageReference storageReference = storage.getReferenceFromUrl(String.valueOf(url));

            //Progresso
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Aguarde");
            progressDialog.setMessage("carregando... ");
            progressDialog.show();

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(Cadastrar_icon_nome_Activity.this)
                            .load(uri.toString())
                            .placeholder(R.drawable.padrao)
                            .error(R.drawable.carregando)
                            .resize(400, 400)
                            .into(imageNick);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    progressDialog.dismiss();
                }
            });

        }
    }

   //Cadastrando Icone
    private void CadastrarIcone() {
    imageNick.setDrawingCacheEnabled(true);
    imageNick.buildDrawingCache();

    Bitmap bitmap = imageNick.getDrawingCache();
     if(bitmap!=null){
         //Recuperar dados da imagem  para o  Firebase
         ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
         byte[] dadosImagem= baos.toByteArray();

         //Salvar no Firebase
         StorageReference MontarImagemReference =
                 storageReference
                         .child("imagens")
                         .child("perfil")
                         .child(identificadorUsuario)
                         .child("perfil.jpg");
         //Progress
         final ProgressDialog progressDialog = new ProgressDialog(this);
         progressDialog.setTitle("Carregando...");
         progressDialog.show();
         UploadTask uploadTask = MontarImagemReference.putBytes(dadosImagem);
         //caso de errado
         uploadTask.addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 progressDialog.dismiss();
                 Toast.makeText(Cadastrar_icon_nome_Activity.this, "Erro ao carregar a imagem", Toast.LENGTH_SHORT).show();
             }
             //caso o carregamento no firebase de tudo certo
         }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 progressDialog.dismiss();
                 Toast.makeText(Cadastrar_icon_nome_Activity.this, "Imagem Carregada com Sucesso", Toast.LENGTH_SHORT).show();

                 Uri url= taskSnapshot.getDownloadUrl();


             }

         }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                 double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                 progressDialog.setMessage("carregando " + (int) progress + "%");
             }
         });

     }
    }










    //recebe a imagem da galeia
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {

        if (resultCode == RESULT_OK) {

            Bitmap imagem = null;

            try {
                switch (requestCode) {

                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;

            }
                if(imagem !=null){
                    imageNick.setImageBitmap(imagem);
                    //Recuperar dados da imagem  para o  Firebase
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte[] dadosImagem= baos.toByteArray();

                    //Salvar no Firebase
                    StorageReference  imagemRef = storageReference
                            .child("imagens")
                            .child("perfil")
                            .child(identificadorUsuario)
                            .child("perfil.jpg");
                    //Progress
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Aguarde");
                    progressDialog.show();
                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Cadastrar_icon_nome_Activity.this, "Erro ao carregar a imagem", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Cadastrar_icon_nome_Activity.this, "Imagem Carregada com Sucesso", Toast.LENGTH_SHORT).show();


                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setMessage("carregando... ");
                        }
                    });


                }
            } catch (Exception e) {
                e.printStackTrace();
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


                Intent it = new Intent(Cadastrar_icon_nome_Activity.this,PageIcon.class);
                startActivityForResult(it, SELECAO_ICONE);
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



    //PERMISSOES
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for( int permissaoResultado: grantResults){
            if (permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private  void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissôes Negadas");
        builder.setMessage("Para ultilizar o app é nescessario aceitar as permissôes");
        builder.setCancelable(false);
        builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(Cadastrar_icon_nome_Activity.this,Cadastrar_icon_nome_Activity.class);
                startActivity(it);

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
