package com.example.ecuapprendejugando.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ecuapprendejugando.db.entity.ProgressEntity;

import java.util.List;

@Dao
public interface ProgressDao {

    @Insert
    void insert(ProgressEntity progress);

    @Query("SELECT * FROM progress WHERE userId = :userId ORDER BY timestamp DESC")
    LiveData<List<ProgressEntity>> getByUser(int userId);

    @Query("SELECT * FROM progress WHERE userId = :userId AND topicId = :topicId")
    List<ProgressEntity> getByUserAndTopic(int userId, int topicId);

    @Query("SELECT SUM(pointsEarned) FROM progress WHERE userId = :userId")
    int getTotalPoints(int userId);

    @Query("SELECT COUNT(*) FROM progress WHERE userId = :userId AND correct = 1")
    int getCorrectCount(int userId);

    @Query("SELECT COUNT(*) FROM progress WHERE userId = :userId AND topicId = :topicId AND correct = 1")
    int getCorrectCountByTopic(int userId, int topicId);

    @Query("SELECT COUNT(DISTINCT exerciseId) FROM progress WHERE userId = :userId AND topicId = :topicId")
    int getAttemptedCountByTopic(int userId, int topicId);
}
