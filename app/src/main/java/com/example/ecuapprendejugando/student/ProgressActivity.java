package com.example.ecuapprendejugando.student;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.util.PointsCalculator;
import com.example.ecuapprendejugando.viewmodel.ProgressViewModel;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        int userId = getIntent().getIntExtra("user_id", -1);

        ProgressViewModel vm = new ViewModelProvider(this).get(ProgressViewModel.class);
        vm.loadStats(userId);

        TextView tvPoints  = findViewById(R.id.tv_total_points);
        TextView tvLevel   = findViewById(R.id.tv_current_level);
        TextView tvCorrect = findViewById(R.id.tv_correct_count);
        TextView tvNext    = findViewById(R.id.tv_points_to_next);
        ProgressBar pbLevel = findViewById(R.id.pb_level);

        vm.getTotalPoints().observe(this, pts -> {
            tvPoints.setText(pts + " pts");
            int toNext = PointsCalculator.pointsToNextLevel(pts);
            tvNext.setText("Faltan " + toNext + " pts para el siguiente nivel");
            int[] thresholds = {100, 300, 600, 1000};
            int level = PointsCalculator.levelFromPoints(pts);
            int max = level <= 4 ? thresholds[level - 1] : 1000;
            pbLevel.setMax(max);
            pbLevel.setProgress(Math.min(pts, max));
        });

        vm.getCurrentLevel().observe(this, level ->
            tvLevel.setText("Nivel " + level));

        vm.getCorrectCount().observe(this, count ->
            tvCorrect.setText(count + " respuestas correctas"));

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
