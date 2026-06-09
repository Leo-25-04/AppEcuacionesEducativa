package com.example.ecuapprendejugando.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "progress")
public class ProgressEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public int topicId;
    public int exerciseId;
    public boolean correct;
    public int pointsEarned;
    public int attempts;
    public long timestamp;

    public ProgressEntity(int userId, int topicId, int exerciseId, boolean correct, int pointsEarned) {
        this.userId = userId;
        this.topicId = topicId;
        this.exerciseId = exerciseId;
        this.correct = correct;
        this.pointsEarned = pointsEarned;
        this.attempts = 1;
        this.timestamp = System.currentTimeMillis();
    }
}
