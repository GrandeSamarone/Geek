package com.example.fulanoeciclano.geek.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fulanoeciclano.geek.Model.Gibi;
import com.example.fulanoeciclano.geek.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by fulanoeciclano on 19/05/2018.
 */

public class GibiAdapter extends RecyclerView.Adapter<GibiAdapter.MyViewHolder> {

  private Context c;
  private List<Gibi> gibis;

    public GibiAdapter(List<Gibi> ListaGibi, Context context) {
        this.gibis =ListaGibi;
        this.c = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptergibi,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gibi gibi  = gibis.get(position);
        holder.nome.setText(gibi.getNome());

        if(gibi.getIconegibi() !=null){
            Uri uri = Uri.parse(gibi.getIconegibi());

            DraweeController controllerOne = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();

            holder.foto.setController(controllerOne);
            /* Glide.with(c)
            .load(uri)
            .into(holder.foto);*/


       }else{
           holder.foto.setImageResource(R.drawable.carregando);
        }

    }

    @Override
    public int getItemCount() {
        return gibis.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        SimpleDraweeView foto;
        TextView nome;

        public MyViewHolder(View itemView) {
            super(itemView);

            foto = ( SimpleDraweeView)itemView.findViewById(R.id.iconegibi);
            nome = itemView.findViewById(R.id.nomegibi);
        }
    }
}
