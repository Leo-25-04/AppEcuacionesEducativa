package com.example.ecuapprendejugando.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.ecuapprendejugando.db.AppDatabase;
import com.example.ecuapprendejugando.db.dao.RoomDao;
import com.example.ecuapprendejugando.db.entity.RoomEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomRepository {

    private final RoomDao roomDao;
    private final ExecutorService executor;

    public RoomRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.roomDao = db.roomDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(RoomEntity room, OnInsertCallback callback) {
        executor.execute(() -> {
            long id = roomDao.insert(room);
            if (callback != null) callback.onInserted((int) id);
        });
    }

    public void getByCode(String code, OnRoomLoadedCallback callback) {
        executor.execute(() -> {
            RoomEntity room = roomDao.getByCodeSync(code);
            if (callback != null) callback.onLoaded(room);
        });
    }

    public LiveData<RoomEntity> getByCodeLive(String code) {
        return roomDao.getByCode(code);
    }

    public LiveData<List<RoomEntity>> getByTeacher(int teacherId) {
        return roomDao.getByTeacher(teacherId);
    }

    public void updateStatus(String code, String status) {
        executor.execute(() -> roomDao.updateStatus(code, status));
    }

    public void updateCurrentExercise(String code, int exerciseId) {
        executor.execute(() -> roomDao.updateCurrentExercise(code, exerciseId));
    }

    public void cleanOldRooms() {
        long oneDayAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
        executor.execute(() -> roomDao.deleteOldRooms(oneDayAgo));
    }

    public interface OnInsertCallback {
        void onInserted(int id);
    }

    public interface OnRoomLoadedCallback {
        void onLoaded(RoomEntity room);
    }
}
