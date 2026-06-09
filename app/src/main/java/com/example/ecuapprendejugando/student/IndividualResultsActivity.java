package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.db.repository.ProgressRepository;
import com.example.ecuapprendejugando.util.HapticUtil;
import com.example.ecuapprendejugando.util.PointsCalculator;

public class IndividualResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_results);

        int userId       = getIntent().getIntExtra("user_id", -1);
        int topicId      = getIntent().getIntExtra("topic_id", -1);
        int pointsEarned = getIntent().getIntExtra("points_earned", 0);
        boolean fromRoom = getIntent().getBooleanExtra("from_room", false);

        TextView tvTitle   = findViewById(R.id.tv_result_title);
        TextView tvPoints  = findViewById(R.id.tv_result_points);
        TextView tvCorrect = findViewById(R.id.tv_result_correct);
        TextView tvLevel   = findViewById(R.id.tv_result_level);
        Button   btnContinue = findViewById(R.id.btn_continue);
        Button   btnHome     = findViewById(R.id.btn_home);

        ProgressRepository repo = new ProgressRepository(this);

        if (fromRoom) {
            tvTitle.setText("¡Ronda terminada!");
            tvPoints.setText("+" + pointsEarned + " pts en esta ronda");
            tvCorrect.setText(pointsEarned > 0 ? "¡Respondiste correctamente!" : "Sigue practicando");
            tvLevel.setVisibility(android.view.View.GONE);
            btnContinue.setVisibility(android.view.View.GONE);
        } else {
            repo.getTotalPoints(userId, total -> {
                int level = PointsCalculator.levelFromPoints(total);
                int toNext = PointsCalculator.pointsToNextLevel(total);
                runOnUiThread(() -> {
                    tvTitle.setText("¡Ejercicios completados!");
                    tvPoints.setText(total + " pts totales");
                    tvLevel.setText("Nivel " + level + " — faltan " + toNext + " pts para el siguiente");
                });
            });
            repo.getCorrectCountByTopic(userId, topicId, count ->
                runOnUiThread(() -> tvCorrect.setText(count + " respuestas correctas en este tema")));
        }

        HapticUtil.achievement(this);

        btnContinue.setOnClickListener(v -> {
            HapticUtil.tap(this);
            Intent i = new Intent(this, TopicsActivity.class);
            i.putExtra("user_id", userId);
            startActivity(i);
            finish();
        });

        btnHome.setOnClickListener(v -> {
            HapticUtil.tap(this);
            Intent i = new Intent(this, StudentDashboardActivity.class);
            i.putExtra("user_id", userId);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        });
    }
}
