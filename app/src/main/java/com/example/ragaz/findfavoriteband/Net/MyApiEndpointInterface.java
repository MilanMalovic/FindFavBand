package com.example.ragaz.findfavoriteband.Net;

import com.example.ragaz.findfavoriteband.Net.Model.Event;
import com.example.ragaz.findfavoriteband.Net.Model.Example;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


/**
        * Klasa koja opisuje koji tj mapira putanju servisa
        * opisuje koji metod koristimo ali i sta ocekujemo kao rezultat
        *
        * */


public interface MyApiEndpointInterface {


    @GET("artists/{artist}/events")
    Call<List<Example>> getArtistByName(@Path("artist") String artist, @QueryMap Map<String, String> options);


}
