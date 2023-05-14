package com.example.littlemixmobile.fragment.loja;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.activity.loja.LojaFormProdutoActivity;
import com.example.littlemixmobile.databinding.FragmentLojaProdutoBinding;

public class LojaProdutoFragment extends Fragment {

    private FragmentLojaProdutoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLojaProdutoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configClicks();
    }

    private void configClicks() {
        binding.toolbar.btnAdd.setOnClickListener(
                v -> startActivity(new Intent(requireContext(), LojaFormProdutoActivity.class))
        );
    }
}