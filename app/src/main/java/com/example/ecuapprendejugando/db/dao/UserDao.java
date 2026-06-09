package com.example.ecuapprendejugando.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecuapprendejugando.db.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insert(UserEntity user);

    @Update
    void update(UserEntity user);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<UserEntity> getById(int id);

    @Query("SELECT * FROM users WHERE id = :id")
    UserEntity getByIdSync(int id);

    @Query("SELECT * FROM users WHERE role = :role")
    LiveData<List<UserEntity>> getByRole(String role);

    @Query("UPDATE users SET totalPoints = totalPoints + :points WHERE id = :userId")
    void addPoints(int userId, int points);

    @Query("UPDATE users SET streak = :streak WHERE id = :userId")
    void updateStreak(int userId, int streak);

    @Query("UPDATE users SET currentLevel = :level WHERE id = :userId")
    void updateLevel(int userId, int level);

    @Query("DELETE FROM users")
    void deleteAll();
}
