package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.db.entity.ExerciseEntity;
import com.example.ecuapprendejugando.db.repository.ExerciseRepository;
import com.example.ecuapprendejugando.db.repository.ProgressRepository;
import com.example.ecuapprendejugando.db.entity.ProgressEntity;
import com.example.ecuapprendejugando.util.HapticUtil;
import com.example.ecuapprendejugando.util.PointsCalculator;

public class CollaborativeGameActivity extends AppCompatActivity {

    private int userId, exerciseId;
    private String roomCode;
    private ExerciseEntity currentExercise;
    private CountDownTimer timer;
    private TextView tvQuestion, tvTimer, tvFeedback;
    private Button btnA, btnB, btnC, btnD;
    private ProgressBar pbTimer;
    private boolean answered = false;
    private static final int TIME_SECONDS = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaborative_game);

        userId     = getIntent().getIntExtra("user_id", -1);
        roomCode   = getIntent().getStringExtra("room_code");
        exerciseId = getIntent().getIntExtra("exercise_id", -1);

        tvQuestion = findViewById(R.id.tv_question);
        tvTimer    = findViewById(R.id.tv_timer);
        tvFeedback = findViewById(R.id.tv_feedback);
        pbTimer    = findViewById(R.id.pb_timer);
        btnA = findViewById(R.id.btn_option_a);
        btnB = findViewById(R.id.btn_option_b);
        btnC = findViewById(R.id.btn_option_c);
        btnD = findViewById(R.id.btn_option_d);

        ExerciseRepository repo = new ExerciseRepository(this);
        repo.getByIdSync(exerciseId, ex -> {
            if (ex == null) return;
            currentExercise = ex;
            runOnUiThread(() -> {
                tvQuestion.setText(ex.question);
                btnA.setText(ex.optionA);
                btnB.setText(ex.optionB);
                btnC.setText(ex.optionC);
                btnD.setText(ex.optionD);
                startTimer();
            });
        });

        btnA.setOnClickListener(v -> submitAnswer(btnA.getText().toString()));
        btnB.setOnClickListener(v -> submitAnswer(btnB.getText().toString()));
        btnC.setOnClickListener(v -> submitAnswer(btnC.getText().toString()));
        btnD.setOnClickListener(v -> submitAnswer(btnD.getText().toString()));
    }

    private void startTimer() {
        pbTimer.setMax(TIME_SECONDS);
        pbTimer.setProgress(TIME_SECONDS);
        timer = new CountDownTimer(TIME_SECONDS * 1000L, 1000) {
            public void onTick(long ms) {
                int sec = (int)(ms / 1000);
                tvTimer.setText(sec + "s");
                pbTimer.setProgress(sec);
            }
            public void onFinish() {
                if (!answered) {
                    tvFeedback.setText("¡Tiempo agotado!");
                    tvFeedback.setTextColor(getColor(R.color.warning));
                    setButtonsEnabled(false);
                    goToResults(0);
                }
            }
        }.start();
    }

    private void submitAnswer(String answer) {
        if (answered || currentExercise == null) return;
        answered = true;
        timer.cancel();
        setButtonsEnabled(false);

        boolean correct = currentExercise.correctAnswer.trim().equalsIgnoreCase(answer.trim());
        int pts = PointsCalculator.calculate(correct, 1, 0);

        ProgressRepository progressRepo = new ProgressRepository(this);
        progressRepo.insert(new ProgressEntity(userId, currentExercise.topicId, currentExercise.id, correct, Math.max(pts, 0)));

        if (correct) {
            HapticUtil.correct(this);
            tvFeedback.setText("¡Correcto! +" + pts + " pts");
            tvFeedback.setTextColor(getColor(R.color.success));
        } else {
            HapticUtil.wrong(this);
            tvFeedback.setText("Incorrecto. La respuesta era: " + currentExercise.correctAnswer);
            tvFeedback.setTextColor(getColor(R.color.error));
        }

        goToResults(pts);
    }

    private void goToResults(int pts) {
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            Intent i = new Intent(this, IndividualResultsActivity.class);
            i.putExtra("user_id", userId);
            i.putExtra("points_earned", pts);
            i.putExtra("from_room", true);
            startActivity(i);
            finish();
        }, 2000);
    }

    private void setButtonsEnabled(boolean enabled) {
        btnA.setEnabled(enabled);
        btnB.setEnabled(enabled);
        btnC.setEnabled(enabled);
        btnD.setEnabled(enabled);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}
