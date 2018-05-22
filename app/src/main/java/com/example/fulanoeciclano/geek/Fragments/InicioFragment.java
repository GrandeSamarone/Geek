package com.example.fulanoeciclano.geek.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fulanoeciclano.geek.Adapter.GibiAdapter;
import com.example.fulanoeciclano.geek.Config.ConfiguracaoFirebase;
import com.example.fulanoeciclano.geek.Model.Gibi;
import com.example.fulanoeciclano.geek.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    private RecyclerView recyclerViewListaGibiMarvel;
    private RecyclerView recyclerViewListaGibiDC;
    private GibiAdapter adapterMarvel;
    private GibiAdapter adapterDC;
    private ArrayList<Gibi> ListaGibiMarvel = new ArrayList<>();
    private ArrayList<Gibi> ListaGibiDC = new ArrayList<>();
    private DatabaseReference GibiMarvel;
    private DatabaseReference GibiDC;
    private ValueEventListener valueEventListenerGibi;
    private ValueEventListener valueEventListenerDC;

    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fresco.initialize(getContext());
        View view= inflater.inflate(R.layout.fragment_inicio, container, false);

        //Configuracoes Iniciais
        recyclerViewListaGibiDC = view.findViewById(R.id.RecycleViewGibiDC);
        recyclerViewListaGibiMarvel = view.findViewById(R.id.RecycleViewGibis);
        GibiMarvel = ConfiguracaoFirebase.getFirebaseDatabase().child("Marvel");
        GibiDC = ConfiguracaoFirebase.getFirebaseDatabase().child("DC");

        //Configurar Adapter
        adapterMarvel=new GibiAdapter(ListaGibiMarvel,getActivity());
        adapterDC = new GibiAdapter(ListaGibiDC,getActivity());


        //Configurar recycleView Marvel
        RecyclerView.LayoutManager layoutManagerMarvel = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewListaGibiMarvel.setLayoutManager(layoutManagerMarvel);
        recyclerViewListaGibiMarvel.setHasFixedSize(true);
        recyclerViewListaGibiMarvel.setAdapter(adapterMarvel);

        //Configurar recycleView DC
        RecyclerView.LayoutManager layoutManagerdc = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewListaGibiDC.setLayoutManager(layoutManagerdc);
        recyclerViewListaGibiDC.setHasFixedSize(true);
        recyclerViewListaGibiDC.setAdapter(adapterDC);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        RecuperarMarvel();
        RecuperarDC();
    }

    @Override
    public void onStop() {
        super.onStop();
        GibiMarvel.removeEventListener(valueEventListenerGibi);
        GibiDC.removeEventListener(valueEventListenerDC);
    }


    public  void RecuperarMarvel(){
        ListaGibiMarvel.clear();
      valueEventListenerGibi =  GibiMarvel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              for(DataSnapshot dados:  dataSnapshot.getChildren()){
                  Gibi gibi = dados.getValue(Gibi.class);
                  ListaGibiMarvel.add(gibi);
              }
                adapterMarvel.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void RecuperarDC(){
        valueEventListenerDC = GibiDC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dados:  dataSnapshot.getChildren()){
                    Gibi gibi = dados.getValue(Gibi.class);
                   ListaGibiDC.add(gibi);
                }
               adapterDC.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
