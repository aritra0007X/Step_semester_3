public class Week4ReinforcedConstructs {

    // =====================================================
    // M1. LIBRARY BOOK CATALOGUING
    // =====================================================

    static class LibraryBook {

        String title;
        String isbn;

        // Constructor with ISBN
        public LibraryBook(String title, String isbn) {

            this.title = title;

            if (isbn == null || isbn.isEmpty()) {
                this.isbn = "PENDING";
            } else {
                this.isbn = isbn;
            }
        }

        // Constructor without ISBN
        public LibraryBook(String title) {

            this(title, "PENDING");
        }

        public void printStatus() {

            System.out.println(
                    title
                            + " | "
                            + isbn
                            + " | Catalogued: true"
            );
        }
    }


    // =====================================================
    // M2. PAYROLL BATCH BONUS ROUND
    // =====================================================

    static class Employee {

        String empId;
        double salary;

        // Constructor uses this to resolve
        // field/parameter naming clash
        public Employee(String empId, double salary) {

            this.empId = empId;
            this.salary = salary;
        }

        // Parameter name deliberately matches field
        public void raiseSalary(double salary) {

            this.salary += salary;
        }

        public void printSalary() {

            System.out.println(
                    empId
                            + " | Final Salary: Rs "
                            + salary
            );
        }
    }


    // =====================================================
    // M3. LATE FEES — SKIP ON-TIME ACCOUNTS
    // =====================================================

    static class FeeAccount {

        String regNo;
        double totalFee;

        public FeeAccount(
                String regNo,
                double totalFee) {

            this.regNo = regNo;
            this.totalFee = totalFee;
        }

        // Locked method
        public final double calculateLateFee(
                int daysLate) {

            // 10% of total fee for late accounts
            return totalFee * 0.10;
        }

        // Locked method
        public final void printSummary(
                int daysLate) {

            if (daysLate <= 0) {

                System.out.println(
                        regNo
                                + " - On time, no late fee"
                );

                return;
            }

            double lateFee =
                    calculateLateFee(daysLate);

            System.out.println(
                    regNo
                            + " | Total Fee: Rs "
                            + totalFee
                            + " | Late Fee: Rs "
                            + lateFee
            );
        }
    }


    // =====================================================
    // M4. ONE-TIME COLLEGE SETUP
    // =====================================================

    static class SrmStudent {

        String name;

        static String collegeName;
        static String academicYear;

        // Static block executes exactly once
        static {

            collegeName =
                    "SRM Institute of Science and Technology";

            academicYear =
                    "2026-27";

            System.out.println(
                    "College info loaded"
            );
        }

        public SrmStudent(String name) {

            this.name = name;

            System.out.println(
                    "Student record created: "
                            + name
            );
        }
    }


    // =====================================================
    // M5. ACCOUNT BATCH PAYMENTS
    // =====================================================

    static class FeeAccountPayment {

        String accountName;

        public FeeAccountPayment(
                String accountName) {

            this.accountName = accountName;
        }
    }


    static class HostelFeeAccount
            extends FeeAccountPayment {

        public HostelFeeAccount(
                String accountName) {

            super(accountName);
        }
    }


    static class PlainFeeAccount
            extends FeeAccountPayment {

        public PlainFeeAccount(
                String accountName) {

            super(accountName);
        }
    }


    static void processPayment(
            FeeAccountPayment account,
            double amount) {

        if (account instanceof HostelFeeAccount) {

            System.out.println(
                    "Paid in two installments "
                            + "(hostel account)"
            );

        } else if (account instanceof PlainFeeAccount) {

            System.out.println(
                    "Paid in one go "
                            + "(day-scholar account)"
            );
        }
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {


        // =================================================
        // M1
        // =================================================

        System.out.println(
                "\n========== M1: LIBRARY BOOK CATALOGUING =========="
        );

        String[] titles = {
                "Clean Code",
                "Untitled Draft",
                "1984",
                "Notes"
        };

        String[] isbns = {
                "978-0132350884",
                "",
                "9780451524935",
                ""
        };

        for (int i = 0; i < titles.length; i++) {

            LibraryBook book;

            if (isbns[i].isEmpty()) {

                book = new LibraryBook(titles[i]);

            } else {

                book = new LibraryBook(
                        titles[i],
                        isbns[i]
                );
            }

            book.printStatus();
        }


        // =================================================
        // M2
        // =================================================

        System.out.println(
                "\n========== M2: PAYROLL BONUS =========="
        );

        Employee[] employees = {

                new Employee("E-101", 40000),
                new Employee("E-102", 55000),
                new Employee("E-103", 62000),
                new Employee("E-104", 48000)
        };

        double bonus = 5000;

        for (Employee employee : employees) {

            employee.raiseSalary(bonus);
        }

        for (Employee employee : employees) {

            employee.printSalary();
        }


        // =================================================
        // M3
        // =================================================

        System.out.println(
                "\n========== M3: LATE FEE ANALYSIS =========="
        );

        FeeAccount[] accounts = {

                new FeeAccount("RA001", 200000),
                new FeeAccount("RA002", 150000),
                new FeeAccount("RA003", 180000),
                new FeeAccount("RA004", 220000)
        };

        int[] daysLate = {
                10,
                0,
                -2,
                5
        };

        for (int i = 0; i < accounts.length; i++) {

            accounts[i].printSummary(
                    daysLate[i]
            );
        }


        // =================================================
        // M4
        // =================================================

        System.out.println(
                "\n========== M4: STATIC COLLEGE SETUP =========="
        );

        String[] names = {
                "Ravi",
                "Meera",
                "Karthik",
                "Divya",
                "Anitha"
        };

        SrmStudent[] students =
                new SrmStudent[names.length];

        for (int i = 0; i < names.length; i++) {

            students[i] =
                    new SrmStudent(names[i]);
        }


        // =================================================
        // M5
        // =================================================

        System.out.println(
                "\n========== M5: ACCOUNT BATCH PAYMENTS =========="
        );

        FeeAccountPayment[] paymentAccounts = {

                new HostelFeeAccount("Hostel-1"),
                new HostelFeeAccount("Hostel-2"),
                new PlainFeeAccount("Fee-1"),
                new PlainFeeAccount("Fee-2")
        };

        double paymentAmount = 60000;

        int hostelCount = 0;
        int dayScholarCount = 0;

        for (FeeAccountPayment account :
                paymentAccounts) {

            processPayment(
                    account,
                    paymentAmount
            );

            if (account instanceof HostelFeeAccount) {

                hostelCount++;

            } else if (
                    account instanceof PlainFeeAccount) {

                dayScholarCount++;
            }
        }

        System.out.println(
                "Hostel accounts processed: "
                        + hostelCount
                        + " | Day-scholar accounts processed: "
                        + dayScholarCount
        );
    }
}