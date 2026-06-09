package com.example.ecuapprendejugando;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.util.DataSeeder;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DataSeeder.seedIfEmpty(this, () ->
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                startActivity(new Intent(this, OnboardingActivity.class));
                finish();
            }, SPLASH_DELAY)
        );
    }
}
