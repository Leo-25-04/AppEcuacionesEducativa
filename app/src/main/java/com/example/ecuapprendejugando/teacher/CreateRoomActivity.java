package com.example.ecuapprendejugando.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.util.HapticUtil;
import com.example.ecuapprendejugando.viewmodel.TeacherViewModel;

public class CreateRoomActivity extends AppCompatActivity {

    private TeacherViewModel teacherVM;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        userId    = getIntent().getIntExtra("user_id", -1);
        teacherVM = new ViewModelProvider(this).get(TeacherViewModel.class);

        TextView tvCode = findViewById(R.id.tv_generated_code);

        teacherVM.getRoomCode().observe(this, code -> {
            if (code != null) {
                tvCode.setText(code);
                HapticUtil.achievement(this);
            }
        });

        teacherVM.getActiveRoom().observe(this, room -> {
            if (room != null) {
                findViewById(R.id.btn_go_to_room).setEnabled(true);
            }
        });

        findViewById(R.id.btn_generate).setOnClickListener(v -> {
            HapticUtil.tap(this);
            teacherVM.createRoom(userId);
        });

        findViewById(R.id.btn_go_to_room).setOnClickListener(v -> {
            String code = teacherVM.getRoomCode().getValue();
            if (code == null) return;
            HapticUtil.tap(this);
            Intent i = new Intent(this, RoomAdminActivity.class);
            i.putExtra("room_code", code);
            i.putExtra("user_id", userId);
            startActivity(i);
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
