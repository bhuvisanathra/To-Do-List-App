package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Handler To Handle The Activity From One Activity To Another
        Handler handle = new Handler();

        //For Redirection To Main Activity
        Runnable run = new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        //Passing The Runnable And Delay Time
        handle.postDelayed(run, 4000);
    }
}