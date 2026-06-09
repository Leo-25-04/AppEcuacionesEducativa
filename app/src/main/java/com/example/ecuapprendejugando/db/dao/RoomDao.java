package com.example.ecuapprendejugando.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecuapprendejugando.db.entity.RoomEntity;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert
    long insert(RoomEntity room);

    @Update
    void update(RoomEntity room);

    @Query("SELECT * FROM rooms WHERE code = :code LIMIT 1")
    RoomEntity getByCodeSync(String code);

    @Query("SELECT * FROM rooms WHERE code = :code LIMIT 1")
    LiveData<RoomEntity> getByCode(String code);

    @Query("SELECT * FROM rooms WHERE teacherId = :teacherId ORDER BY createdAt DESC")
    LiveData<List<RoomEntity>> getByTeacher(int teacherId);

    @Query("UPDATE rooms SET status = :status WHERE code = :code")
    void updateStatus(String code, String status);

    @Query("UPDATE rooms SET currentExerciseId = :exerciseId WHERE code = :code")
    void updateCurrentExercise(String code, int exerciseId);

    @Query("DELETE FROM rooms WHERE createdAt < :timestamp")
    void deleteOldRooms(long timestamp);
}
