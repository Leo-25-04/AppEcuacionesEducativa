package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.util.HapticUtil;
import com.example.ecuapprendejugando.util.RoomCodeGenerator;
import com.example.ecuapprendejugando.viewmodel.RoomViewModel;

public class EnterRoomCodeActivity extends AppCompatActivity {

    private RoomViewModel roomVM;
    private EditText etCode;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_room_code);

        userId = getIntent().getIntExtra("user_id", -1);
        roomVM = new ViewModelProvider(this).get(RoomViewModel.class);
        etCode = findViewById(R.id.et_room_code);
        Button btnJoin = findViewById(R.id.btn_join);

        roomVM.getJoinedRoom().observe(this, room -> {
            if (room != null) {
                HapticUtil.tap(this);
                Intent i = new Intent(this, WaitingRoomActivity.class);
                i.putExtra("room_code", room.code);
                i.putExtra("user_id", userId);
                startActivity(i);
                finish();
            }
        });

        roomVM.getErrorMessage().observe(this, msg -> {
            if (msg != null) {
                HapticUtil.wrong(this);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnJoin.setOnClickListener(v -> {
            String code = etCode.getText().toString().trim().toUpperCase();
            if (!RoomCodeGenerator.isValid(code)) {
                Toast.makeText(this, "Código inválido (6 caracteres)", Toast.LENGTH_SHORT).show();
                return;
            }
            roomVM.joinRoom(code);
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
