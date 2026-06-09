package com.example.ecuapprendejugando.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "topics")
public class TopicEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public int level;
    public boolean unlocked;
    public boolean completed;
    public String iconRes;

    public TopicEntity(String title, String description, int level, boolean unlocked) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.unlocked = unlocked;
        this.completed = false;
        this.iconRes = "ic_topic_default";
    }
}
