package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.db.repository.RoomRepository;
import com.example.ecuapprendejugando.util.HapticUtil;

public class WaitingRoomActivity extends AppCompatActivity {

    private String roomCode;
    private int userId;
    private Handler handler;
    private Runnable pollRunnable;
    private RoomRepository roomRepo;
    private boolean activityStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);

        roomCode = getIntent().getStringExtra("room_code");
        userId   = getIntent().getIntExtra("user_id", -1);
        roomRepo = new RoomRepository(this);
        handler  = new Handler(Looper.getMainLooper());

        TextView tvCode = findViewById(R.id.tv_room_code);
        tvCode.setText("Sala: " + roomCode);

        startPolling();
    }

    private void startPolling() {
        pollRunnable = () -> {
            roomRepo.getByCode(roomCode, room -> {
                if (room != null && "active".equals(room.status) && !activityStarted) {
                    activityStarted = true;
                    HapticUtil.achievement(this);
                    runOnUiThread(() -> {
                        Intent i = new Intent(this, CollaborativeGameActivity.class);
                        i.putExtra("room_code", roomCode);
                        i.putExtra("user_id", userId);
                        i.putExtra("exercise_id", room.currentExerciseId);
                        startActivity(i);
                        finish();
                    });
                } else if (!activityStarted) {
                    handler.postDelayed(pollRunnable, 3000);
                }
            });
        };
        handler.postDelayed(pollRunnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(pollRunnable);
    }
}
