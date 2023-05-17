package com.example.littlemixmobile.fragment.usuario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.adapter.CategoriaAdapter;
import com.example.littlemixmobile.adapter.LojaProdutoAdapter;
import com.example.littlemixmobile.databinding.FragmentUsuarioHomeBinding;
import com.example.littlemixmobile.helper.FirebaseHelper;
import com.example.littlemixmobile.model.Categoria;
import com.example.littlemixmobile.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UsuarioHomeFragment extends Fragment implements CategoriaAdapter.onClick, LojaProdutoAdapter.OnclickLister {



    private FragmentUsuarioHomeBinding binding;

    private List<Categoria> categoriaList = new ArrayList<>();

    private final List<Produto> produtoList = new ArrayList<>();

    private CategoriaAdapter categoriaAdapter;
    private LojaProdutoAdapter lojaProdutoAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsuarioHomeFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static UsuarioHomeFragment newInstance(String param1, String param2) {
        UsuarioHomeFragment fragment = new UsuarioHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        configRvCategorias();

        configRvProdutos();

        recuperaCategorias();
        recuperaProdutos();


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsuarioHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void configRvCategorias(){
        binding.rvCategorias.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategorias.setHasFixedSize(true);
        categoriaAdapter = new CategoriaAdapter(R.layout.item_categoria_horzontal, true, categoriaList, this);
        binding.rvCategorias.setAdapter(categoriaAdapter);
    }

    private void recuperaCategorias(){
        DatabaseReference categoriaRef = FirebaseHelper.getDatabaseReference()
                .child("categorias");
        categoriaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                categoriaList.clear();

                for (DataSnapshot ds : snapshot.getChildren()){
                    Categoria categoria = ds.getValue(Categoria.class);
                    categoriaList.add(categoria);

                }

                Collections.reverse(categoriaList);
                categoriaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configRvProdutos(){
        binding.rvProdutos.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvProdutos.setHasFixedSize(true);
        lojaProdutoAdapter = new LojaProdutoAdapter(produtoList, requireContext(), this);
        binding.rvProdutos.setAdapter(lojaProdutoAdapter);
    }

    private void recuperaProdutos(){
        DatabaseReference produtoRef = FirebaseHelper.getDatabaseReference()
                .child("produtos");
        produtoRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){

                produtoList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Produto produto = ds.getValue(Produto.class);
                    produtoList.add(produto);
                }

                listEmpty();

                binding.progressBar.setVisibility(View.GONE);
                Collections.reverse(produtoList);
                lojaProdutoAdapter.notifyDataSetChanged();
            }
            @Override
            public void  onCancelled(@NonNull DatabaseError error){

            }

        });
    }

    private void listEmpty(){
        if (produtoList.isEmpty()) {
            binding.textInfo.setText("Nenhum Produto cadastrado");
        }else {
            binding.textInfo.setText("");
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClickListener(Categoria categoria) {
        Toast.makeText(requireContext(), categoria.getNome(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Produto produto) {

    }
}