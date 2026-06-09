package com.example.ecuapprendejugando.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ecuapprendejugando.db.dao.AchievementDao;
import com.example.ecuapprendejugando.db.dao.ExerciseDao;
import com.example.ecuapprendejugando.db.dao.ProgressDao;
import com.example.ecuapprendejugando.db.dao.RoomDao;
import com.example.ecuapprendejugando.db.dao.TopicDao;
import com.example.ecuapprendejugando.db.dao.UserDao;
import com.example.ecuapprendejugando.db.entity.AchievementEntity;
import com.example.ecuapprendejugando.db.entity.ExerciseEntity;
import com.example.ecuapprendejugando.db.entity.ProgressEntity;
import com.example.ecuapprendejugando.db.entity.RoomEntity;
import com.example.ecuapprendejugando.db.entity.TopicEntity;
import com.example.ecuapprendejugando.db.entity.UserEntity;

@Database(
    entities = {
        UserEntity.class,
        TopicEntity.class,
        ExerciseEntity.class,
        ProgressEntity.class,
        RoomEntity.class,
        AchievementEntity.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final String DB_NAME = "ecuaprende_db";

    public abstract UserDao userDao();
    public abstract TopicDao topicDao();
    public abstract ExerciseDao exerciseDao();
    public abstract ProgressDao progressDao();
    public abstract RoomDao roomDao();
    public abstract AchievementDao achievementDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DB_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
