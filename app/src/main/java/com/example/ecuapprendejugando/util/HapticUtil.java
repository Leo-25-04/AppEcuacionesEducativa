package com.example.ecuapprendejugando.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

public class HapticUtil {

    public static void correct(Context context) {
        vibrate(context, new long[]{0, 80, 40, 80}, new int[]{0, 200, 0, 200});
    }

    public static void wrong(Context context) {
        vibrate(context, new long[]{0, 200, 100, 200, 100, 200}, new int[]{0, 255, 0, 255, 0, 255});
    }

    public static void tap(Context context) {
        vibrate(context, new long[]{0, 40}, new int[]{0, 120});
    }

    public static void achievement(Context context) {
        vibrate(context, new long[]{0, 100, 50, 100, 50, 200}, new int[]{0, 180, 0, 180, 0, 255});
    }

    private static void vibrate(Context context, long[] timings, int[] amplitudes) {
        Vibrator vibrator = getVibrator(context);
        if (vibrator == null || !vibrator.hasVibrator()) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createWaveform(timings, amplitudes, -1);
            vibrator.vibrate(effect);
        } else {
            vibrator.vibrate(timings, -1);
        }
    }

    private static Vibrator getVibrator(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager manager = (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            return manager != null ? manager.getDefaultVibrator() : null;
        } else {
            return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
    }
}
