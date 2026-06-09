package com.example.ecuapprendejugando.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecuapprendejugando.db.entity.RoomEntity;
import com.example.ecuapprendejugando.db.repository.RoomRepository;
import com.example.ecuapprendejugando.util.RoomCodeGenerator;

public class TeacherViewModel extends AndroidViewModel {

    private final RoomRepository roomRepository;
    private final MutableLiveData<RoomEntity> activeRoom = new MutableLiveData<>();
    private final MutableLiveData<String> roomCode = new MutableLiveData<>();
    private final MutableLiveData<Integer> connectedStudents = new MutableLiveData<>();

    public TeacherViewModel(@NonNull Application application) {
        super(application);
        roomRepository = new RoomRepository(application);
        connectedStudents.setValue(0);
    }

    public void createRoom(int teacherId) {
        String code = RoomCodeGenerator.generate();
        RoomEntity room = new RoomEntity(code, teacherId);
        roomRepository.insert(room, id -> {
            room.id = id;
            activeRoom.postValue(room);
            roomCode.postValue(code);
        });
    }

    public LiveData<RoomEntity> getActiveRoom() { return activeRoom; }
    public LiveData<String> getRoomCode() { return roomCode; }
    public LiveData<Integer> getConnectedStudents() { return connectedStudents; }

    public void studentJoined() {
        Integer current = connectedStudents.getValue();
        connectedStudents.postValue(current != null ? current + 1 : 1);
    }

    public void startActivity(String code, int exerciseId) {
        roomRepository.updateStatus(code, "active");
        roomRepository.updateCurrentExercise(code, exerciseId);
    }

    public void finishRoom(String code) {
        roomRepository.updateStatus(code, "finished");
    }
}
