import java.util.Scanner;

public class Week1Assignment {

    // ==========================================
    // 1. EXAM HALL SEAT DUPLICATION CHECKER
    // ==========================================

    static void checkDuplicateSeats(int[] seatNumbers) {

        boolean duplicateFound = false;

        System.out.println("\n========== SEAT DUPLICATION CHECK ==========");

        for (int i = 0; i < seatNumbers.length; i++) {

            for (int j = i + 1; j < seatNumbers.length; j++) {

                if (seatNumbers[i] == seatNumbers[j]) {

                    System.out.println(
                            "Duplicate Seat Number Found: "
                                    + seatNumbers[i]
                    );

                    duplicateFound = true;
                }
            }
        }

        if (!duplicateFound) {
            System.out.println("No Duplicate Seats Found");
        }
    }


    // ==========================================
    // 2. TYPING SPEED TEST ACCURACY CHECKER
    // ==========================================

    static void checkTypingAccuracy(
            String original,
            String typed) {

        int totalLength = original.length();
        int matched = 0;
        int firstMismatch = -1;

        // Compare characters
        for (int i = 0; i < totalLength; i++) {

            if (original.charAt(i) == typed.charAt(i)) {

                matched++;

            } else if (firstMismatch == -1) {

                firstMismatch = i;
            }
        }

        double accuracy =
                (matched * 100.0) / totalLength;

        System.out.println("\n========== TYPING ACCURACY ==========");

        System.out.printf(
                "Matched: %d/%d | Accuracy: %.2f%%%n",
                matched,
                totalLength,
                accuracy
        );

        if (firstMismatch == -1) {

            System.out.println("No Mismatches");

        } else {

            System.out.println(
                    "First Mismatch at position "
                            + (firstMismatch + 1)
                            + " ('"
                            + original.charAt(firstMismatch)
                            + "' vs '"
                            + typed.charAt(firstMismatch)
                            + "')"
            );
        }
    }


    // ==========================================
    // 3. TRAFFIC SIGNAL STREAK ANALYZER
    // ==========================================

    static void findLongestStreak(String signalLog) {

        if (signalLog == null ||
                signalLog.length() == 0) {

            System.out.println(
                    "Signal log is empty."
            );

            return;
        }

        char longestColor = signalLog.charAt(0);

        int longestStreak = 1;

        char currentColor = signalLog.charAt(0);

        int currentStreak = 1;

        for (int i = 1; i < signalLog.length(); i++) {

            char currentChar = signalLog.charAt(i);

            if (currentChar == currentColor) {

                currentStreak++;

            } else {

                currentColor = currentChar;
                currentStreak = 1;
            }

            if (currentStreak > longestStreak) {

                longestStreak = currentStreak;
                longestColor = currentColor;
            }
        }

        System.out.println(
                "\n========== TRAFFIC SIGNAL ANALYSIS =========="
        );

        System.out.println(
                "Longest Streak: '"
                        + longestColor
                        + "' repeated "
                        + longestStreak
                        + " times"
        );
    }


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("       WEEK 1 ASSIGNMENT");
        System.out.println("======================================");

        System.out.println("1. Seat Duplication Checker");
        System.out.println("2. Typing Accuracy Checker");
        System.out.println("3. Traffic Signal Streak Analyzer");

        System.out.print("\nChoose a problem (1-3): ");

        int choice = sc.nextInt();
        sc.nextLine();


        switch (choice) {

            // ==================================
            // PROBLEM 1
            // ==================================

            case 1:

                int[] seatNumbers = {
                        101,
                        102,
                        103,
                        102,
                        105
                };

                checkDuplicateSeats(seatNumbers);

                break;


            // ==================================
            // PROBLEM 2
            // ==================================

            case 2:

                System.out.print(
                        "Enter original passage: "
                );

                String original = sc.nextLine();

                System.out.print(
                        "Enter typed text: "
                );

                String typed = sc.nextLine();

                if (original.length() != typed.length()) {

                    System.out.println(
                            "Error: Both strings must have equal length."
                    );

                } else {

                    checkTypingAccuracy(
                            original,
                            typed
                    );
                }

                break;


            // ==================================
            // PROBLEM 3
            // ==================================

            case 3:

                System.out.print(
                        "Enter traffic signal log: "
                );

                String signalLog = sc.nextLine();

                findLongestStreak(signalLog);

                break;


            default:

                System.out.println(
                        "Invalid choice."
                );
        }

        sc.close();
    }
}