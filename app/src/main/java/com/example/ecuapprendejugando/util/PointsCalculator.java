package com.example.ecuapprendejugando.util;

public class PointsCalculator {

    public static final int CORRECT_FIRST_TRY   = 10;
    public static final int CORRECT_SECOND_TRY  = 5;
    public static final int WRONG_PENALTY       = -2;
    public static final int STREAK_BONUS        = 15;
    public static final int STREAK_THRESHOLD    = 3;

    public static int calculate(boolean correct, int attempts, int currentStreak) {
        if (!correct) return WRONG_PENALTY;
        int points = (attempts == 1) ? CORRECT_FIRST_TRY : CORRECT_SECOND_TRY;
        if (currentStreak > 0 && currentStreak % STREAK_THRESHOLD == 0) {
            points += STREAK_BONUS;
        }
        return points;
    }

    public static int newStreak(boolean correct, int currentStreak) {
        return correct ? currentStreak + 1 : 0;
    }

    public static String formatPoints(int points) {
        return points >= 0 ? "+" + points : String.valueOf(points);
    }

    public static int levelFromPoints(int totalPoints) {
        if (totalPoints < 100)  return 1;
        if (totalPoints < 300)  return 2;
        if (totalPoints < 600)  return 3;
        return 4;
    }

    public static int pointsToNextLevel(int totalPoints) {
        int level = levelFromPoints(totalPoints);
        int[] thresholds = {100, 300, 600, Integer.MAX_VALUE};
        return Math.max(0, thresholds[level - 1] - totalPoints);
    }
}
