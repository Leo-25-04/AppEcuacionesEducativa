package com.example.ecuapprendejugando.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ecuapprendejugando.db.entity.AchievementEntity;

import java.util.List;

@Dao
public interface AchievementDao {

    @Insert
    void insert(AchievementEntity achievement);

    @Query("SELECT * FROM achievements WHERE userId = :userId ORDER BY earnedAt DESC")
    LiveData<List<AchievementEntity>> getByUser(int userId);

    @Query("SELECT COUNT(*) FROM achievements WHERE userId = :userId AND type = :type")
    int hasAchievement(int userId, String type);

    @Query("SELECT COUNT(*) FROM achievements WHERE userId = :userId")
    int getCountByUser(int userId);
}
