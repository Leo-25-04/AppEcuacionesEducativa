package com.example.ecuapprendejugando.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ecuapprendejugando.db.entity.ExerciseEntity;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(ExerciseEntity exercise);

    @Insert
    void insertAll(List<ExerciseEntity> exercises);

    @Query("SELECT * FROM exercises WHERE topicId = :topicId ORDER BY difficulty ASC")
    LiveData<List<ExerciseEntity>> getByTopic(int topicId);

    @Query("SELECT * FROM exercises WHERE topicId = :topicId ORDER BY difficulty ASC")
    List<ExerciseEntity> getByTopicSync(int topicId);

    @Query("SELECT * FROM exercises WHERE id = :id")
    ExerciseEntity getByIdSync(int id);

    @Query("SELECT * FROM exercises WHERE topicId = :topicId AND difficulty = :difficulty")
    List<ExerciseEntity> getByTopicAndDifficulty(int topicId, int difficulty);

    @Query("SELECT COUNT(*) FROM exercises WHERE topicId = :topicId")
    int getCountByTopic(int topicId);

    @Query("SELECT COUNT(*) FROM exercises")
    int getCount();
}
