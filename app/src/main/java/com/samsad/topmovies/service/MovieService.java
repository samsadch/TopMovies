package com.samsad.topmovies.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samsad.topmovies.R;
import com.samsad.topmovies.models.MovieResponse;
import com.samsad.topmovies.network.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.samsad.topmovies.activity.BaseActivity.BASE_URL;
import static com.samsad.topmovies.activity.MainActivity.MOVIE_LIST;
import static com.samsad.topmovies.activity.MainActivity.SERVICE_RECEIVER;

public class MovieService extends IntentService {

    private MovieResponse movieResponse;

    public MovieService() {
        super("MovieService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getTopRatedMovies();
    }

    private void getTopRatedMovies() {
        try{
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            ApiClient foodzerApi = retrofit.create(ApiClient.class);

            Call<MovieResponse> call = foodzerApi.getTopRatedMovies(getString(R.string.api_key));
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    try {
                        String message = response.body().toString();
                        movieResponse = response.body();
                        publishResults();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void publishResults() {
        Intent intent = new Intent(SERVICE_RECEIVER);
        intent.putParcelableArrayListExtra(MOVIE_LIST, movieResponse.getMovieList());
        sendBroadcast(intent);
    }


}
