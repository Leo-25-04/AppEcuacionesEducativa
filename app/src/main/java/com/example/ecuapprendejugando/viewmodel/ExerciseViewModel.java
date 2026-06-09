package com.example.ecuapprendejugando.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecuapprendejugando.db.entity.ExerciseEntity;
import com.example.ecuapprendejugando.db.entity.ProgressEntity;
import com.example.ecuapprendejugando.db.repository.ExerciseRepository;
import com.example.ecuapprendejugando.db.repository.ProgressRepository;
import com.example.ecuapprendejugando.util.PointsCalculator;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private final ExerciseRepository exerciseRepository;
    private final ProgressRepository progressRepository;

    private final MutableLiveData<List<ExerciseEntity>> exercises = new MutableLiveData<>();
    private final MutableLiveData<ExerciseEntity> currentExercise = new MutableLiveData<>();
    private final MutableLiveData<Boolean> answerResult = new MutableLiveData<>();
    private final MutableLiveData<Integer> pointsEarned = new MutableLiveData<>();

    private int currentIndex = 0;
    private int attempts = 0;
    private int streak = 0;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        exerciseRepository = new ExerciseRepository(application);
        progressRepository = new ProgressRepository(application);
    }

    public void loadExercises(int topicId) {
        exerciseRepository.getByTopicSync(topicId, list -> {
            exercises.postValue(list);
            if (list != null && !list.isEmpty()) {
                currentIndex = 0;
                attempts = 0;
                currentExercise.postValue(list.get(0));
            }
        });
    }

    public LiveData<ExerciseEntity> getCurrentExercise() { return currentExercise; }
    public LiveData<Boolean> getAnswerResult() { return answerResult; }
    public LiveData<Integer> getPointsEarned() { return pointsEarned; }
    public LiveData<List<ExerciseEntity>> getExercises() { return exercises; }

    public void submitAnswer(String answer, int userId, int topicId) {
        ExerciseEntity ex = currentExercise.getValue();
        if (ex == null) return;
        attempts++;
        boolean correct = ex.correctAnswer.trim().equalsIgnoreCase(answer.trim());
        int pts = PointsCalculator.calculate(correct, attempts, streak);
        streak = PointsCalculator.newStreak(correct, streak);

        ProgressEntity progress = new ProgressEntity(userId, topicId, ex.id, correct, Math.max(pts, 0));
        progress.attempts = attempts;
        progressRepository.insert(progress);

        answerResult.postValue(correct);
        pointsEarned.postValue(pts);
    }

    public boolean moveToNext() {
        List<ExerciseEntity> list = exercises.getValue();
        if (list == null) return false;
        currentIndex++;
        attempts = 0;
        if (currentIndex < list.size()) {
            currentExercise.postValue(list.get(currentIndex));
            return true;
        }
        return false;
    }

    public int getStreak() { return streak; }
    public int getCurrentIndex() { return currentIndex; }
    public int getTotalExercises() {
        List<ExerciseEntity> list = exercises.getValue();
        return list != null ? list.size() : 0;
    }
}
