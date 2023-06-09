package com.example.littlemixmobile.autenticacao;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.littlemixmobile.R;
import com.example.littlemixmobile.activity.loja.MainActivityEmpresa;
import com.example.littlemixmobile.activity.usuario.MainActivityUsuario;
import com.example.littlemixmobile.databinding.ActivityLoginBinding;
import com.example.littlemixmobile.helper.FirebaseHelper;
import com.example.littlemixmobile.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    String email = result.getData().getStringExtra("email");
                    binding.edtEmail.setText(email);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    public void validaDados(View view) {
        String email = binding.edtEmail.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                ocultaTeclado();

                binding.progressbar.setVisibility(View.VISIBLE);

                login(email, senha);

            } else {
                binding.edtSenha.requestFocus();
                binding.edtSenha.setError("Informe uma senha.");
            }
        } else {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Informe seu email.");
        }

    }

    private void login(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                recuperaUsuario(task.getResult().getUser().getUid());
            } else {
                binding.progressbar.setVisibility(View.GONE);
                Toast.makeText(this, FirebaseHelper.validaErros(task.getException().getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recuperaUsuario(String id) {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(id);
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){ // Usuário
                    setResult(RESULT_OK);
                }else {
                    startActivity(new Intent(getBaseContext(), MainActivityEmpresa.class));
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configClicks() {
        binding.include.ibVoltar.setOnClickListener(view -> finish());

        binding.btnRecuperaSenha.setOnClickListener(view ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));

        binding.btnCadastro.setOnClickListener(view -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            resultLauncher.launch(intent);
        });
    }

    // Oculta o teclado do dispotivo
    private void ocultaTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtEmail.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}