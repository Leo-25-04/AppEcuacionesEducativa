package com.example.ecuapprendejugando.teacher;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.R;

public class StartActivityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Redirects to RoomAdminActivity — kept for Manifest compatibility
        String roomCode = getIntent().getStringExtra("room_code");
        int userId = getIntent().getIntExtra("user_id", -1);
        Intent i = new Intent(this, RoomAdminActivity.class);
        i.putExtra("room_code", roomCode);
        i.putExtra("user_id", userId);
        startActivity(i);
        finish();
    }
}
