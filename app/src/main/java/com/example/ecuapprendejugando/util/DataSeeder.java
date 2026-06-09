package com.example.ecuapprendejugando.util;

import android.content.Context;

import com.example.ecuapprendejugando.db.AppDatabase;
import com.example.ecuapprendejugando.db.entity.ExerciseEntity;
import com.example.ecuapprendejugando.db.entity.TopicEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataSeeder {

    public static void seedIfEmpty(Context context, OnSeededCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AppDatabase db = AppDatabase.getInstance(context);

        executor.execute(() -> {
            int topicCount = db.topicDao().getCount();
            if (topicCount > 0) {
                if (callback != null) callback.onSeeded();
                return;
            }

            // --- Topics ---
            List<TopicEntity> topics = new ArrayList<>();
            topics.add(new TopicEntity("Ecuaciones de primer grado", "Aprende a resolver ax + b = c", 1, true));
            topics.add(new TopicEntity("Ecuaciones con paréntesis", "Maneja distribución y agrupación", 2, false));
            topics.add(new TopicEntity("Ecuaciones con fracciones", "Trabaja con denominadores", 3, false));
            topics.add(new TopicEntity("Sistemas de ecuaciones", "Resuelve dos ecuaciones simultáneas", 4, false));
            db.topicDao().insertAll(topics);

            // --- Exercises Topic 1 ---
            List<ExerciseEntity> ex = new ArrayList<>();

            ex.add(new ExerciseEntity(1,
                "Resuelve: 2x + 4 = 10",
                "3",
                "2", "3", "4", "5",
                "Restamos 4 a ambos lados: 2x = 6, luego dividimos entre 2: x = 3",
                1, "multiple_choice"));

            ex.add(new ExerciseEntity(1,
                "Resuelve: 3x - 6 = 9",
                "5",
                "3", "4", "5", "6",
                "Sumamos 6 a ambos lados: 3x = 15, luego dividimos entre 3: x = 5",
                1, "multiple_choice"));

            ex.add(new ExerciseEntity(1,
                "Resuelve: x + 7 = 15",
                "8",
                "6", "7", "8", "9",
                "Restamos 7 a ambos lados: x = 8",
                1, "multiple_choice"));

            ex.add(new ExerciseEntity(1,
                "Resuelve: 5x = 35",
                "7",
                "5", "6", "7", "8",
                "Dividimos ambos lados entre 5: x = 7",
                2, "multiple_choice"));

            ex.add(new ExerciseEntity(1,
                "Resuelve: 4x + 3 = 19",
                "4",
                "3", "4", "5", "6",
                "Restamos 3: 4x = 16, dividimos entre 4: x = 4",
                2, "multiple_choice"));

            // --- Exercises Topic 2 ---
            ex.add(new ExerciseEntity(2,
                "Resuelve: 2(x + 3) = 14",
                "4",
                "2", "3", "4", "5",
                "Distribuimos: 2x + 6 = 14, restamos 6: 2x = 8, dividimos: x = 4",
                1, "multiple_choice"));

            ex.add(new ExerciseEntity(2,
                "Resuelve: 3(2x - 1) = 15",
                "3",
                "2", "3", "4", "5",
                "Distribuimos: 6x - 3 = 15, sumamos 3: 6x = 18, dividimos: x = 3",
                2, "multiple_choice"));

            ex.add(new ExerciseEntity(2,
                "Resuelve: 4(x + 5) = 28",
                "2",
                "1", "2", "3", "4",
                "Distribuimos: 4x + 20 = 28, restamos 20: 4x = 8, dividimos: x = 2",
                2, "multiple_choice"));

            // --- Exercises Topic 3 ---
            ex.add(new ExerciseEntity(3,
                "Resuelve: x/2 + 3 = 7",
                "8",
                "4", "6", "8", "10",
                "Restamos 3: x/2 = 4, multiplicamos por 2: x = 8",
                1, "multiple_choice"));

            ex.add(new ExerciseEntity(3,
                "Resuelve: x/3 - 2 = 4",
                "18",
                "12", "15", "18", "21",
                "Sumamos 2: x/3 = 6, multiplicamos por 3: x = 18",
                2, "multiple_choice"));

            // --- Exercises Topic 4 ---
            ex.add(new ExerciseEntity(4,
                "Si x + y = 10 y x - y = 4, ¿cuánto vale x?",
                "7",
                "5", "6", "7", "8",
                "Sumamos las ecuaciones: 2x = 14, dividimos: x = 7",
                3, "multiple_choice"));

            ex.add(new ExerciseEntity(4,
                "Si 2x + y = 11 y x + y = 7, ¿cuánto vale x?",
                "4",
                "2", "3", "4", "5",
                "Restamos la segunda de la primera: x = 4",
                3, "multiple_choice"));

            db.exerciseDao().insertAll(ex);

            if (callback != null) callback.onSeeded();
        });
    }

    public interface OnSeededCallback {
        void onSeeded();
    }
}
