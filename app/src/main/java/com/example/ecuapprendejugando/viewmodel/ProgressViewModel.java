package com.example.ecuapprendejugando.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecuapprendejugando.db.entity.ProgressEntity;
import com.example.ecuapprendejugando.db.repository.ProgressRepository;
import com.example.ecuapprendejugando.db.repository.UserRepository;
import com.example.ecuapprendejugando.util.PointsCalculator;

import java.util.List;

public class ProgressViewModel extends AndroidViewModel {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<Integer> totalPoints = new MutableLiveData<>();
    private final MutableLiveData<Integer> correctCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentLevel = new MutableLiveData<>();

    public ProgressViewModel(@NonNull Application application) {
        super(application);
        progressRepository = new ProgressRepository(application);
        userRepository = new UserRepository(application);
    }

    public void loadStats(int userId) {
        progressRepository.getTotalPoints(userId, points -> {
            totalPoints.postValue(points);
            currentLevel.postValue(PointsCalculator.levelFromPoints(points));
        });
        progressRepository.getCorrectCount(userId, count -> correctCount.postValue(count));
    }

    public LiveData<List<ProgressEntity>> getHistory(int userId) {
        return progressRepository.getByUser(userId);
    }

    public LiveData<Integer> getTotalPoints() { return totalPoints; }
    public LiveData<Integer> getCorrectCount() { return correctCount; }
    public LiveData<Integer> getCurrentLevel() { return currentLevel; }
}
