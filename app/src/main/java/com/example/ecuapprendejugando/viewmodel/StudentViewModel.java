package com.example.ecuapprendejugando.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecuapprendejugando.db.entity.UserEntity;
import com.example.ecuapprendejugando.db.repository.UserRepository;

public class StudentViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<UserEntity> currentUser = new MutableLiveData<>();
    private final MutableLiveData<Integer> pointsEvent = new MutableLiveData<>();

    public StudentViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void loadUser(int userId) {
        userRepository.getByIdSync(userId, user -> currentUser.postValue(user));
    }

    public LiveData<UserEntity> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Integer> getPointsEvent() {
        return pointsEvent;
    }

    public void addPoints(int userId, int points) {
        userRepository.addPoints(userId, points);
        pointsEvent.postValue(points);
    }

    public void updateStreak(int userId, int streak) {
        userRepository.updateStreak(userId, streak);
    }

    public void updateLevel(int userId, int level) {
        userRepository.updateLevel(userId, level);
    }
}
