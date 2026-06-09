package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.util.HapticUtil;
import com.example.ecuapprendejugando.util.PointsCalculator;
import com.example.ecuapprendejugando.viewmodel.ExerciseViewModel;
import com.example.ecuapprendejugando.viewmodel.StudentViewModel;

public class ExerciseActivity extends AppCompatActivity {

    private ExerciseViewModel exerciseVM;
    private StudentViewModel studentVM;
    private int userId;
    private int topicId;
    private TextView tvQuestion, tvFeedback, tvPoints, tvProgress;
    private Button btnA, btnB, btnC, btnD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        userId  = getIntent().getIntExtra("user_id", -1);
        topicId = getIntent().getIntExtra("topic_id", 1);

        exerciseVM = new ViewModelProvider(this).get(ExerciseViewModel.class);
        studentVM  = new ViewModelProvider(this).get(StudentViewModel.class);

        tvQuestion = findViewById(R.id.tv_question);
        tvFeedback = findViewById(R.id.tv_feedback);
        tvPoints   = findViewById(R.id.tv_points_earned);
        tvProgress = findViewById(R.id.tv_progress);
        btnA = findViewById(R.id.btn_option_a);
        btnB = findViewById(R.id.btn_option_b);
        btnC = findViewById(R.id.btn_option_c);
        btnD = findViewById(R.id.btn_option_d);

        exerciseVM.loadExercises(topicId);

        exerciseVM.getCurrentExercise().observe(this, ex -> {
            if (ex == null) return;
            tvQuestion.setText(ex.question);
            btnA.setText(ex.optionA);
            btnB.setText(ex.optionB);
            btnC.setText(ex.optionC);
            btnD.setText(ex.optionD);
            tvFeedback.setText("");
            tvPoints.setText("");
            tvProgress.setText((exerciseVM.getCurrentIndex() + 1) + "/" + exerciseVM.getTotalExercises());
            enableButtons(true);
        });

        exerciseVM.getAnswerResult().observe(this, correct -> {
            if (correct == null) return;
            if (correct) {
                HapticUtil.correct(this);
                tvFeedback.setText("¡Correcto! " + exerciseVM.getCurrentExercise().getValue().explanation);
                tvFeedback.setTextColor(getColor(R.color.success));
            } else {
                HapticUtil.wrong(this);
                tvFeedback.setText("Incorrecto. Intenta de nuevo.");
                tvFeedback.setTextColor(getColor(R.color.error));
            }
        });

        exerciseVM.getPointsEarned().observe(this, pts -> {
            if (pts == null) return;
            tvPoints.setText(PointsCalculator.formatPoints(pts) + " pts");
            if (pts > 0) studentVM.addPoints(userId, pts);
        });

        btnA.setOnClickListener(v -> submitAnswer(btnA.getText().toString()));
        btnB.setOnClickListener(v -> submitAnswer(btnB.getText().toString()));
        btnC.setOnClickListener(v -> submitAnswer(btnC.getText().toString()));
        btnD.setOnClickListener(v -> submitAnswer(btnD.getText().toString()));

        findViewById(R.id.btn_next).setOnClickListener(v -> {
            HapticUtil.tap(this);
            boolean hasMore = exerciseVM.moveToNext();
            if (!hasMore) {
                Intent i = new Intent(this, IndividualResultsActivity.class);
                i.putExtra("user_id", userId);
                i.putExtra("topic_id", topicId);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void submitAnswer(String answer) {
        enableButtons(false);
        exerciseVM.submitAnswer(answer, userId, topicId);
    }

    private void enableButtons(boolean enabled) {
        btnA.setEnabled(enabled);
        btnB.setEnabled(enabled);
        btnC.setEnabled(enabled);
        btnD.setEnabled(enabled);
    }
}
