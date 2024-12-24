package id.ac.binus.myapplication.utils;

import java.util.Random;

public class RandomIDGenerator {
    public static String generateRandomID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            char randomChar = (char) ('A' + random.nextInt(26)); // Random letter between A and Z
            id.append(randomChar);
        }

        for (int i = 0; i < 2; i++) {
            int randomDigit = random.nextInt(10); // Random number between 0 and 9
            id.append(randomDigit);
        }

        return id.toString();
    }
}
