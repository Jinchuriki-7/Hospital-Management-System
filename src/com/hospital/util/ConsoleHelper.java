package com.hospital.util;

import java.util.Scanner;

public class ConsoleHelper {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private static final Scanner scanner = new Scanner(System.in);

    public static void printHeader(String title) {
        System.out.println();
        System.out.println(CYAN + "==========================================================================================" + RESET);
        System.out.println(BOLD + CYAN + "   " + title.toUpperCase() + RESET);
        System.out.println(CYAN + "==========================================================================================" + RESET);
    }

    public static void printSubHeader(String subtitle) {
        System.out.println(YELLOW + "--- " + subtitle + " ---" + RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + BOLD + "[SUCCESS] " + RESET + GREEN + message + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + BOLD + "[ERROR] " + RESET + RED + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.println(YELLOW + BOLD + "[WARNING] " + RESET + YELLOW + message + RESET);
    }

    public static void printInfo(String message) {
        System.out.println(BLUE + "[INFO] " + RESET + message);
    }

    public static String readString(String prompt) {
        System.out.print(BOLD + prompt + ": " + RESET);
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            printWarning("Input cannot be empty. Please enter again.");
            System.out.print(BOLD + prompt + ": " + RESET);
            input = scanner.nextLine().trim();
        }
        return input;
    }

    public static String readOptionalString(String prompt, String defaultValue) {
        System.out.print(BOLD + prompt + " [" + defaultValue + "]: " + RESET);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(BOLD + prompt + ": " + RESET);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    printWarning(String.format("Please enter a number between %d and %d.", min, max));
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                printError("Invalid number format. Please try again.");
            }
        }
    }

    public static double readDouble(String prompt, double min) {
        while (true) {
            System.out.print(BOLD + prompt + ": " + RESET);
            String line = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(line);
                if (value < min) {
                    printWarning("Value must be at least " + min);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                printError("Invalid amount format. Please try again.");
            }
        }
    }

    public static void pressEnterToContinue() {
        System.out.print(PURPLE + "\nPress [ENTER] to return to menu..." + RESET);
        scanner.nextLine();
    }
}
