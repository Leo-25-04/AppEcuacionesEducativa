package com.example.ecuapprendejugando.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.ecuapprendejugando.db.AppDatabase;
import com.example.ecuapprendejugando.db.dao.ProgressDao;
import com.example.ecuapprendejugando.db.entity.ProgressEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressRepository {

    private final ProgressDao progressDao;
    private final ExecutorService executor;

    public ProgressRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.progressDao = db.progressDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(ProgressEntity progress) {
        executor.execute(() -> progressDao.insert(progress));
    }

    public LiveData<List<ProgressEntity>> getByUser(int userId) {
        return progressDao.getByUser(userId);
    }

    public void getTotalPoints(int userId, OnIntResultCallback callback) {
        executor.execute(() -> {
            int points = progressDao.getTotalPoints(userId);
            if (callback != null) callback.onResult(points);
        });
    }

    public void getCorrectCount(int userId, OnIntResultCallback callback) {
        executor.execute(() -> {
            int count = progressDao.getCorrectCount(userId);
            if (callback != null) callback.onResult(count);
        });
    }

    public void getCorrectCountByTopic(int userId, int topicId, OnIntResultCallback callback) {
        executor.execute(() -> {
            int count = progressDao.getCorrectCountByTopic(userId, topicId);
            if (callback != null) callback.onResult(count);
        });
    }

    public void getAttemptedCountByTopic(int userId, int topicId, OnIntResultCallback callback) {
        executor.execute(() -> {
            int count = progressDao.getAttemptedCountByTopic(userId, topicId);
            if (callback != null) callback.onResult(count);
        });
    }

    public interface OnIntResultCallback {
        void onResult(int value);
    }
}
