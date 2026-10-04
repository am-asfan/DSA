package utils;

import java.util.Scanner;

/**
 * Reads and validates all keyboard input so invalid input never crashes the program.
 * Field readers keep asking until a valid value is entered.
 */
public class InputValidator {

    public static final int MIN_MARKS = 0;
    public static final int MAX_MARKS = 100;

    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Reads one trimmed line. Exits cleanly if the input stream has ended. */
    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("No more input. Exiting system.");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    /**
     * Reads a menu choice.
     * @return the number chosen, or -1 if it is not a number between min and max
     */
    public int readMenuChoice(String prompt, int min, int max) {
        String input = readLine(prompt);
        Integer number = parseInteger(input);
        if (number == null || number < min || number > max) {
            return -1;
        }
        return number;
    }

    /** Reads text that must not be empty (name, programme, location, description...). */
    public String readNonEmpty(String prompt, String fieldName) {
        while (true) {
            String input = readLine(prompt);
            if (isNonEmpty(input)) {
                return input;
            }
            System.out.println(fieldName + " cannot be empty. Please try again.");
        }
    }

    /**
     * Reads an ID (Student ID / Request ID).
     * IDs must not be empty or contain spaces, and are stored in UPPER CASE
     * so "it001" and "IT001" are treated as the same ID.
     */
    public String readId(String prompt, String fieldName) {
        while (true) {
            String input = readLine(prompt);
            if (!isNonEmpty(input)) {
                System.out.println(fieldName + " cannot be empty. Please try again.");
            } else if (input.contains(" ")) {
                System.out.println(fieldName + " cannot contain spaces. Please try again.");
            } else {
                return input.toUpperCase();
            }
        }
    }

    /** Reads marks; only whole numbers from 0 to 100 are accepted. */
    public int readMarks(String prompt) {
        while (true) {
            String input = readLine(prompt);
            Integer marks = parseInteger(input);
            if (marks != null && isValidMarks(marks)) {
                return marks;
            }
            System.out.println("Invalid marks. Please enter a whole number between "
                    + MIN_MARKS + " and " + MAX_MARKS + ".");
        }
    }

    /** Reads a Y/N answer. */
    public boolean readYesNo(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                return true;
            }
            if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        }
    }

    public static boolean isNonEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isValidMarks(int marks) {
        return marks >= MIN_MARKS && marks <= MAX_MARKS;
    }

    /** Converts text to an Integer, or returns null when the text is not a whole number. */
    private static Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
