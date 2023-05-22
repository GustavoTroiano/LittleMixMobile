package com.example.littlemixmobile.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.databinding.ActivityUsuarioEnderecoBinding;

public class UsuarioEnderecoActivity extends AppCompatActivity {

    private ActivityUsuarioEnderecoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioEnderecoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.include.textTitulo.setText("Meus endereços");
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());

        iniciaComponentes();

        configClicks();

    }

    private void configClicks(){
        binding.include.btnAdd.setOnClickListener(v ->
                startActivity(new Intent(this, UsuarioFormEnderecoActivity.class)));
    }

    private void iniciaComponentes(){
        binding.include.textTitulo.setText("Meus endereços");
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
    }
}