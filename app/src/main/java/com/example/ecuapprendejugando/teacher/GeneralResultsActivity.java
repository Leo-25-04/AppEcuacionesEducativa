package com.example.ecuapprendejugando.teacher;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.db.repository.RoomRepository;
import com.example.ecuapprendejugando.util.HapticUtil;

public class GeneralResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_results);

        String roomCode  = getIntent().getStringExtra("room_code");
        int userId       = getIntent().getIntExtra("user_id", -1);
        int exerciseId   = getIntent().getIntExtra("exercise_id", -1);

        TextView tvCode    = findViewById(R.id.tv_results_room_code);
        TextView tvStatus  = findViewById(R.id.tv_results_status);
        TextView tvSummary = findViewById(R.id.tv_results_summary);
        Button   btnFinish = findViewById(R.id.btn_finish_room);
        Button   btnBack   = findViewById(R.id.btn_back);

        tvCode.setText("Sala: " + roomCode);
        tvStatus.setText("Actividad en curso");
        tvSummary.setText("Ejercicio #" + exerciseId + " enviado a los alumnos.\nEsperando respuestas...");

        HapticUtil.tap(this);

        btnFinish.setOnClickListener(v -> {
            HapticUtil.achievement(this);
            RoomRepository roomRepo = new RoomRepository(this);
            roomRepo.updateStatus(roomCode, "finished");
            tvStatus.setText("Sala finalizada");
            tvSummary.setText("La actividad ha concluido.\nRevisa los resultados individuales de tus alumnos.");
            btnFinish.setEnabled(false);
        });

        btnBack.setOnClickListener(v -> finish());
    }
}
