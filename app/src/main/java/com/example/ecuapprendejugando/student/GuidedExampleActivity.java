package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.util.HapticUtil;

public class GuidedExampleActivity extends AppCompatActivity {

    private int currentStep = 0;
    private int topicId;
    private int userId;
    private String[][] steps;
    private TextView tvStep, tvExplanation;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_example);

        topicId = getIntent().getIntExtra("topic_id", 1);
        userId  = getIntent().getIntExtra("user_id", -1);
        steps   = getSteps(topicId);

        tvStep        = findViewById(R.id.tv_step);
        tvExplanation = findViewById(R.id.tv_explanation);
        btnNext       = findViewById(R.id.btn_next_step);

        showStep(0);

        btnNext.setOnClickListener(v -> {
            HapticUtil.tap(this);
            currentStep++;
            if (currentStep < steps.length) {
                showStep(currentStep);
            } else {
                Intent i = new Intent(this, ExerciseActivity.class);
                i.putExtra("topic_id", topicId);
                i.putExtra("user_id", userId);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void showStep(int index) {
        tvStep.setText(steps[index][0]);
        tvExplanation.setText(steps[index][1]);
        btnNext.setText(index == steps.length - 1 ? "Practicar ahora" : "Siguiente paso");
    }

    private String[][] getSteps(int topicId) {
        switch (topicId) {
            case 1: return new String[][]{
                {"Paso 1: Planteamos la ecuación", "Tenemos: 3x + 6 = 18\nIdentificamos: coeficiente=3, constante=6, resultado=18"},
                {"Paso 2: Despejamos la constante", "Restamos 6 a ambos lados:\n3x + 6 - 6 = 18 - 6\n3x = 12"},
                {"Paso 3: Despejamos la incógnita", "Dividimos entre 3:\n3x ÷ 3 = 12 ÷ 3\nx = 4"},
                {"Paso 4: Verificamos", "Sustituimos x = 4:\n3(4) + 6 = 12 + 6 = 18 ✓"}
            };
            case 2: return new String[][]{
                {"Paso 1: Identificamos el paréntesis", "Tenemos: 3(x + 2) = 15"},
                {"Paso 2: Aplicamos distributiva", "3·x + 3·2 = 15\n3x + 6 = 15"},
                {"Paso 3: Despejamos", "3x = 15 - 6 = 9\nx = 9 ÷ 3 = 3"},
                {"Paso 4: Verificamos", "3(3 + 2) = 3(5) = 15 ✓"}
            };
            case 3: return new String[][]{
                {"Paso 1: Identificamos la fracción", "Tenemos: x/4 + 2 = 5"},
                {"Paso 2: Despejamos la fracción", "x/4 = 5 - 2 = 3"},
                {"Paso 3: Eliminamos el denominador", "x = 3 × 4 = 12"},
                {"Paso 4: Verificamos", "12/4 + 2 = 3 + 2 = 5 ✓"}
            };
            default: return new String[][]{
                {"Paso 1: Sistema de ecuaciones", "x + y = 8\nx - y = 2"},
                {"Paso 2: Sumamos las ecuaciones", "(x+y) + (x-y) = 8+2\n2x = 10"},
                {"Paso 3: Resolvemos x", "x = 10 ÷ 2 = 5"},
                {"Paso 4: Encontramos y", "5 + y = 8 → y = 3\nVerificamos: 5-3 = 2 ✓"}
            };
        }
    }
}
