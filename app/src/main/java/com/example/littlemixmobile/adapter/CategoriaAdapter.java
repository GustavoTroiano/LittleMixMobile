package com.example.littlemixmobile.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.model.Categoria;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter <CategoriaAdapter.MyViewHolder> {

    private int layout;
    private boolean background;
    private final List<Categoria> categoriaList;
    private onClick onClick;
    private int row_index = 0;

    public CategoriaAdapter(int layout, boolean background, List<Categoria> categoriaList, CategoriaAdapter.onClick onClick) {
        this.layout = layout;
        this.background = background;
        this.categoriaList = categoriaList;
        this.onClick = onClick;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Categoria categoria = categoriaList.get(position);

        if (background){

            holder.itemView.setOnClickListener(view -> {
                onClick.onClickListener(categoria);

                row_index = holder.getAdapterPosition();
                notifyDataSetChanged();
            });

            if (row_index == holder.getAdapterPosition()){
                holder.itemView.setBackgroundResource(R.drawable.bg_categoria_home);
                holder.nomeCategoria.setTextColor(Color.parseColor("#a2ebea"));
                holder.imagemCategoria.setColorFilter(Color.parseColor("#a2ebea"), PorterDuff.Mode.SRC_IN);

            }else{
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.nomeCategoria.setTextColor(Color.parseColor("#808080"));
                holder.imagemCategoria.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
            }
        }else{
            holder.itemView.setOnClickListener(view -> onClick.onClickListener(categoria));
        }


        holder.nomeCategoria.setText(categoria.getNome());

        Picasso.get().load(categoria.getUrlImagem()).into(holder.imagemCategoria);


    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    public interface onClick{
        void onClickListener(Categoria categoria);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagemCategoria;
        TextView nomeCategoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemCategoria = itemView.findViewById(R.id.imagemCategoria);
            nomeCategoria = itemView.findViewById(R.id.nomeCategoria);
        }
    }
}
