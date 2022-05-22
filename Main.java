package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static String numLengthString = "";
    static int numLength = 0;
    static int numSymbol = 0;
    static String numSymbolString = "";
    static String alphaNumericString = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static void main(String[] args) {
        numLength = validateUserInput();
        String secretCode1 = generateRandomNumber();
        printGrade(secretCode1);
    }

    public static int validateUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        numLengthString = scanner.next();
        try {
            numLength = Integer.parseInt(numLengthString);
            numLengthString = String.valueOf(numLength);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", numLengthString);
            System.exit(0);
        }
        System.out.println("Input the number of possible symbols in the code:");
        numSymbol = scanner.nextInt();
        if (numLength <= 0) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d", numLength);
            System.exit(0);
        } else if (numSymbol < numLength) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", numLength, numSymbol);
            System.exit(0);
        } else if (numLength > 36 || numSymbol > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        } else {
            numSymbolString = String.valueOf(numSymbol);
            String star = "*";
            StringBuilder str = new StringBuilder();
            str.append(star.repeat(numLength));
            String symbolEnd = String.valueOf(alphaNumericString.charAt(numSymbol - 1));
            System.out.printf("The secret is prepared: %s (0-9, a-%s).", str, symbolEnd);
            System.out.println("Okay, let's start a game!");
        }
        return numLength;
    }

    public static String generateRandomNumber() {
        Random random = new Random();
        StringBuilder secretCode = new StringBuilder(numLength);
        do {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (numSymbol * Math.random());
            // add Character one by one in end of sb
            char symbol = alphaNumericString.charAt(index);
            if (!secretCode.toString().contains(String.valueOf(symbol))) {
                secretCode.append(String.valueOf(symbol));
            }
        } while (secretCode.length() < numLength);
        return secretCode.toString();
    }

    public static void printGrade(String secretCode1) {
        boolean isAllCorrect = false;
        int turn = 1;
        while (!isAllCorrect) {
            System.out.printf("Turn %d:", turn);
            Scanner scanner = new Scanner(System.in);
            String userGuess = scanner.nextLine();
            int bull = 0;
            int cow = 0;
            for (int i = 0; i < userGuess.length(); i++) {
                String userChar = String.valueOf(userGuess.charAt(i));
                String secretChar = String.valueOf(secretCode1.charAt(i));
//                System.out.println("user guess:" + userGuess + " userChar:" + userChar);
//                System.out.println("secret code:" + secretCode1 + " secretChar:" + secretChar);
                if (userChar.equals(secretChar)) {
                    bull++;
                } else if (secretCode1.contains(userChar)) {
                    cow++;
                }
            }
            String grade;
            if (bull > 0 && cow > 0) {
                grade = String.format("%d bull(s) and %d cow(s)", bull, cow);
            } else if (bull > 0) {
                grade = String.format("%d bull(s)", bull);
            } else if (cow > 0) {
                grade = String.format("%d cow(s)", cow);
            } else {
                grade = "None";
            }
            System.out.printf("Grade: %s.", grade);
            turn += 1;
            if (bull == userGuess.length()) {
                isAllCorrect = true;
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }
}

