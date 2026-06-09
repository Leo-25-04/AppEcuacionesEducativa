package com.example.ecuapprendejugando.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.ecuapprendejugando.db.AppDatabase;
import com.example.ecuapprendejugando.db.dao.UserDao;
import com.example.ecuapprendejugando.db.entity.UserEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final ExecutorService executor;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.userDao = db.userDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(UserEntity user, OnInsertCallback callback) {
        executor.execute(() -> {
            long id = userDao.insert(user);
            if (callback != null) callback.onInserted((int) id);
        });
    }

    public void update(UserEntity user) {
        executor.execute(() -> userDao.update(user));
    }

    public LiveData<UserEntity> getById(int id) {
        return userDao.getById(id);
    }

    public void getByIdSync(int id, OnUserLoadedCallback callback) {
        executor.execute(() -> {
            UserEntity user = userDao.getByIdSync(id);
            if (callback != null) callback.onLoaded(user);
        });
    }

    public void addPoints(int userId, int points) {
        executor.execute(() -> userDao.addPoints(userId, points));
    }

    public void updateStreak(int userId, int streak) {
        executor.execute(() -> userDao.updateStreak(userId, streak));
    }

    public void updateLevel(int userId, int level) {
        executor.execute(() -> userDao.updateLevel(userId, level));
    }

    public interface OnInsertCallback {
        void onInserted(int id);
    }

    public interface OnUserLoadedCallback {
        void onLoaded(UserEntity user);
    }
}
