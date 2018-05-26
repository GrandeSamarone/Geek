package com.example.fulanoeciclano.geek.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.example.fulanoeciclano.geek.Adapter.IconeAdapter;
import com.example.fulanoeciclano.geek.Config.ConfiguracaoFirebase;
import com.example.fulanoeciclano.geek.Model.Icones;
import com.example.fulanoeciclano.geek.R;
import com.example.fulanoeciclano.geek.helper.RecyclerItemClickListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class PageIcon extends AppCompatActivity {
    private RecyclerView recyclerViewIcone;
    private DatabaseReference Icones_Conf;
    private CardView cd;
    private ArrayList<Icones> Listicones = new ArrayList<>();
    private IconeAdapter adapterIcone;
    private ValueEventListener valueEventListenerIcone;
    public static final int SELECAO_ICONE = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_page_icon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configuracoes

        recyclerViewIcone = (RecyclerView) findViewById(R.id.mRecylcerID);
        Icones_Conf= ConfiguracaoFirebase.getFirebaseDatabase().child("Icones");

        //Configurar Adapter
        adapterIcone = new IconeAdapter(PageIcon.this,Listicones);


           //Configuracao RecycleView
        recyclerViewIcone.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewIcone.setHasFixedSize(true);
        recyclerViewIcone.setAdapter(adapterIcone);

        recyclerViewIcone.addOnItemTouchListener(new RecyclerItemClickListener(
                this, recyclerViewIcone, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(PageIcon.this, Cadastrar_icon_nome_Activity.class);
                intent.putExtra("caminho_foto",Listicones.get(position).getUrl());
             startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));




    }

    @Override
    protected void onStart() {
        super.onStart();
        RecuperarIcones();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Icones_Conf.removeEventListener(valueEventListenerIcone);
    }

public void RecuperarIcones(){
        Listicones.clear();
        valueEventListenerIcone = Icones_Conf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dados:  dataSnapshot.getChildren()){
                    Icones icone = dados.getValue(Icones.class);
                   Listicones.add(icone);
                }
                adapterIcone.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode==9 && resultCode == RESULT_OK) {
            Uri photoData = data.getData();
            if (photoData!=null) {
                Intent it = new Intent(PageIcon.this, Cadastrar_icon_nome_Activity.class);
                it.putExtra("caminho_foto", photoData);
                startActivity(it);


            }

            Bitmap photoBitmap = null;
            try {
                photoBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoData);
                // imageNick.setImageBitmap(photoBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
