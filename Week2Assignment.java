import java.util.*;

public class Week2Assignment {

    // ==========================================
    // 1. ATM PIN LENGTH VALIDATOR
    // ==========================================

    static void checkPinLength(String pin) {

        if (pin.length() != 4) {
            System.out.println(
                    "Invalid PIN — must be exactly 4 digits."
            );
        } else {
            System.out.println("PIN length OK.");
        }
    }


    // ==========================================
    // 2. WORD REVERSAL ENCODER
    // ==========================================

    static String reverseEachWord(String sentence) {

        String[] words = sentence.split(" ");

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {

            StringBuilder reversedWord =
                    new StringBuilder(words[i]);

            reversedWord.reverse();

            result.append(reversedWord);

            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }


    // ==========================================
    // 3. PRODUCT INVENTORY CSV PARSER
    // ==========================================

    static void parseInventoryRecord(String csvLine) {

        String[] fields = csvLine.split(",");

        if (fields.length != 3) {

            System.out.println("Invalid Record");
            return;
        }

        String product = fields[0].trim();
        String sku = fields[1].trim();
        String quantity = fields[2].trim();

        System.out.println(
                "Product: " + product +
                " | SKU: " + sku +
                " | Qty: " + quantity
        );
    }


    // ==========================================
    // 4. LIBRARY ISBN NORMALIZER & VALIDATOR
    // ==========================================

    static String normalizeCode(String raw) {

        String code = raw.trim();

        // Avoid substring error for very short input
        if (code.length() < 3) {
            return code;
        }

        String publisherCode =
                code.substring(0, 3).toUpperCase();

        String remaining =
                code.substring(3);

        return publisherCode + remaining;
    }


    static String validateAndFormat(String code) {

        // Check total length
        if (code.length() != 13) {

            return "Invalid: wrong length";
        }

        // Check first 3 characters
        // are letters
        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(code.charAt(i))) {

                return "Invalid: publisher code must be 3 letters";
            }
        }

        // Check remaining 10 characters
        // are digits
        for (int i = 3; i < code.length(); i++) {

            if (!Character.isDigit(code.charAt(i))) {

                return "Invalid: body must contain only digits";
            }
        }

        String publisherCode =
                code.substring(0, 3);

        String year =
                code.substring(3, 7);

        String catalog =
                code.substring(7, 13);

        StringBuilder result =
                new StringBuilder();

        result.append("[")
                .append(publisherCode)
                .append("] YEAR: ")
                .append(year)
                .append(" | CATALOG: ")
                .append(catalog);

        return result.toString();
    }


    // ==========================================
    // 5. STOP-WORD-FILTERED WORD FREQUENCY
    // ==========================================

    static void printFilteredWordFrequency(
            String feedback) {

        // Stop words
        Set<String> stopWords = new HashSet<>(
                Arrays.asList(
                        "the",
                        "was",
                        "and",
                        "a",
                        "is",
                        "of",
                        "in"
                )
        );

        // Convert to lowercase
        String cleanedText =
                feedback.toLowerCase();

        // Remove punctuation
        cleanedText =
                cleanedText.replace(".", "");

        cleanedText =
                cleanedText.replace(",", "");

        // Split using whitespace
        String[] words =
                cleanedText.trim().split("\\s+");

        // Frequency map
        HashMap<String, Integer> frequency =
                new HashMap<>();

        for (String word : words) {

            if (stopWords.contains(word)) {
                continue;
            }

            frequency.put(
                    word,
                    frequency.getOrDefault(word, 0) + 1
            );
        }

        // Convert entries to list
        List<Map.Entry<String, Integer>> entries =
                new ArrayList<>(frequency.entrySet());

        // Sort by frequency descending
        entries.sort(
                (a, b) ->
                        b.getValue()
                                .compareTo(a.getValue())
        );

        System.out.println(
                "\n========== WORD FREQUENCY REPORT =========="
        );

        for (Map.Entry<String, Integer> entry : entries) {

            System.out.println(
                    entry.getKey()
                            + ": "
                            + entry.getValue()
            );
        }
    }


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("       WEEK 2 ASSIGNMENT");
        System.out.println("======================================");

        System.out.println("1. ATM PIN Length Validator");
        System.out.println("2. Word Reversal Encoder");
        System.out.println("3. Product Inventory CSV Parser");
        System.out.println("4. Library ISBN Normalizer & Validator");
        System.out.println("5. Stop-Word Word Frequency Report");

        System.out.print("\nChoose a problem (1-5): ");

        int choice = sc.nextInt();
        sc.nextLine();


        switch (choice) {

            // ==================================
            // PROBLEM 1
            // ==================================

            case 1:

                System.out.print("Enter PIN: ");

                String pin = sc.nextLine();

                checkPinLength(pin);

                break;


            // ==================================
            // PROBLEM 2
            // ==================================

            case 2:

                System.out.print(
                        "Enter sentence: "
                );

                String sentence = sc.nextLine();

                String reversed =
                        reverseEachWord(sentence);

                System.out.println(
                        "Reversed Sentence: "
                                + reversed
                );

                break;


            // ==================================
            // PROBLEM 3
            // ==================================

            case 3:

                System.out.print(
                        "Enter inventory record: "
                );

                String csvLine = sc.nextLine();

                parseInventoryRecord(csvLine);

                break;


            // ==================================
            // PROBLEM 4
            // ==================================

            case 4:

                System.out.print(
                        "Enter library code: "
                );

                String rawCode = sc.nextLine();

                String normalizedCode =
                        normalizeCode(rawCode);

                System.out.println(
                        validateAndFormat(normalizedCode)
                );

                break;


            // ==================================
            // PROBLEM 5
            // ==================================

            case 5:

                System.out.println(
                        "Enter feedback paragraph:"
                );

                String feedback =
                        sc.nextLine();

                printFilteredWordFrequency(
                        feedback
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