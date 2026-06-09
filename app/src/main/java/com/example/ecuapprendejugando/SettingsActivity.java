package com.example.ecuapprendejugando;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("ecuaprende_settings", MODE_PRIVATE);

        Switch switchHaptic = findViewById(R.id.switch_haptic);
        Switch switchSound = findViewById(R.id.switch_sound);

        switchHaptic.setChecked(prefs.getBoolean("haptic", true));
        switchSound.setChecked(prefs.getBoolean("sound", true));

        switchHaptic.setOnCheckedChangeListener((btn, checked) ->
                prefs.edit().putBoolean("haptic", checked).apply());

        switchSound.setOnCheckedChangeListener((btn, checked) ->
                prefs.edit().putBoolean("sound", checked).apply());

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
