package com.example.littlemixmobile.activity.loja;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.littlemixmobile.databinding.ActivityLojaRecebimentosBinding;
import com.example.littlemixmobile.helper.FirebaseHelper;
import com.example.littlemixmobile.model.Loja;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class LojaRecebimentosActivity extends AppCompatActivity {
    
    private Loja loja;
    private ActivityLojaRecebimentosBinding binding;
    
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLojaRecebimentosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();

        recuperaLoja();
    }
    
    
    
    private void configClicks(){
        binding.include.textTitulo.setText("Recebimentos");
        binding.include.include.ibVoltar.setOnClickListener(v -> finish());
        
        
        binding.btnSalvar.setOnClickListener(v -> {
            if (loja != null){
                validaDados();
            }else{
                Toast.makeText(this, "Ainda estamos recuperando as informações da loja, aguarde...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recuperaLoja(){
        DatabaseReference lojaRef = FirebaseHelper.getDatabaseReference().child("loja");
        lojaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loja = snapshot.getValue(Loja.class);

                configDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configDados() {
        if (loja.getPublicKey() !=null){
            binding.edtPublicKey.setText(loja.getPublicKey());
        }
        if (loja.getAccessToken() !=null){
            binding.edtAcessToken.setText(loja.getAccessToken());
        }
        if (loja.getParcelas() != 0){
            binding.edtParcelas.setText(String.valueOf(loja.getParcelas()));
        }
    }


    private void validaDados() {
        String publicKey = binding.edtPublicKey.getText().toString().trim();
        String acessToken = binding.edtAcessToken.getText().toString().trim();
        String parcelasStr = binding.edtParcelas.getText().toString().trim();
        int parcelas = 0;
        if (!parcelasStr.isEmpty()){
            parcelas = Integer.parseInt(binding.edtParcelas.getText().toString().trim());
        }

        if (!publicKey.isEmpty()){
            if (!acessToken.isEmpty()){
                if (parcelas > 0 && parcelas <= 12){

                    ocultaTeclado();

                    loja.setPublicKey(publicKey);
                    loja.setAccessToken(acessToken);
                    loja.setParcelas(parcelas);
                    loja.salvar();
                    
                }else {
                    binding.edtParcelas.setError("Mínimo 1 e máximo 12.");
                    binding.edtParcelas.requestFocus();
                }
            }else {
                binding.edtAcessToken.setError("Informe seu Acess Token");
                binding.edtAcessToken.requestFocus();
            }
        }else {
            binding.edtPublicKey.setError("Informe sua public key");
            binding.edtPublicKey.requestFocus();
        }
    }
    private void ocultaTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtPublicKey.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
