package com.example.fulanoeciclano.geek.Activitys;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.fulanoeciclano.geek.Fragments.InicioFragment;
import com.example.fulanoeciclano.geek.Fragments.NoticiaFragment;
import com.example.fulanoeciclano.geek.Fragments.RankFragment;
import com.example.fulanoeciclano.geek.Fragments.TopicosFragment;
import com.example.fulanoeciclano.geek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {

  private Toolbar toolbar;
  private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        toolbar =findViewById(R.id.toolbarprincipal);
        toolbar.setTitle("Geek");
        setSupportActionBar(toolbar);


        //Configurar Abas
        final FragmentPagerItemAdapter  adapter= new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                 .add("Inicio", InicioFragment.class)
                 .add("Noticia", NoticiaFragment.class)
                 .add("Topicos", TopicosFragment.class)
                 .add("Tops", RankFragment.class)
                .create()
        );
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        SmartTabLayout ViewPageTab = findViewById(R.id.SmartTabLayout);
        ViewPageTab.setViewPager(viewPager);

    }

    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }
    public boolean  onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menusair:
                DeslogarUsuario();
                finish();
                break;
            case R.id.menuconfiguracoes:
                //abrirConfiguracoes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void DeslogarUsuario(){
        try {
            autenticacao.signOut();

        }catch (Exception e ){
            e.printStackTrace();
        }
    }
    public  void abrirConfiguracoes(){

    }
}
