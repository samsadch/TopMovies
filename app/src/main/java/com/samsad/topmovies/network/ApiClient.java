package com.samsad.topmovies.network;

import com.samsad.topmovies.models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    /*@GET("Mob_home/getCityAreas")
    Call<JsonObject> getInitialData();

    @GET("Mob_home/offers_and_deals")
    Call<JsonObject> getOffersAndDeals();

    @GET("Mob_home/get_slider")
    Call<JsonObject> getOfferSlider();*/

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
