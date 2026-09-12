import java.util.Scanner;

public class Week3OOP {

    // ==========================================
    // M1. FROM PARALLEL ARRAYS TO A CLASS
    // ==========================================

    static class PlacementRecord {

        String studentName;
        String company;
        double packageLpa;

        PlacementRecord(
                String studentName,
                String company,
                double packageLpa) {

            this.studentName = studentName;
            this.company = company;
            this.packageLpa = packageLpa;
        }

        void printRecord() {

            System.out.println(
                    studentName
                            + " -> "
                            + company
                            + " @ "
                            + packageLpa
                            + " LPA"
            );
        }
    }


    // ==========================================
    // M2. ENCAPSULATED MESS-CARD WALLET
    // ==========================================

    static class MessWallet {

        private double balance;

        public MessWallet(double openingBalance) {

            if (openingBalance < 0) {

                System.out.println(
                        "Warning: Negative opening balance. "
                                + "Starting with 0."
                );

                balance = 0;

            } else {

                balance = openingBalance;
            }
        }

        public void topUp(double amount) {

            if (amount <= 0) {

                System.out.println(
                        "Top-up rejected: amount must be positive"
                );

                return;
            }

            balance += amount;

            System.out.println(
                    "Balance after top-up: "
                            + balance
            );
        }

        public void deduct(double amount) {

            if (amount <= 0) {

                System.out.println(
                        "Deduction rejected: amount must be positive"
                );

                return;
            }

            if (amount > balance) {

                System.out.println(
                        "Deduct rejected: insufficient balance"
                );

                return;
            }

            balance -= amount;

            System.out.println(
                    "Balance after deduction: "
                            + balance
            );
        }

        public double getBalance() {

            return balance;
        }
    }


    // ==========================================
    // M3. OVERLOADED CONSTRUCTORS FOR A COURSE
    // ==========================================

    static class Course {

        String code;
        String title;
        int credits;
        int labCredits;

        public Course(
                String code,
                String title,
                int credits,
                int labCredits) {

            this.code = code;
            this.title = title;
            this.credits = credits;
            this.labCredits = labCredits;
        }

        public Course(
                String code,
                String title,
                int credits) {

            this(
                    code,
                    title,
                    credits,
                    0
            );
        }

        public int totalCredits() {

            return credits + labCredits;
        }
    }


    // ==========================================
    // M4. REFERENCE COPIES AND SHARED ID CARD
    // ==========================================

    static class IdCard {

        String name;
        int booksIssued;

        IdCard(
                String name,
                int booksIssued) {

            this.name = name;
            this.booksIssued = booksIssued;
        }
    }


    // ==========================================
    // M5. INSTANCE VS STATIC
    // ==========================================

    static class Student {

        String name;
        int attendance;

        static String collegeName =
                "SRM Institute of Science and Technology";

        static int studentCount = 0;

        Student(
                String name,
                int attendance) {

            this.name = name;
            this.attendance = attendance;

            studentCount++;
        }

        static void printCollegeInfo() {

            System.out.println(collegeName);
            System.out.println(
                    "Students created: "
                            + studentCount
            );
        }
    }


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("       WEEK 3 - OOP PRACTICE");
        System.out.println("======================================");

        System.out.println("1. Placement Record");
        System.out.println("2. Mess-Card Wallet");
        System.out.println("3. Course Constructors");
        System.out.println("4. Reference Copies");
        System.out.println("5. Instance vs Static");

        System.out.print("\nChoose a problem (1-5): ");

        int choice = sc.nextInt();
        sc.nextLine();


        switch (choice) {

            // ==================================
            // M1
            // ==================================

            case 1:

                PlacementRecord[] records = {

                        new PlacementRecord(
                                "Ravi",
                                "TCS",
                                4.5
                        ),

                        new PlacementRecord(
                                "Anitha",
                                "Zoho",
                                6.2
                        ),

                        new PlacementRecord(
                                "Karthik",
                                "Infosys",
                                4.0
                        )
                };

                System.out.println(
                        "\n========== PLACEMENT RECORDS =========="
                );

                for (PlacementRecord record : records) {

                    record.printRecord();
                }

                break;


            // ==================================
            // M2
            // ==================================

            case 2:

                System.out.println(
                        "\n========== MESS WALLET =========="
                );

                MessWallet wallet =
                        new MessWallet(500);

                wallet.topUp(200);

                wallet.deduct(1000);

                System.out.println(
                        "Final balance: "
                                + wallet.getBalance()
                );

                break;


            // ==================================
            // M3
            // ==================================

            case 3:

                System.out.println(
                        "\n========== COURSE DETAILS =========="
                );

                Course theoryCourse =
                        new Course(
                                "21CSC201J",
                                "Data Structures",
                                4
                        );

                Course labCourse =
                        new Course(
                                "21CSC205L",
                                "DSA Lab",
                                3,
                                1
                        );

                System.out.println(
                        theoryCourse.code
                                + " total credits: "
                                + theoryCourse.totalCredits()
                );

                System.out.println(
                        labCourse.code
                                + " total credits: "
                                + labCourse.totalCredits()
                );

                break;


            // ==================================
            // M4
            // ==================================

            case 4:

                System.out.println(
                        "\n========== REFERENCE COPYING =========="
                );

                IdCard ravi =
                        new IdCard(
                                "Ravi",
                                0
                        );

                // Both variables point to
                // the SAME object
                IdCard duplicate = ravi;

                duplicate.booksIssued = 3;

                // Separate object
                IdCard separate =
                        new IdCard(
                                "Ravi",
                                3
                        );

                System.out.println(
                        "Ravi's booksIssued "
                                + "(via first variable): "
                                + ravi.booksIssued
                );

                System.out.println(
                        "duplicate == ravi: "
                                + (duplicate == ravi)
                );

                System.out.println(
                        "separate == ravi: "
                                + (separate == ravi)
                );

                break;


            // ==================================
            // M5
            // ==================================

            case 5:

                System.out.println(
                        "\n========== INSTANCE VS STATIC =========="
                );

                Student student1 =
                        new Student(
                                "Ravi",
                                90
                        );

                Student student2 =
                        new Student(
                                "Anitha",
                                95
                        );

                // Static method called using
                // the CLASS NAME
                Student.printCollegeInfo();

                break;


            default:

                System.out.println(
                        "Invalid choice."
                );
        }

        sc.close();
    }
}