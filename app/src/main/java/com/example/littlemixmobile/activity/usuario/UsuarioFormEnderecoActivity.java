package com.example.littlemixmobile.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.databinding.ActivityUsuarioFormEnderecoBinding;
import com.example.littlemixmobile.model.Endereco;

public class UsuarioFormEnderecoActivity extends AppCompatActivity {

    private Endereco endereco;

    private ActivityUsuarioFormEnderecoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioFormEnderecoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configClicks();

    }

    private void configClicks(){
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
        binding.include.btnSalvar.setOnClickListener(v -> validaDados());
    }

    private void validaDados(){
        String nomeEndereco = binding.edtNomeEndereco.getText().toString().trim();
        String cep = binding.edtCEP.getText().toString().trim();
        String uf = binding.edtUF.getText().toString().trim();
        String numero = binding.edtNumEndereco.getText().toString().trim();
        String logradouro = binding.edtLogradouro.getText().toString().trim();
        String bairro = binding.edtBairro.getText().toString().trim();
        String municipio = binding.edtMunicipio.getText().toString().trim();

        if (!nomeEndereco.isEmpty()) {
            if (!cep.isEmpty()) {
                if (!uf.isEmpty()) {
                    if (!logradouro.isEmpty()) {
                        if (!bairro.isEmpty()) {
                            if (!municipio.isEmpty()) {

                                ocultaTeclado();

                                binding.progressBar.setVisibility(View.VISIBLE);

                                if (endereco == null) endereco = new Endereco();
                                endereco.setNomeEndereco(nomeEndereco);
                                endereco.setCep(cep);
                                endereco.setUf(uf);
                                endereco.setNumero(numero);
                                endereco.setLogradouro(logradouro);
                                endereco.setBairro(bairro);
                                endereco.setLocalidade(municipio);

                                endereco.salvar();
                                finish();

                            }else {
                                binding.edtMunicipio.requestFocus();
                                binding.edtMunicipio.setError("Informação obrigatória");
                            }

                        }else {
                            binding.edtBairro.requestFocus();
                            binding.edtBairro.setError("Informação obrigatória");
                        }

                    }else {
                        binding.edtLogradouro.requestFocus();
                        binding.edtLogradouro.setError("Informação obrigatória");
                    }

                }else {
                    binding.edtUF.requestFocus();
                    binding.edtUF.setError("Informação obrigatória");
                }

            }else {
                binding.edtCEP.requestFocus();
                binding.edtCEP.setError("Informação obrigatória");
            }

        }else {
            binding.edtNomeEndereco.requestFocus();
            binding.edtNomeEndereco.setError("Informação obrigatória");
        }

    }

    // Oculta o teclado do dispositivo
    private void ocultaTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtNomeEndereco.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void iniciaComponentes(){
        binding.include.textTitulo.setText("Novo endereço");
    }

}