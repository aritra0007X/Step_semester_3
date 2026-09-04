import java.util.Scanner;

public class Day2LiveCoding {

    // ==========================================
    // 1. VOWEL & CONSONANT COUNTER
    // ==========================================

    static void countVowelsAndConsonants(String text) {

        int vowels = 0;
        int consonants = 0;

        for (int i = 0; i < text.length(); i++) {

            char ch = Character.toLowerCase(text.charAt(i));

            if (ch == 'a' || ch == 'e' || ch == 'i'
                    || ch == 'o' || ch == 'u') {

                vowels++;

            } else if (ch != ' ') {

                consonants++;
            }
        }

        System.out.println(
                "Vowels: " + vowels +
                " | Consonants: " + consonants
        );
    }


    // ==========================================
    // 2. CSV STUDENT RECORD PARSER
    // ==========================================

    static void parseStudentRecord(String csvLine) {

        String[] fields = csvLine.split(",");

        if (fields.length != 3) {

            System.out.println("Invalid Record");
            return;
        }

        String name = fields[0].trim();
        String rollNumber = fields[1].trim();
        String department = fields[2].trim();

        System.out.println(
                "Name: " + name +
                " | Roll No: " + rollNumber +
                " | Dept: " + department
        );
    }


    // ==========================================
    // 3. FILE EXTENSION VALIDATOR
    // ==========================================

    static String validateFileExtension(String filename) {

        int dotPosition = filename.lastIndexOf('.');

        // No dot or dot is the last character
        if (dotPosition == -1 ||
                dotPosition == filename.length() - 1) {

            return "Rejected — invalid file type";
        }

        String extension =
                filename.substring(dotPosition + 1);

        if (extension.equalsIgnoreCase("pdf") ||
                extension.equalsIgnoreCase("docx") ||
                extension.equalsIgnoreCase("zip")) {

            return "Accepted";
        }

        return "Rejected — invalid file type";
    }


    // ==========================================
    // 4. MASKED PHONE NUMBER FORMATTER
    // ==========================================

    static String maskPhoneNumber(String phone) {

        if (phone.length() != 10) {
            return "Invalid phone number";
        }

        // Check every character is a digit
        for (int i = 0; i < phone.length(); i++) {

            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }

        String lastFour =
                phone.substring(phone.length() - 4);

        StringBuilder masked =
                new StringBuilder("XXXXXX");

        masked.insert(6, "-");

        masked.append(lastFour);

        return masked.toString();
    }


    // ==========================================
    // 5. BANK TRANSACTION REFERENCE
    // ==========================================

    static String normalizeReference(String raw) {

        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference;
        }

        String bankCode =
                reference.substring(0, 3).toUpperCase();

        String remaining =
                reference.substring(3);

        return bankCode + remaining;
    }


    static String validateAndFormat(String reference) {

        // Check length
        if (reference.length() != 14) {

            return "Invalid: wrong length";
        }

        // Check first 3 characters are letters
        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(reference.charAt(i))) {

                return "Invalid: bank code must be 3 letters";
            }
        }

        // Check remaining 11 characters are digits
        for (int i = 3; i < reference.length(); i++) {

            if (!Character.isDigit(reference.charAt(i))) {

                return "Invalid: body must contain only digits";
            }
        }

        String bankCode =
                reference.substring(0, 3);

        String date =
                reference.substring(3, 9);

        String sequence =
                reference.substring(9, 14);

        // ddMMyy → dd/MM/yy
        String formattedDate =
                date.substring(0, 2) + "/"
                + date.substring(2, 4) + "/"
                + date.substring(4, 6);

        StringBuilder result =
                new StringBuilder();

        result.append("[")
                .append(bankCode)
                .append("] DATE: ")
                .append(formattedDate)
                .append(" | SEQ: ")
                .append(sequence);

        return result.toString();
    }


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("       DAY 2 LIVE CODING");
        System.out.println("======================================");

        System.out.println("1. Vowel & Consonant Counter");
        System.out.println("2. CSV Student Record Parser");
        System.out.println("3. File Extension Validator");
        System.out.println("4. Masked Phone Number Formatter");
        System.out.println("5. Bank Transaction Reference");

        System.out.print("\nChoose a problem (1-5): ");

        int choice = sc.nextInt();
        sc.nextLine();


        switch (choice) {

            // ==================================
            // PROBLEM 1
            // ==================================

            case 1:

                System.out.print("Enter text: ");

                String text = sc.nextLine();

                countVowelsAndConsonants(text);

                break;


            // ==================================
            // PROBLEM 2
            // ==================================

            case 2:

                System.out.print(
                        "Enter CSV student record: "
                );

                String csvLine = sc.nextLine();

                parseStudentRecord(csvLine);

                break;


            // ==================================
            // PROBLEM 3
            // ==================================

            case 3:

                System.out.print(
                        "Enter filename: "
                );

                String filename = sc.nextLine();

                System.out.println(
                        validateFileExtension(filename)
                );

                break;


            // ==================================
            // PROBLEM 4
            // ==================================

            case 4:

                System.out.print(
                        "Enter phone number: "
                );

                String phone = sc.nextLine();

                System.out.println(
                        maskPhoneNumber(phone)
                );

                break;


            // ==================================
            // PROBLEM 5
            // ==================================

            case 5:

                System.out.print(
                        "Enter transaction reference: "
                );

                String rawReference = sc.nextLine();

                String normalized =
                        normalizeReference(rawReference);

                System.out.println(
                        validateAndFormat(normalized)
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