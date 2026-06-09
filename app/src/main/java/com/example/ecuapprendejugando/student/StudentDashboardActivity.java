package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.CreditsActivity;
import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.SettingsActivity;
import com.example.ecuapprendejugando.db.repository.UserRepository;
import com.example.ecuapprendejugando.util.HapticUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentDashboardActivity extends AppCompatActivity {

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        userId = getIntent().getIntExtra("user_id", -1);

        UserRepository repo = new UserRepository(this);
        repo.getByIdSync(userId, user -> {
            if (user != null) {
                runOnUiThread(() -> {
                    TextView tvName   = findViewById(R.id.tv_username);
                    TextView tvPoints = findViewById(R.id.tv_points);
                    tvName.setText("Hola, " + user.name);
                    tvPoints.setText(String.valueOf(user.totalPoints));
                });
            }
        });

        findViewById(R.id.btn_topics).setOnClickListener(v -> {
            HapticUtil.tap(this);
            Intent i = new Intent(this, TopicsActivity.class);
            i.putExtra("user_id", userId);
            startActivity(i);
        });

        findViewById(R.id.btn_join_room).setOnClickListener(v -> {
            HapticUtil.tap(this);
            Intent i = new Intent(this, EnterRoomCodeActivity.class);
            i.putExtra("user_id", userId);
            startActivity(i);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_topics) {
                HapticUtil.tap(this);
                startActivity(new Intent(this, TopicsActivity.class).putExtra("user_id", userId));
                return true;
            } else if (id == R.id.nav_progress) {
                HapticUtil.tap(this);
                startActivity(new Intent(this, ProgressActivity.class).putExtra("user_id", userId));
                return true;
            } else if (id == R.id.nav_profile) {
                HapticUtil.tap(this);
                startActivity(new Intent(this, ProfileActivity.class).putExtra("user_id", userId));
                return true;
            } else if (id == R.id.nav_credits) {
                HapticUtil.tap(this);
                startActivity(new Intent(this, CreditsActivity.class));
                return true;
            }
            return true;
        });
    }
}
