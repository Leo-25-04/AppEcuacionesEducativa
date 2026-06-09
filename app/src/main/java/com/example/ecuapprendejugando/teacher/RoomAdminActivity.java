package com.example.ecuapprendejugando.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.db.entity.ExerciseEntity;
import com.example.ecuapprendejugando.db.repository.ExerciseRepository;
import com.example.ecuapprendejugando.db.repository.RoomRepository;
import com.example.ecuapprendejugando.util.HapticUtil;
import com.example.ecuapprendejugando.viewmodel.TeacherViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoomAdminActivity extends AppCompatActivity {

    private TeacherViewModel teacherVM;
    private String roomCode;
    private int userId;
    private List<ExerciseEntity> exerciseList = new ArrayList<>();
    private ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_admin);

        roomCode  = getIntent().getStringExtra("room_code");
        userId    = getIntent().getIntExtra("user_id", -1);
        teacherVM = new ViewModelProvider(this).get(TeacherViewModel.class);

        TextView tvCode     = findViewById(R.id.tv_room_code);
        TextView tvStudents = findViewById(R.id.tv_students_count);
        tvCode.setText("Código: " + roomCode);

        teacherVM.getConnectedStudents().observe(this, count ->
            tvStudents.setText(count + " alumno(s) conectado(s)"));

        RecyclerView rv = findViewById(R.id.rv_exercises);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseAdapter(exerciseList, exercise -> {
            HapticUtil.tap(this);
            RoomRepository roomRepo = new RoomRepository(this);
            roomRepo.updateStatus(roomCode, "active");
            roomRepo.updateCurrentExercise(roomCode, exercise.id);
            Intent i = new Intent(this, GeneralResultsActivity.class);
            i.putExtra("room_code", roomCode);
            i.putExtra("user_id", userId);
            i.putExtra("exercise_id", exercise.id);
            startActivity(i);
        });
        rv.setAdapter(adapter);

        ExerciseRepository repo = new ExerciseRepository(this);
        for (int topicId = 1; topicId <= 4; topicId++) {
            final int tid = topicId;
            repo.getByTopicSync(tid, list -> {
                if (list != null) {
                    runOnUiThread(() -> {
                        exerciseList.addAll(list);
                        adapter.notifyDataSetChanged();
                    });
                }
            });
        }

        findViewById(R.id.btn_simulate_join).setOnClickListener(v -> {
            HapticUtil.tap(this);
            teacherVM.studentJoined();
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    static class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.VH> {
        private final List<ExerciseEntity> items;
        private final OnExerciseClick listener;

        ExerciseAdapter(List<ExerciseEntity> items, OnExerciseClick listener) {
            this.items = items;
            this.listener = listener;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_exercise, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            ExerciseEntity ex = items.get(position);
            holder.tvQuestion.setText(ex.question);
            holder.tvDifficulty.setText("Dificultad: " + ex.difficulty);
            holder.btnSelect.setOnClickListener(v -> listener.onClick(ex));
        }

        @Override
        public int getItemCount() { return items.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView tvQuestion, tvDifficulty;
            Button btnSelect;
            VH(View v) {
                super(v);
                tvQuestion   = v.findViewById(R.id.tv_exercise_question);
                tvDifficulty = v.findViewById(R.id.tv_exercise_difficulty);
                btnSelect    = v.findViewById(R.id.btn_select_exercise);
            }
        }

        interface OnExerciseClick { void onClick(ExerciseEntity exercise); }
    }
}
