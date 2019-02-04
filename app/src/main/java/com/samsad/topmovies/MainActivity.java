package com.samsad.topmovies;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.samsad.topmovies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        user = new User();
        user.setName("Ravi Tamada");
        user.setEmail("ravi@androidhive.info");

        binding.setUser(user);
    }
}
