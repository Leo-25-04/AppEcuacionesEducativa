package com.example.ecuapprendejugando.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecuapprendejugando.db.entity.TopicEntity;

import java.util.List;

@Dao
public interface TopicDao {

    @Insert
    void insert(TopicEntity topic);

    @Insert
    void insertAll(List<TopicEntity> topics);

    @Update
    void update(TopicEntity topic);

    @Query("SELECT * FROM topics ORDER BY level ASC")
    LiveData<List<TopicEntity>> getAll();

    @Query("SELECT * FROM topics WHERE id = :id")
    TopicEntity getByIdSync(int id);

    @Query("SELECT * FROM topics WHERE unlocked = 1 ORDER BY level ASC")
    LiveData<List<TopicEntity>> getUnlocked();

    @Query("UPDATE topics SET unlocked = 1 WHERE level = :level")
    void unlockLevel(int level);

    @Query("UPDATE topics SET completed = 1 WHERE id = :topicId")
    void markCompleted(int topicId);

    @Query("SELECT COUNT(*) FROM topics")
    int getCount();
}
