package com.example.ecuapprendejugando.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rooms")
public class RoomEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String code;
    public int teacherId;
    public String status; // "waiting", "active", "finished"
    public int currentExerciseId;
    public long createdAt;

    public RoomEntity(String code, int teacherId) {
        this.code = code;
        this.teacherId = teacherId;
        this.status = "waiting";
        this.currentExerciseId = -1;
        this.createdAt = System.currentTimeMillis();
    }
}
