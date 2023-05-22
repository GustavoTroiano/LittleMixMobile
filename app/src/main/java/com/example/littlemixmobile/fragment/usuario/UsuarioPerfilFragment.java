package com.example.littlemixmobile.fragment.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.activity.usuario.UsuarioEnderecoActivity;
import com.example.littlemixmobile.autenticacao.CadastroActivity;
import com.example.littlemixmobile.autenticacao.LoginActivity;
import com.example.littlemixmobile.databinding.FragmentUsuarioPerfilBinding;
import com.example.littlemixmobile.helper.FirebaseHelper;

public class UsuarioPerfilFragment extends Fragment {

    private FragmentUsuarioPerfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsuarioPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        configClicks();

    }

    private void configClicks() {
        binding.btnEntrar.setOnClickListener(v -> startActivity(LoginActivity.class));
        binding.btnCadastrar.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), CadastroActivity.class));
        });
        binding.btnMeusDados.setOnClickListener(v -> startActivity(LoginActivity.class));
        binding.btnEnderecos.setOnClickListener(v -> startActivity(UsuarioEnderecoActivity.class));
    }

    private void startActivity(Class<?> clazz){
        if (FirebaseHelper.getAutenticado()) {
            startActivity(new Intent(requireContext(), clazz));
        }else {
            startActivity(new Intent(requireContext(), LoginActivity.class));
        }
    }

}