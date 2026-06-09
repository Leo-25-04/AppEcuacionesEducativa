package com.example.ecuapprendejugando;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecuapprendejugando.student.StudentDashboardActivity;
import com.example.ecuapprendejugando.teacher.TeacherDashboardActivity;
import com.example.ecuapprendejugando.util.HapticUtil;

public class RoleSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selector);

        int userId = getIntent().getIntExtra("user_id", -1);

        Button btnStudent = findViewById(R.id.btn_student);
        Button btnTeacher = findViewById(R.id.btn_teacher);

        btnStudent.setOnClickListener(v -> {
            HapticUtil.tap(this);
            Intent intent = new Intent(this, StudentDashboardActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        btnTeacher.setOnClickListener(v -> {
            HapticUtil.tap(this);
            Intent intent = new Intent(this, TeacherDashboardActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
    }
}
