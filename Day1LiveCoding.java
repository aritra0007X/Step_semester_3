import java.util.*;

public class Day1LiveCoding {

    // ================================
    // 1. ROCK PAPER SCISSORS
    // ================================

    static String playRound(String playerMove, String computerMove) {

        if (playerMove.equals(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
            (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
            (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {

            return "Player Wins";
        }

        return "Computer Wins";
    }


    // ================================
    // 2. PALINDROME CHECKER
    // ================================

    static boolean isPalindromeIterative(String text) {

        int left = 0;
        int right = text.length() - 1;

        while (left < right) {

            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }


    static boolean isPalindromeRecursive(String text) {
        return recursiveCheck(text, 0, text.length() - 1);
    }

    static boolean recursiveCheck(String text, int left, int right) {

        if (left >= right) {
            return true;
        }

        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }

        return recursiveCheck(text, left + 1, right - 1);
    }


    static boolean isPalindromeArrayReversal(String text) {

        char[] original = text.toCharArray();
        char[] reversed = text.toCharArray();

        int left = 0;
        int right = reversed.length - 1;

        while (left < right) {

            char temp = reversed[left];
            reversed[left] = reversed[right];
            reversed[right] = temp;

            left++;
            right--;
        }

        return Arrays.equals(original, reversed);
    }


    // ================================
    // 3. BMI CALCULATOR
    // ================================

    static String getBmiStatus(double bmi) {

        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }


    static void printWellnessReport(double[] heights, double[] weights) {

        System.out.println("\n========== WELLNESS REPORT ==========");

        System.out.printf(
                "%-10s %-15s %-15s %-10s %-15s%n",
                "Person",
                "Height (m)",
                "Weight (kg)",
                "BMI",
                "Status"
        );

        for (int i = 0; i < heights.length; i++) {

            double bmi = weights[i] /
                    (heights[i] * heights[i]);

            String status = getBmiStatus(bmi);

            System.out.printf(
                    "%-10d %-15.2f %-15.2f %-10.2f %-15s%n",
                    i + 1,
                    heights[i],
                    weights[i],
                    bmi,
                    status
            );
        }
    }


    // ================================
    // 4. FIRST NON-REPEATING CHARACTER
    // ================================

    static char findFirstNonRepeatingChar(String text) {

        HashMap<Character, Integer> frequency =
                new HashMap<>();

        // Count characters
        for (char ch : text.toCharArray()) {

            frequency.put(
                    ch,
                    frequency.getOrDefault(ch, 0) + 1
            );
        }

        // Find first unique character
        for (char ch : text.toCharArray()) {

            if (frequency.get(ch) == 1) {
                return ch;
            }
        }

        return '\0';
    }


    // ================================
    // 5. REVERSE CUSTOMER NAME
    // ================================

    static String reverseCustomerName(String customerName) {

        char[] characters =
                customerName.toCharArray();

        int left = 0;
        int right = characters.length - 1;

        while (left < right) {

            char temp = characters[left];

            characters[left] = characters[right];
            characters[right] = temp;

            left++;
            right--;
        }

        return new String(characters);
    }


    // ================================
    // MAIN METHOD
    // ================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("       DAY 1 LIVE CODING");
        System.out.println("================================");

        System.out.println("\n1. Rock-Paper-Scissors");
        System.out.println("2. Palindrome Checker");
        System.out.println("3. BMI Calculator");
        System.out.println("4. First Non-Repeating Character");
        System.out.println("5. Reverse Customer Name");

        System.out.print("\nChoose a problem (1-5): ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:

                Random random = new Random();

                String[] moves = {
                        "Rock",
                        "Paper",
                        "Scissors"
                };

                int wins = 0;
                int losses = 0;
                int draws = 0;

                for (int i = 1; i <= 5; i++) {

                    System.out.print(
                            "\nRound " + i +
                            " - Enter your move: "
                    );

                    String playerMove = sc.nextLine();

                    String computerMove =
                            moves[random.nextInt(3)];

                    String result =
                            playRound(
                                    playerMove,
                                    computerMove
                            );

                    System.out.println(
                            "Computer: " + computerMove
                    );

                    System.out.println(
                            "Result: " + result
                    );

                    if (result.equals("Player Wins")) {
                        wins++;
                    } else if (
                            result.equals("Computer Wins")) {
                        losses++;
                    } else {
                        draws++;
                    }
                }

                double winPercentage =
                        wins * 100.0 / 5;

                System.out.println("\n========== SCOREBOARD ==========");
                System.out.println("Wins   : " + wins);
                System.out.println("Losses : " + losses);
                System.out.println("Draws  : " + draws);

                System.out.printf(
                        "Win %%  : %.1f%%%n",
                        winPercentage
                );

                break;


            case 2:

                System.out.print(
                        "Enter text: "
                );

                String text = sc.nextLine();

                boolean iterative =
                        isPalindromeIterative(text);

                boolean recursive =
                        isPalindromeRecursive(text);

                boolean array =
                        isPalindromeArrayReversal(text);

                System.out.println(
                        "Iterative      : " +
                        (iterative
                                ? "Palindrome"
                                : "Not Palindrome")
                );

                System.out.println(
                        "Recursive      : " +
                        (recursive
                                ? "Palindrome"
                                : "Not Palindrome")
                );

                System.out.println(
                        "Array Reversal : " +
                        (array
                                ? "Palindrome"
                                : "Not Palindrome")
                );

                break;


            case 3:

                double[] heights = {
                        1.75, 1.60, 1.80,
                        1.68, 1.72
                };

                double[] weights = {
                        70, 90, 85,
                        60, 95
                };

                printWellnessReport(
                        heights,
                        weights
                );

                break;


            case 4:

                System.out.print(
                        "Enter text: "
                );

                String input = sc.nextLine();

                char result =
                        findFirstNonRepeatingChar(input);

                if (result == '\0') {

                    System.out.println(
                            "No Non-Repeating Character Found"
                    );

                } else {

                    System.out.println(
                            "First Non-Repeating Character: '"
                            + result + "'"
                    );
                }

                break;


            case 5:

                System.out.print(
                        "Enter customer name: "
                );

                String name = sc.nextLine();

                String reversed =
                        reverseCustomerName(name);

                System.out.println(
                        "Original Name : " + name
                );

                System.out.println(
                        "Reversed Name : " + reversed
                );

                break;


            default:

                System.out.println(
                        "Invalid choice."
                );
        }

        sc.close();
    }
}