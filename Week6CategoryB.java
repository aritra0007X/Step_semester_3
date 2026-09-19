public class Week6CategoryB {

    // =========================================================
    // PROBLEM 1 & COMMON BASE CLASS
    // Library Membership Foundation
    // =========================================================

    static class LibraryMember {

        protected String memberId;
        protected int borrowLimit;
        protected int booksBorrowed;

        // Problem 5
        public final String memberNumber;

        private static int membersEnrolled = 0;

        // Problem 3
        private int[] fineHistory = new int[10];
        private int fineCount = 0;

        // Problem 5
        private String[] borrowedGenres = new String[10];
        private int genreCount = 0;


        // -----------------------------------------------------
        // Problem 1 Constructor
        // -----------------------------------------------------

        public LibraryMember(String memberId, int borrowLimit) {

            if (memberId == null
                    || memberId.trim().isEmpty()
                    || memberId.length() < 4) {

                throw new IllegalArgumentException(
                        "Invalid member ID"
                );
            }

            if (borrowLimit <= 0) {

                throw new IllegalArgumentException(
                        "Borrow limit must be positive"
                );
            }

            this.memberId = memberId;
            this.borrowLimit = borrowLimit;

            // Assign unique member number
            membersEnrolled++;

            this.memberNumber =
                    "LIB-" + (100 + membersEnrolled);
        }


        // -----------------------------------------------------
        // Problem 5 Constructor
        // -----------------------------------------------------

        public LibraryMember(int borrowLimit) {

            if (borrowLimit <= 0) {

                throw new IllegalArgumentException(
                        "Borrow limit must be positive"
                );
            }

            this.memberId = "UNKNOWN";
            this.borrowLimit = borrowLimit;

            membersEnrolled++;

            this.memberNumber =
                    "LIB-" + (100 + membersEnrolled);
        }


        // -----------------------------------------------------
        // Problem 1
        // Borrow Book
        // -----------------------------------------------------

        void borrowBook() {

            if (booksBorrowed < borrowLimit) {

                booksBorrowed++;
            }
        }


        // -----------------------------------------------------
        // Problem 5
        // Overloaded borrowBook()
        // -----------------------------------------------------

        void borrowBook(String genre) {

            // Record genre before borrowing
            if (genreCount < borrowedGenres.length) {

                borrowedGenres[genreCount] = genre;
                genreCount++;
            }

            // Reuse no-argument method
            borrowBook();
        }


        // -----------------------------------------------------
        // Get borrowed book count
        // -----------------------------------------------------

        int getBooksBorrowed() {

            return booksBorrowed;
        }


        // -----------------------------------------------------
        // Problem 2
        // General displayInfo()
        // -----------------------------------------------------

        void displayInfo() {

            System.out.println(
                    "General Member | Books Borrowed: "
                            + booksBorrowed
            );
        }


        // -----------------------------------------------------
        // Problem 3
        // Fine logic
        // -----------------------------------------------------

        protected void chargeFine(int amount) {

            if (amount <= 0) {
                return;
            }

            if (fineCount < fineHistory.length) {

                fineHistory[fineCount] = amount;
                fineCount++;
            }
        }


        // -----------------------------------------------------
        // Problem 3
        // Defensive copy of fine history
        // -----------------------------------------------------

        int[] getFineHistory() {

            int[] result =
                    new int[fineCount];

            for (int i = 0; i < fineCount; i++) {

                result[i] = fineHistory[i];
            }

            return result;
        }


        // -----------------------------------------------------
        // Problem 3
        // Total fine
        // -----------------------------------------------------

        int getTotalFine() {

            int total = 0;

            for (int i = 0; i < fineCount; i++) {

                total += fineHistory[i];
            }

            return total;
        }


        // -----------------------------------------------------
        // Problem 5
        // Renewal Code Validation
        // -----------------------------------------------------

        static boolean isValidRenewalCode(String code) {

            // Check length first
            if (code == null || code.length() != 4) {

                return false;
            }


            // First character must be R
            if (code.charAt(0) != 'R') {

                return false;
            }


            // Second character must be digit
            if (!Character.isDigit(code.charAt(1))) {

                return false;
            }


            // Third character must be digit
            if (!Character.isDigit(code.charAt(2))) {

                return false;
            }


            // Fourth character must be uppercase
            if (!Character.isUpperCase(code.charAt(3))) {

                return false;
            }


            return true;
        }


        // -----------------------------------------------------
        // Problem 5
        // Number of enrolled members
        // -----------------------------------------------------

        static int getMembersEnrolled() {

            return membersEnrolled;
        }


        // -----------------------------------------------------
        // Problem 1
        // Batch Enrollment
        // -----------------------------------------------------

        static String enrollBatch(
                String[] memberIds,
                int borrowLimit) {

            int enrolled = 0;
            int rejected = 0;


            for (String id : memberIds) {

                try {

                    /*
                     * Do NOT validate the ID here.
                     *
                     * LibraryMember constructor is responsible
                     * for validation.
                     */
                    new LibraryMember(
                            id,
                            borrowLimit
                    );

                    enrolled++;

                } catch (IllegalArgumentException e) {

                    rejected++;
                }
            }


            return "Enrolled: "
                    + enrolled
                    + " | Rejected: "
                    + rejected;
        }
    }


    // =========================================================
    // STUDENT MEMBER
    // =========================================================

    static class StudentMember
            extends LibraryMember {

        private String course;


        // -----------------------------------------------------
        // Problem 1 Constructor
        // -----------------------------------------------------

        public StudentMember(
                String memberId,
                int borrowLimit,
                String course) {

            // Forward shared fields using super()
            super(memberId, borrowLimit);

            this.course = course;
        }


        // -----------------------------------------------------
        // Problem 2
        // Override displayInfo()
        // -----------------------------------------------------

        @Override
        void displayInfo() {

            System.out.println(
                    "Student Member | Course: "
                            + course
                            + " | Books Borrowed: "
                            + booksBorrowed
            );
        }


        // -----------------------------------------------------
        // Problem 3
        // Override chargeFine()
        // -----------------------------------------------------

        @Override
        protected void chargeFine(int amount) {

            /*
             * Student receives 50% discount.
             *
             * The actual fine recording logic remains
             * in LibraryMember.
             */
            super.chargeFine(amount / 2);
        }


        String getCourse() {

            return course;
        }
    }


    // =========================================================
    // HONORS STUDENT MEMBER
    // Multilevel inheritance
    // =========================================================

    static class HonorsStudentMember
            extends StudentMember {

        private int bonusLimit;


        public HonorsStudentMember(
                String memberId,
                int borrowLimit,
                String course,
                int bonusLimit) {

            super(
                    memberId,
                    borrowLimit,
                    course
            );

            this.bonusLimit = bonusLimit;
        }


        @Override
        void displayInfo() {

            System.out.println(
                    "Honors Student Member | Course: "
                            + getCourse()
                            + " | Bonus Limit: "
                            + bonusLimit
                            + " | Books Borrowed: "
                            + booksBorrowed
            );
        }


        // Honors students can use bonus borrowing limit
        @Override
        void borrowBook() {

            if (booksBorrowed
                    < borrowLimit + bonusLimit) {

                booksBorrowed++;
            }
        }
    }


    // =========================================================
    // FACULTY MEMBER
    // Hierarchical inheritance
    // =========================================================

    static class FacultyMember
            extends LibraryMember {

        private String department;


        // Constructor for Problem 2
        public FacultyMember(
                String memberId,
                int borrowLimit,
                String department) {

            super(
                    memberId,
                    borrowLimit
            );

            this.department = department;
        }


        // Constructor for Problem 5
        public FacultyMember(
                int borrowLimit,
                String department) {

            super(borrowLimit);

            this.department = department;
        }


        @Override
        void displayInfo() {

            System.out.println(
                    "Faculty Member | Department: "
                            + department
                            + " | Books Borrowed: "
                            + booksBorrowed
            );
        }
    }


    // =========================================================
    // PROBLEM 2
    // classifyGeneration()
    // =========================================================

    static String classifyGeneration(
            LibraryMember member) {

        /*
         * instanceof only.
         * No manual type field is used.
         */

        if (member instanceof HonorsStudentMember) {

            return "Multilevel descendant (3 generations deep)";
        }


        if (member instanceof StudentMember) {

            return "Student branch";
        }


        if (member instanceof FacultyMember) {

            return "Hierarchical sibling (independent branch)";
        }


        return "Base generation";
    }


    // =========================================================
    // PROBLEM 2
    // getTotalBooksBorrowed()
    // =========================================================

    static int getTotalBooksBorrowed(
            LibraryMember[] members) {

        int total = 0;


        for (LibraryMember member : members) {

            if (member != null) {

                /*
                 * Polymorphism:
                 * each object's own getBooksBorrowed()
                 * is used.
                 */
                total += member.getBooksBorrowed();
            }
        }


        return total;
    }


    // =========================================================
    // PROBLEM 4
    // Weekly Circulation Report
    // =========================================================

    static String batchPrint(
            LibraryMember[] members) {

        StringBuilder report =
                new StringBuilder();


        for (LibraryMember member : members) {

            /*
             * Call displayInfo() polymorphically.
             *
             * We don't use instanceof to decide what
             * main information should be printed.
             */
            String type;

            if (member instanceof StudentMember) {

                type = "Student";

            } else {

                type = "General";
            }


            if (type.equals("Student")) {

                StudentMember student =
                        (StudentMember) member;

                report.append(
                        "Student | Course: "
                                + student.getCourse()
                                + " | Books: "
                                + student.getBooksBorrowed()
                                + " [Course via downcast: "
                                + student.getCourse()
                                + "] | "
                );

            } else {

                report.append(
                        "General | Books: "
                                + member.getBooksBorrowed()
                                + " | "
                );
            }
        }


        return report.toString();
    }


    // =========================================================
    // PROBLEM 5
    // Nightly Circulation Audit
    // =========================================================

    static String processNightlyAudit(
            LibraryMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int faculty = 0;
        int regular = 0;


        for (LibraryMember member : members) {

            // Handle null safely
            if (member == null) {

                nullSkipped++;

                continue;
            }


            processed++;


            if (member instanceof FacultyMember) {

                faculty++;

            } else {

                regular++;
            }
        }


        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + faculty
                + " faculty | "
                + regular
                + " regular";
    }


    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {


        // =====================================================
        // PROBLEM 1
        // =====================================================

        System.out.println(
                "===== PROBLEM 1 ====="
        );


        // Invalid member ID
        try {

            new LibraryMember(
                    "LB1",
                    3
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "construction rejected"
            );
        }


        // Student member
        StudentMember s =
                new StudentMember(
                        "STU10",
                        3,
                        "CSE"
                );


        s.borrowBook();
        s.borrowBook();


        System.out.println(
                "Books borrowed: "
                        + s.getBooksBorrowed()
        );


        // Batch enrollment
        String[] memberIds = {

                "STU1",
                "LB1",
                "STU2",
                " ",
                "STU3"
        };


        System.out.println(
                LibraryMember.enrollBatch(
                        memberIds,
                        3
                )
        );


        // =====================================================
        // PROBLEM 2
        // =====================================================

        System.out.println(
                "\n===== PROBLEM 2 ====="
        );


        LibraryMember general =
                new LibraryMember(
                        "STU1",
                        3
                );


        StudentMember student =
                new StudentMember(
                        "STU2",
                        3,
                        "CSE"
                );


        HonorsStudentMember honors =
                new HonorsStudentMember(
                        "STU3",
                        3,
                        "ECE",
                        2
                );


        FacultyMember faculty =
                new FacultyMember(
                        "STU4",
                        5,
                        "Physics"
                );


        general.displayInfo();

        student.displayInfo();

        honors.displayInfo();

        faculty.displayInfo();


        System.out.println(
                "Honors: "
                        + classifyGeneration(honors)
        );


        System.out.println(
                "Faculty: "
                        + classifyGeneration(faculty)
        );


        // Borrow books
        student.borrowBook();
        student.borrowBook();


        honors.borrowBook();


        faculty.borrowBook();
        faculty.borrowBook();
        faculty.borrowBook();


        LibraryMember[] mixedMembers = {

                student,
                honors,
                faculty
        };


        System.out.println(
                "Total books borrowed: "
                        + getTotalBooksBorrowed(
                                mixedMembers
                        )
        );


        // =====================================================
        // PROBLEM 3
        // =====================================================

        System.out.println(
                "\n===== PROBLEM 3 ====="
        );


        StudentMember fineStudent =
                new StudentMember(
                        "STU5",
                        3,
                        "CSE"
                );


        fineStudent.chargeFine(100);


        System.out.println(
                "Total fine: "
                        + fineStudent.getTotalFine()
        );


        int[] history =
                fineStudent.getFineHistory();


        // Try modifying returned array
        history[0] = 999;


        System.out.println(
                "Fine history: "
                        + java.util.Arrays.toString(
                                fineStudent.getFineHistory()
                        )
        );


        // =====================================================
        // PROBLEM 4
        // =====================================================

        System.out.println(
                "\n===== PROBLEM 4 ====="
        );


        LibraryMember[] reportMembers = {

                new LibraryMember(
                        "LB5",
                        3
                ),

                new StudentMember(
                        "STU6",
                        3,
                        "ECE"
                )
        };


        System.out.println(
                batchPrint(reportMembers)
        );


        // =====================================================
        // PROBLEM 5
        // =====================================================

        System.out.println(
                "\n===== PROBLEM 5 ====="
        );


        LibraryMember m1 =
                new LibraryMember(3);


        System.out.println(
                "Member Number: "
                        + m1.memberNumber
        );


        System.out.println(
                "Members Enrolled: "
                        + LibraryMember.getMembersEnrolled()
        );


        // Renewal code
        System.out.println(
                "R12A: "
                        + LibraryMember.isValidRenewalCode(
                                "R12A"
                        )
        );


        System.out.println(
                "R1A: "
                        + LibraryMember.isValidRenewalCode(
                                "R1A"
                        )
        );


        System.out.println(
                "X12A: "
                        + LibraryMember.isValidRenewalCode(
                                "X12A"
                        )
        );


        // Overloaded borrowBook()
        m1.borrowBook();

        m1.borrowBook("Fiction");


        System.out.println(
                "Books borrowed: "
                        + m1.getBooksBorrowed()
        );


        // Nightly audit
        LibraryMember[] auditMembers = {

                new FacultyMember(
                        5,
                        "Physics"
                ),

                null,

                new LibraryMember(3)
        };


        System.out.println(
                processNightlyAudit(
                        auditMembers
                )
        );
    }
}