package com.example.ecuapprendejugando.student;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.db.repository.ProgressRepository;
import com.example.ecuapprendejugando.db.repository.UserRepository;
import com.example.ecuapprendejugando.util.PointsCalculator;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int userId = getIntent().getIntExtra("user_id", -1);

        TextView tvName    = findViewById(R.id.tv_profile_name);
        TextView tvLevel   = findViewById(R.id.tv_profile_level);
        TextView tvPoints  = findViewById(R.id.tv_profile_points);
        TextView tvStreak  = findViewById(R.id.tv_profile_streak);
        TextView tvCorrect = findViewById(R.id.tv_profile_correct);

        UserRepository userRepo = new UserRepository(this);
        userRepo.getByIdSync(userId, user -> {
            if (user == null) return;
            runOnUiThread(() -> {
                tvName.setText(user.name);
                tvLevel.setText("Nivel " + user.currentLevel);
                tvPoints.setText(user.totalPoints + " pts");
                tvStreak.setText("Racha: " + user.streak);
            });
        });

        ProgressRepository progressRepo = new ProgressRepository(this);
        progressRepo.getCorrectCount(userId, count ->
            runOnUiThread(() -> tvCorrect.setText(count + " correctas en total")));

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
