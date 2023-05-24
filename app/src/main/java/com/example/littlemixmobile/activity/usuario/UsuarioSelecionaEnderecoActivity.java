package com.example.littlemixmobile.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.databinding.ActivityUsuarioSelecionaEnderecoBinding;

public class UsuarioSelecionaEnderecoActivity extends AppCompatActivity {

    private ActivityUsuarioSelecionaEnderecoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioSelecionaEnderecoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClick();

    }

    private void configClick(){
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }

    private void iniciaComponentes(){
        binding.include.textTitulo.setText("Endereço de entrega");
    }

}