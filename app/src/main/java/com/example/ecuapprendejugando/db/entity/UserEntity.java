package com.example.ecuapprendejugando.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String role; // "student" or "teacher"
    public String avatarRes;
    public int totalPoints;
    public int currentLevel;
    public int streak;
    public long createdAt;

    public UserEntity(String name, String role) {
        this.name = name;
        this.role = role;
        this.avatarRes = "avatar_default";
        this.totalPoints = 0;
        this.currentLevel = 1;
        this.streak = 0;
        this.createdAt = System.currentTimeMillis();
    }
}
