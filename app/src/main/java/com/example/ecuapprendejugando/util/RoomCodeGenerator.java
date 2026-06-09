package com.example.ecuapprendejugando.util;

import java.util.Random;

public class RoomCodeGenerator {

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 6;

    public static String generate() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public static boolean isValid(String code) {
        if (code == null || code.length() != CODE_LENGTH) return false;
        for (char c : code.toUpperCase().toCharArray()) {
            if (CHARS.indexOf(c) == -1) return false;
        }
        return true;
    }
}
