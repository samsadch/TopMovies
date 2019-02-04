package com.samsad.topmovies.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samsad.topmovies.R;
import com.samsad.topmovies.adapter.MoviesAdapter;
import com.samsad.topmovies.models.Movie;
import com.samsad.topmovies.models.MovieResponse;
import com.samsad.topmovies.databinding.ActivityMainBinding;
import com.samsad.topmovies.network.ApiClient;
import com.samsad.topmovies.service.MovieService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.samsad.topmovies.activity.BaseActivity.BASE_URL;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private ArrayList<Movie> movieList = new ArrayList<>();

    public static final String MOVIE_LIST = "movie.list";
    public static final String SERVICE_RECEIVER ="com.samsad.service.receiver";

    private MovieResponse movieResponse;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        context = MainActivity.this;
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main);
        startMovieService();
        setLoading(getString(R.string.loading_message));
        showProgress();
    }

    private void initRecyclerView() {
        try {
            if(isValid(movieList)) {
                recyclerView = binding.movieRcv;
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setNestedScrollingEnabled(false);
                adapter = new MoviesAdapter(movieList, this);
                recyclerView.setAdapter(adapter);
            }
        }
        catch(Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hideProgress();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                movieList = bundle.getParcelableArrayList(MOVIE_LIST);
                int k = movieList.size();
                if ( k > 15 )
                    movieList.subList(15, k).clear();
                initRecyclerView();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SERVICE_RECEIVER));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void startMovieService() {
        try{
            Intent intent = new Intent(this, MovieService.class);
            startService(intent);
        }catch (Exception e){
            hideProgress();
            e.printStackTrace();
        }
    }
}
