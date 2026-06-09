package com.example.ecuapprendejugando.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")
public class ExerciseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int topicId;
    public String question;
    public String correctAnswer;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public String explanation;
    public int difficulty; // 1=easy, 2=medium, 3=hard
    public String type;    // "multiple_choice" or "open"

    public ExerciseEntity(int topicId, String question, String correctAnswer,
                          String optionA, String optionB, String optionC, String optionD,
                          String explanation, int difficulty, String type) {
        this.topicId = topicId;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.explanation = explanation;
        this.difficulty = difficulty;
        this.type = type;
    }
}
