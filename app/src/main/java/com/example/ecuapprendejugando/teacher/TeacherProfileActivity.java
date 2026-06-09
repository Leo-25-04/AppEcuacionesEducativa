package com.example.ecuapprendejugando.teacher;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.db.repository.UserRepository;

public class TeacherProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int userId = getIntent().getIntExtra("user_id", -1);

        TextView tvName   = findViewById(R.id.tv_profile_name);
        TextView tvLevel  = findViewById(R.id.tv_profile_level);
        TextView tvPoints = findViewById(R.id.tv_profile_points);
        TextView tvStreak = findViewById(R.id.tv_profile_streak);

        UserRepository repo = new UserRepository(this);
        repo.getByIdSync(userId, user -> {
            if (user == null) return;
            runOnUiThread(() -> {
                tvName.setText("Prof. " + user.name);
                tvLevel.setText("Rol: Profesor");
                tvPoints.setText("—");
                tvStreak.setText("—");
            });
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
