package com.example.myapplication.service;

import com.example.myapplication.entidade.PokeResposta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon")
    Call<PokeResposta> recuperarListaPoke(@Query("limit") int limit, @Query("offset") int offset);

}
