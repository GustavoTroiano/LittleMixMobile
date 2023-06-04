package com.example.littlemixmobile.api;

import com.example.littlemixmobile.model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPservices {

    @GET("{cep}/json/")
    Call<Endereco> recuperaCEP(@Path("cep") String cep);
}
