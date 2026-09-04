public class Week3HomeworkM1M4 {

    // ==========================================
    // M1. LIBRARY INVENTORY
    // ==========================================

    static class BookInventory {

        String title;
        String author;
        int copiesAvailable;

        BookInventory(
                String title,
                String author,
                int copiesAvailable) {

            this.title = title;
            this.author = author;
            this.copiesAvailable = copiesAvailable;
        }

        void printEntry() {

            System.out.println(
                    title
                            + " by "
                            + author
                            + " - "
                            + copiesAvailable
                            + " copies available"
            );
        }
    }


    // ==========================================
    // M2. ENCAPSULATED PAYROLL ACCOUNT
    // ==========================================

    static class PayrollAccount {

        private double basicSalary;
        private double bonus;

        public PayrollAccount(double openingBasicSalary) {

            if (openingBasicSalary < 0) {

                System.out.println(
                        "Warning: Negative basic salary. "
                                + "Starting at Rs 0.0"
                );

                basicSalary = 0;

            } else {

                basicSalary = openingBasicSalary;
            }

            bonus = 0;
        }

        public void creditBonus(double amount) {

            if (amount <= 0) {

                System.out.println(
                        "Bonus rejected: amount must be positive"
                );

                return;
            }

            bonus += amount;

            System.out.println(
                    "Bonus credited: Rs "
                            + amount
            );
        }

        public void deductTax(double percent) {

            if (percent < 0 || percent > 100) {

                System.out.println(
                        "Tax deduction rejected: "
                                + "percent must be between 0 and 100"
                );

                return;
            }

            basicSalary =
                    basicSalary
                            - (basicSalary * percent / 100);

            System.out.println(
                    "Tax deducted: "
                            + percent
                            + "%"
            );
        }

        public double getNetSalary() {

            return basicSalary + bonus;
        }
    }


    // ==========================================
    // M3. OVERLOADED CONSTRUCTORS FOR EMPLOYEE
    // ==========================================

    static class Employee {

        String empId;
        String empName;
        double salary;
        boolean isIntern;

        // Permanent employee
        public Employee(
                String empId,
                String empName,
                double salary) {

            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
            this.isIntern = false;
        }

        // Intern
        public Employee(
                String empId,
                String empName) {

            this(
                    empId,
                    empName,
                    0
            );

            this.isIntern = true;
        }

        public void printProfile() {

            System.out.println(
                    empId
                            + " | "
                            + empName
                            + " | Rs "
                            + salary
                            + " | Intern: "
                            + isIntern
            );
        }
    }


    // ==========================================
    // M4. REFERENCE COPIES
    // ==========================================

    static class HallTicket {

        String studentName;
        int seatNumber;

        HallTicket(
                String studentName,
                int seatNumber) {

            this.studentName = studentName;
            this.seatNumber = seatNumber;
        }
    }


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        // ======================================
        // M1
        // ======================================

        System.out.println(
                "========== M1: LIBRARY INVENTORY =========="
        );

        BookInventory[] books = {

                new BookInventory(
                        "Clean Code",
                        "Robert C. Martin",
                        3
                ),

                new BookInventory(
                        "Effective Java",
                        "Joshua Bloch",
                        5
                ),

                new BookInventory(
                        "Refactoring",
                        "Martin Fowler",
                        0
                ),

                new BookInventory(
                        "Design Patterns",
                        "GoF",
                        2
                )
        };

        for (BookInventory book : books) {
            book.printEntry();
        }


        // ======================================
        // M2
        // ======================================

        System.out.println(
                "\n========== M2: PAYROLL ACCOUNT =========="
        );

        PayrollAccount payroll =
                new PayrollAccount(50000);

        payroll.creditBonus(5000);

        payroll.deductTax(10);

        System.out.println(
                "Net salary: Rs "
                        + payroll.getNetSalary()
        );


        // ======================================
        // M3
        // ======================================

        System.out.println(
                "\n========== M3: EMPLOYEE =========="
        );

        Employee permanentEmployee =
                new Employee(
                        "E-101",
                        "Divya",
                        65000
                );

        Employee internEmployee =
                new Employee(
                        "E-102",
                        "Arjun"
                );

        permanentEmployee.printProfile();
        internEmployee.printProfile();


        // ======================================
        // M4
        // ======================================

        System.out.println(
                "\n========== M4: REFERENCE COPYING =========="
        );

        HallTicket priya =
                new HallTicket(
                        "Priya",
                        0
                );

        // Both variables refer to the SAME object
        HallTicket copy = priya;

        copy.seatNumber = 45;

        // Separate object
        HallTicket separate =
                new HallTicket(
                        "Priya",
                        45
                );

        System.out.println(
                "Priya's seatNumber "
                        + "(via first variable): "
                        + priya.seatNumber
        );

        System.out.println(
                "copy == priya: "
                        + (copy == priya)
        );

        System.out.println(
                "separate == priya: "
                        + (separate == priya)
        );
    }
}
