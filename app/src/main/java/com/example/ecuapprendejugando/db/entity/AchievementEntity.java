package com.example.ecuapprendejugando.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "achievements")
public class AchievementEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public String type;   // "first_correct", "streak_5", "level_complete", "room_win", "perfect"
    public String title;
    public String description;
    public String iconRes;
    public long earnedAt;

    public AchievementEntity(int userId, String type, String title, String description, String iconRes) {
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.description = description;
        this.iconRes = iconRes;
        this.earnedAt = System.currentTimeMillis();
    }
}
