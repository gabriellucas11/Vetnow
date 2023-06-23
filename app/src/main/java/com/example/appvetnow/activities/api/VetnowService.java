package com.example.appvetnow.activities.api;

import java.util.List;

import com.example.appvetnow.activities.entidades.Vetnow;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VetnowService {


        @Headers({
                "Accept: application/json",
                "User-Agent: AppVetnow"
        })
        @GET("vetnow")
        Call<List<Vetnow>> getVetnows();

        @GET("vetnow/{id}")
        Call<Vetnow> getVetnow(@Path("id") String codigo);

        @POST("vetnow")
        Call<Vetnow> criavetnow(@Body Vetnow vetnow);

        @PUT("vetnow/{id}")
        Call<Vetnow> atualizaVetnow(@Path("id") String codigo, @Body Vetnow vetnow);

        @DELETE("vetnow/{id}")
        Call<Boolean> excluiVetnow(@Path("id") String codigo);


}
