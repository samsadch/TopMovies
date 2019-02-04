package com.samsad.topmovies.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.samsad.topmovies.R;
import com.samsad.topmovies.service.MovieService;

public class SplashScreenActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        startMainActivity();
        //startMovieService();
    }

    private void startMainActivity() {
        try{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                    //intent.putExtra("home_data", details);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }, SPLASH_DISPLAY_LENGTH);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
