package com.example.ecuapprendejugando.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecuapprendejugando.R;
import com.example.ecuapprendejugando.db.entity.TopicEntity;
import com.example.ecuapprendejugando.db.repository.ExerciseRepository;
import com.example.ecuapprendejugando.util.HapticUtil;

import java.util.ArrayList;
import java.util.List;

public class TopicsActivity extends AppCompatActivity {

    private int userId;
    private List<TopicEntity> topicList = new ArrayList<>();
    private TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        userId = getIntent().getIntExtra("user_id", -1);

        RecyclerView rv = findViewById(R.id.rv_topics);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TopicAdapter(topicList, topic -> {
            if (!topic.unlocked) {
                Toast.makeText(this, "Completa el nivel anterior primero", Toast.LENGTH_SHORT).show();
                return;
            }
            HapticUtil.tap(this);
            Intent i = new Intent(this, TheoryContentActivity.class);
            i.putExtra("topic_id", topic.id);
            i.putExtra("topic_title", topic.title);
            i.putExtra("user_id", userId);
            startActivity(i);
        });
        rv.setAdapter(adapter);

        ExerciseRepository repo = new ExerciseRepository(this);
        repo.getAllTopics().observe(this, topics -> {
            topicList.clear();
            topicList.addAll(topics);
            adapter.notifyDataSetChanged();
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    static class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.VH> {
        private final List<TopicEntity> items;
        private final OnTopicClick listener;

        TopicAdapter(List<TopicEntity> items, OnTopicClick listener) {
            this.items = items;
            this.listener = listener;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            TopicEntity t = items.get(position);
            holder.tvTitle.setText(t.title);
            holder.tvDesc.setText(t.description);
            holder.tvLevel.setText("Nivel " + t.level);
            holder.itemView.setAlpha(t.unlocked ? 1f : 0.4f);
            holder.itemView.setOnClickListener(v -> listener.onClick(t));
        }

        @Override
        public int getItemCount() { return items.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView tvTitle, tvDesc, tvLevel;
            VH(View v) {
                super(v);
                tvTitle = v.findViewById(R.id.tv_topic_title);
                tvDesc = v.findViewById(R.id.tv_topic_desc);
                tvLevel = v.findViewById(R.id.tv_topic_level);
            }
        }

        interface OnTopicClick { void onClick(TopicEntity topic); }
    }
}
