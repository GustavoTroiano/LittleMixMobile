package com.example.littlemixmobile.fragment.loja;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.littlemixmobile.activity.loja.LojaConfigActivity;
import com.example.littlemixmobile.activity.loja.LojaPagamentosActivity;
import com.example.littlemixmobile.activity.usuario.MainActivityUsuario;
import com.example.littlemixmobile.databinding.FragmentLojaConfigBinding;
import com.example.littlemixmobile.helper.FirebaseHelper;


public class LojaConfigFragment extends Fragment {

    private FragmentLojaConfigBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLojaConfigBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configClicks();

    }

    private void configClicks(){
        binding.btnConfigLoja.setOnClickListener(view -> {
            startActivity(LojaConfigActivity.class);
        });



        binding.btnPagamentos.setOnClickListener(view -> {
            startActivity(LojaPagamentosActivity.class);
        });

        binding.btnDeslogar.setOnClickListener(view -> {

            FirebaseHelper.getAuth().signOut();
            requireActivity().finish();
            startActivity(MainActivityUsuario.class);
        });


    }

    private void startActivity(Class<?> clazz){
        startActivity(new Intent(requireContext(), clazz));
    }

}