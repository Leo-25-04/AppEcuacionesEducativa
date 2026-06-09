package com.example.ecuapprendejugando.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.ecuapprendejugando.db.AppDatabase;
import com.example.ecuapprendejugando.db.dao.ExerciseDao;
import com.example.ecuapprendejugando.db.dao.TopicDao;
import com.example.ecuapprendejugando.db.entity.ExerciseEntity;
import com.example.ecuapprendejugando.db.entity.TopicEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExerciseRepository {

    private final ExerciseDao exerciseDao;
    private final TopicDao topicDao;
    private final ExecutorService executor;

    public ExerciseRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.exerciseDao = db.exerciseDao();
        this.topicDao = db.topicDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ExerciseEntity>> getByTopic(int topicId) {
        return exerciseDao.getByTopic(topicId);
    }

    public void getByTopicSync(int topicId, OnExercisesLoadedCallback callback) {
        executor.execute(() -> {
            List<ExerciseEntity> list = exerciseDao.getByTopicSync(topicId);
            if (callback != null) callback.onLoaded(list);
        });
    }

    public void getByIdSync(int id, OnExerciseLoadedCallback callback) {
        executor.execute(() -> {
            ExerciseEntity ex = exerciseDao.getByIdSync(id);
            if (callback != null) callback.onLoaded(ex);
        });
    }

    public LiveData<List<TopicEntity>> getAllTopics() {
        return topicDao.getAll();
    }

    public void unlockNextLevel(int currentLevel) {
        executor.execute(() -> topicDao.unlockLevel(currentLevel + 1));
    }

    public void markTopicCompleted(int topicId) {
        executor.execute(() -> topicDao.markCompleted(topicId));
    }

    public interface OnExercisesLoadedCallback {
        void onLoaded(List<ExerciseEntity> exercises);
    }

    public interface OnExerciseLoadedCallback {
        void onLoaded(ExerciseEntity exercise);
    }
}
