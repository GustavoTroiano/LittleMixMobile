package com.example.littlemixmobile.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.databinding.ActivityCadastroBinding;
import com.example.littlemixmobile.helper.FirebaseHelper;
import com.example.littlemixmobile.model.Loja;
import com.example.littlemixmobile.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();

    }

    public void validaDados(View view){
        String nome = binding.edtNome.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();
        String confirmaSenha = binding.edtConfimaSenha.getText().toString().trim();

        if(!nome.isEmpty()){
            if(!email.isEmpty()){
                if(!senha.isEmpty()){
                    if(!confirmaSenha.isEmpty()){

                        if(senha.equals(confirmaSenha)){

                            binding.progressBar.setVisibility(View.VISIBLE);

                            Usuario usuario = new Usuario();
                            usuario.setNome(nome);
                            usuario.setEmail(email);
                            usuario.setSenha(senha);

                            criarConta(usuario);

                        }else {
                            binding.edtConfimaSenha.requestFocus();
                            binding.edtConfimaSenha.setError("Senha não confere");
                        }

                    }else  {
                        binding.edtConfimaSenha.requestFocus();
                        binding.edtConfimaSenha.setError("Confirme a sua senha");
                    }
                }else  {
                    binding.edtSenha.requestFocus();
                    binding.edtSenha.setError("Informe uma senha");
                }
            }else  {
                binding.edtEmail.requestFocus();
                binding.edtEmail.setError("Informe seu e-mail");
            }
        }else  {
            binding.edtNome.requestFocus();
            binding.edtNome.setError("Informe seu nome");
        }

    }

    private void criarConta(Usuario usuario){
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                String id = task.getResult().getUser().getUid();

                usuario.setId(id);
                usuario.salvar();

                Intent intent = new Intent();
                intent.putExtra("email", usuario.getEmail());
                setResult(RESULT_OK, intent);
                finish();

            }else {
                Toast.makeText(this, FirebaseHelper.validaErros(task.getException().getMessage()), Toast.LENGTH_SHORT).show();
            }

            binding.progressBar.setVisibility(View.GONE);

        });
    }

    private void configClicks(){
        binding.include.ibVoltar.setOnClickListener(view -> finish());
        binding.btnLogin.setOnClickListener(view -> finish());
    }

}