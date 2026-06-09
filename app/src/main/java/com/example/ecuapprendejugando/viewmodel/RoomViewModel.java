package com.example.ecuapprendejugando.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecuapprendejugando.db.entity.RoomEntity;
import com.example.ecuapprendejugando.db.repository.RoomRepository;

public class RoomViewModel extends AndroidViewModel {

    private final RoomRepository roomRepository;
    private final MutableLiveData<RoomEntity> joinedRoom = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> roomStarted = new MutableLiveData<>();

    public RoomViewModel(@NonNull Application application) {
        super(application);
        roomRepository = new RoomRepository(application);
    }

    public void joinRoom(String code) {
        roomRepository.getByCode(code.toUpperCase(), room -> {
            if (room == null) {
                errorMessage.postValue("Sala no encontrada. Verifica el código.");
            } else if (room.status.equals("finished")) {
                errorMessage.postValue("Esta sala ya finalizó.");
            } else {
                joinedRoom.postValue(room);
            }
        });
    }

    public void checkRoomStatus(String code) {
        roomRepository.getByCode(code, room -> {
            if (room != null && room.status.equals("active")) {
                roomStarted.postValue(true);
            }
        });
    }

    public LiveData<RoomEntity> getJoinedRoom() { return joinedRoom; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getRoomStarted() { return roomStarted; }
}
