package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.util.HapticUtil;

public class TheoryContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_content);

        int topicId = getIntent().getIntExtra("topic_id", 1);
        int userId  = getIntent().getIntExtra("user_id", -1);
        String title = getIntent().getStringExtra("topic_title");

        TextView tvTitle   = findViewById(R.id.tv_theory_title);
        TextView tvContent = findViewById(R.id.tv_theory_content);

        tvTitle.setText(title);
        tvContent.setText(getTheoryContent(topicId));

        findViewById(R.id.btn_examples).setOnClickListener(v -> {
            HapticUtil.tap(this);
            Intent i = new Intent(this, GuidedExampleActivity.class);
            i.putExtra("topic_id", topicId);
            i.putExtra("user_id", userId);
            startActivity(i);
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private String getTheoryContent(int topicId) {
        switch (topicId) {
            case 1: return "Una ecuación de primer grado es una igualdad que contiene una incógnita (x) con exponente 1.\n\n" +
                           "Forma general: ax + b = c\n\n" +
                           "Para resolverla:\n" +
                           "1. Pasa los números al lado derecho\n" +
                           "2. Despeja la x dividiendo entre su coeficiente\n\n" +
                           "Ejemplo: 2x + 4 = 10\n" +
                           "→ 2x = 10 - 4\n" +
                           "→ 2x = 6\n" +
                           "→ x = 3";
            case 2: return "Las ecuaciones con paréntesis requieren aplicar la propiedad distributiva.\n\n" +
                           "Pasos:\n" +
                           "1. Distribuye el factor fuera del paréntesis\n" +
                           "2. Simplifica términos semejantes\n" +
                           "3. Despeja la incógnita\n\n" +
                           "Ejemplo: 2(x + 3) = 14\n" +
                           "→ 2x + 6 = 14\n" +
                           "→ 2x = 8\n" +
                           "→ x = 4";
            case 3: return "Las ecuaciones con fracciones se resuelven eliminando los denominadores.\n\n" +
                           "Pasos:\n" +
                           "1. Identifica el mínimo común múltiplo (MCM) de los denominadores\n" +
                           "2. Multiplica toda la ecuación por el MCM\n" +
                           "3. Resuelve la ecuación resultante\n\n" +
                           "Ejemplo: x/2 + 3 = 7\n" +
                           "→ x/2 = 4\n" +
                           "→ x = 8";
            case 4: return "Un sistema de ecuaciones tiene dos o más ecuaciones con las mismas incógnitas.\n\n" +
                           "Método de suma/resta:\n" +
                           "1. Suma o resta las ecuaciones para eliminar una incógnita\n" +
                           "2. Resuelve la ecuación resultante\n" +
                           "3. Sustituye para encontrar la otra incógnita\n\n" +
                           "Ejemplo:\n" +
                           "x + y = 10\n" +
                           "x - y = 4\n" +
                           "→ Sumando: 2x = 14 → x = 7\n" +
                           "→ Sustituyendo: y = 3";
            default: return "Contenido no disponible.";
        }
    }
}
