package com.example.ecuapprendejugando;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.db.entity.UserEntity;
import com.example.ecuapprendejugando.db.repository.UserRepository;

public class OnboardingActivity extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputEditText etName;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        SharedPreferences prefs = getSharedPreferences("ecuaprende", MODE_PRIVATE);
        int savedUserId = prefs.getInt("user_id", -1);
        if (savedUserId != -1) {
            goToRoleSelector(savedUserId);
            return;
        }

        userRepository = new UserRepository(this);
        etName = findViewById(R.id.et_name);
        Button btnContinue = findViewById(R.id.btn_continue);

        btnContinue.setOnClickListener(v -> {
            String name = etName.getText() != null ? etName.getText().toString().trim() : "";
            if (name.isEmpty()) {
                Toast.makeText(this, "Ingresa tu nombre", Toast.LENGTH_SHORT).show();
                return;
            }
            UserEntity user = new UserEntity(name, "student");
            userRepository.insert(user, id -> {
                prefs.edit().putInt("user_id", id).apply();
                goToRoleSelector(id);
            });
        });
    }

    private void goToRoleSelector(int userId) {
        Intent intent = new Intent(this, RoleSelectorActivity.class);
        intent.putExtra("user_id", userId);
        runOnUiThread(() -> {
            startActivity(intent);
            finish();
        });
    }
}
