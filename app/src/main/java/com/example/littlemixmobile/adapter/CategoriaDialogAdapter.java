package com.example.littlemixmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.littlemixmobile.R;
import com.example.littlemixmobile.model.Categoria;


import java.util.List;

public class CategoriaDialogAdapter extends RecyclerView.Adapter <CategoriaDialogAdapter.MyViewHolder> {

    private final List<String> idsCategoriasSelecionadas;
    private final List<Categoria> categoriaList;
    private final Context context;
    private final onClick onClick;


    public CategoriaDialogAdapter(List<String> idsCategoriasSelecionadas, List<Categoria> categoriaList, Context context, CategoriaDialogAdapter.onClick onClick) {
        this.idsCategoriasSelecionadas = idsCategoriasSelecionadas;
        this.categoriaList = categoriaList;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria_dialog, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Categoria categoria = categoriaList.get(position);


        Glide.with(context)
                .load(categoria.getUrlImagem())
                .centerCrop()
                .into(holder.imagemCategoria);


        holder.nomeCategoria.setText(categoria.getNome());

        if (idsCategoriasSelecionadas.contains(categoria.getId())){
            holder.checkBox.setChecked(true);
        }

        holder.itemView.setOnClickListener(view -> {
            onClick.onClickListener(categoria);

            holder.checkBox.setChecked(!holder.checkBox.isChecked());
        });
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

        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemCategoria = itemView.findViewById(R.id.imagemCategoria);
            nomeCategoria = itemView.findViewById(R.id.nomeCategoria);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
