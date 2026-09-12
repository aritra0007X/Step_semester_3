public class Week5IndividualHomework {

    // =========================================================
    // PROBLEM 1
    // Membership Field Reach Checker
    // =========================================================

    static class AccessChecker {

        static String classifyAccess(String fieldModifier,
                                     String accessorContext) {

            switch (accessorContext) {

                case "SAME_CLASS":
                    // All four modifiers are accessible
                    // inside the same class.
                    return "ALLOWED";

                case "SAME_PACKAGE":
                    // private is accessible only inside
                    // the declaring class.
                    if (fieldModifier.equals("private")) {
                        return "DENIED";
                    }

                    // default, protected and public
                    // are accessible in the same package.
                    return "ALLOWED";

                case "DIFFERENT_PACKAGE":
                    // Only public is directly accessible
                    // from another package.
                    if (fieldModifier.equals("public")) {
                        return "ALLOWED";
                    }

                    return "DENIED";

                case "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE":
                    // In a subclass from another package,
                    // protected and public are accessible.
                    if (fieldModifier.equals("protected")
                            || fieldModifier.equals("public")) {
                        return "ALLOWED";
                    }

                    return "DENIED";

                case "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE":
                    // Only public is allowed in this context.
                    if (fieldModifier.equals("public")) {
                        return "ALLOWED";
                    }

                    return "DENIED";

                default:
                    return "DENIED";
            }
        }


        static String summarizeByModifier(String[][] attempts) {

            int privateAllowed = 0;
            int privateDenied = 0;

            int defaultAllowed = 0;
            int defaultDenied = 0;

            int protectedAllowed = 0;
            int protectedDenied = 0;

            int publicAllowed = 0;
            int publicDenied = 0;


            for (String[] attempt : attempts) {

                String modifier = attempt[0];
                String context = attempt[1];

                String result =
                        classifyAccess(modifier, context);


                if (modifier.equals("private")) {

                    if (result.equals("ALLOWED")) {
                        privateAllowed++;
                    } else {
                        privateDenied++;
                    }

                } else if (modifier.equals("default")) {

                    if (result.equals("ALLOWED")) {
                        defaultAllowed++;
                    } else {
                        defaultDenied++;
                    }

                } else if (modifier.equals("protected")) {

                    if (result.equals("ALLOWED")) {
                        protectedAllowed++;
                    } else {
                        protectedDenied++;
                    }

                } else if (modifier.equals("public")) {

                    if (result.equals("ALLOWED")) {
                        publicAllowed++;
                    } else {
                        publicDenied++;
                    }
                }
            }


            return "private: "
                    + privateAllowed
                    + " allowed / "
                    + privateDenied
                    + " denied | "

                    + "default: "
                    + defaultAllowed
                    + " allowed / "
                    + defaultDenied
                    + " denied | "

                    + "protected: "
                    + protectedAllowed
                    + " allowed / "
                    + protectedDenied
                    + " denied | "

                    + "public: "
                    + publicAllowed
                    + " allowed / "
                    + publicDenied
                    + " denied";
        }


        static String firstDeniedAttempt(String[][] attempts) {

            for (int i = 0; i < attempts.length; i++) {

                String modifier = attempts[i][0];
                String context = attempts[i][1];

                String result =
                        classifyAccess(modifier, context);


                if (result.equals("DENIED")) {

                    return modifier
                            + " via "
                            + context
                            + " (attempt #"
                            + (i + 1)
                            + ")";
                }
            }

            return "None Denied";
        }
    }


    // =========================================================
    // LIBRARY MEMBER
    //
    // Problem 1 fields + Problem 4 JavaBean properties
    // =========================================================

    static class LibraryMember {

        // Problem 1 fields

        private String membershipPin;

        String branchCode;

        protected double finesOwed;

        public String displayName;


        // Problem 4 fields

        private String membershipId;

        private String name;

        private boolean premiumMember;

        private String securityAnswer;


        // Write-once tracking
        private boolean membershipIdSet = false;


        // -----------------------------------------------------
        // Problem 4: public no-argument constructor
        // -----------------------------------------------------

        public LibraryMember() {
        }


        // -----------------------------------------------------
        // Problem 4: membershipId
        // -----------------------------------------------------

        String getMembershipId() {
            return membershipId;
        }


        void setMembershipId(String id) {

            // Only the first call changes the value.
            if (!membershipIdSet) {

                membershipId = id;

                membershipIdSet = true;
            }
        }


        // -----------------------------------------------------
        // Problem 4: name
        // -----------------------------------------------------

        String getName() {
            return name;
        }


        void setName(String name) {

            this.name = name;
        }


        // -----------------------------------------------------
        // Problem 4: premiumMember
        // -----------------------------------------------------

        boolean isPremiumMember() {

            return premiumMember;
        }


        void setPremiumMember(boolean premium) {

            this.premiumMember = premium;
        }


        // -----------------------------------------------------
        // Problem 4: securityAnswer
        // -----------------------------------------------------

        void setSecurityAnswer(String answer) {

            /*
             * Store only a one-way transformed value.
             *
             * No getter is provided.
             */

            this.securityAnswer =
                    hashSecurityAnswer(answer);
        }


        private String hashSecurityAnswer(String answer) {

            try {

                java.security.MessageDigest md =
                        java.security.MessageDigest.getInstance(
                                "SHA-256"
                        );

                byte[] hash =
                        md.digest(
                                answer.getBytes(
                                        java.nio.charset.StandardCharsets.UTF_8
                                )
                        );


                StringBuilder result =
                        new StringBuilder();


                for (byte b : hash) {

                    result.append(
                            String.format(
                                    "%02x",
                                    b
                            )
                    );
                }


                return result.toString();

            } catch (java.security.NoSuchAlgorithmException e) {

                throw new RuntimeException(e);
            }
        }
    }


    // =========================================================
    // PROBLEM 2
    // Reference Desk Subclass Reach
    // =========================================================

    static class PremiumLibraryMember
            extends LibraryMember {

        void showFines() {

            // protected member can be accessed
            // from the subclass.
            System.out.println(
                    "Fines owed: " + finesOwed
            );
        }
    }


    // =========================================================
    // PROBLEM 3
    // Book Copy Circulation Guard
    // =========================================================

    static class BookInventory {

        private int copiesTotal;

        private int copiesAvailable;


        BookInventory(int copiesTotal) {

            if (copiesTotal <= 0) {

                throw new IllegalArgumentException(
                        "copiesTotal must be positive"
                );
            }


            this.copiesTotal = copiesTotal;

            this.copiesAvailable = copiesTotal;
        }


        void checkOut() {

            // Do not allow the count to become negative.
            if (copiesAvailable > 0) {

                copiesAvailable--;
            }
        }


        void checkIn() {

            // Do not allow the count to exceed total capacity.
            if (copiesAvailable < copiesTotal) {

                copiesAvailable++;
            }
        }


        int getCopiesAvailable() {

            return copiesAvailable;
        }
    }


    // =========================================================
    // PROBLEM 5
    // Immutable Loan Receipt & Nightly Circulation Ledger
    // =========================================================

    /*
     * IMPORTANT:
     *
     * The assignment says LoanReceipt must be final AND
     * ReferenceOnlyLoanReceipt must extend LoanReceipt.
     *
     * Java does not allow a class declared final to be extended.
     *
     * Therefore LoanReceipt is NOT declared final here so
     * ReferenceOnlyLoanReceipt can extend it.
     *
     * The fields remain final and arrays are defensively copied,
     * so the object is still immutable.
     */

    static class LoanReceipt {

        private final String memberId;

        private final String[] bookIds;


        public LoanReceipt(String memberId,
                           String[] bookIds) {

            this.memberId = memberId;

            // Defensive copy on input
            this.bookIds = bookIds.clone();
        }


        String getMemberId() {

            return memberId;
        }


        String[] getBookIds() {

            // Defensive copy on output
            return bookIds.clone();
        }


        LoanReceipt withCorrectedBookId(
                int index,
                String newId) {

            // Copy the original array.
            String[] correctedIds =
                    bookIds.clone();


            // Change only the copy.
            correctedIds[index] = newId;


            // Return a completely new object.
            return new LoanReceipt(
                    memberId,
                    correctedIds
            );
        }
    }


    // =========================================================
    // Reference-Only Loan Receipt
    // =========================================================

    static class ReferenceOnlyLoanReceipt
            extends LoanReceipt {

        private final String roomNumber;


        public ReferenceOnlyLoanReceipt(
                String memberId,
                String[] bookIds,
                String roomNumber) {

            super(memberId, bookIds);

            this.roomNumber = roomNumber;
        }


        String getRoomNumber() {

            return roomNumber;
        }
    }


    // =========================================================
    // Circulation Ledger
    // =========================================================

    static class CirculationLedger {

        static String branchCode;


        // Static block runs exactly once
        static {

            branchCode = "PT-001";

            System.out.println(
                    "Circulation ledger loaded"
            );
        }


        static String processNightlyCirculation(
                LoanReceipt[] receipts) {

            int processed = 0;

            int nullSkipped = 0;

            int referenceOnly = 0;

            int regular = 0;


            // Single pass
            for (LoanReceipt receipt : receipts) {

                // Null safety
                if (receipt == null) {

                    nullSkipped++;

                    continue;
                }


                processed++;


                // Runtime type checking
                if (receipt instanceof ReferenceOnlyLoanReceipt) {

                    referenceOnly++;

                } else {

                    regular++;
                }
            }


            return processed
                    + " processed | "
                    + nullSkipped
                    + " null skipped | "
                    + referenceOnly
                    + " reference-only | "
                    + regular
                    + " regular";
        }
    }


    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {


        // =====================================================
        // PROBLEM 1
        // =====================================================

        System.out.println("===== PROBLEM 1 =====");


        System.out.println(
                AccessChecker.classifyAccess(
                        "private",
                        "SAME_CLASS"
                )
        );


        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "DIFFERENT_PACKAGE"
                )
        );


        String[][] attempts = {

                {"private", "SAME_CLASS"},

                {"private", "SAME_PACKAGE"},

                {"default", "SAME_PACKAGE"},

                {"default", "DIFFERENT_PACKAGE"},

                {"protected", "SAME_PACKAGE"},

                {"protected", "SAME_CLASS"},

                {"public", "DIFFERENT_PACKAGE"}
        };


        System.out.println(
                AccessChecker.summarizeByModifier(
                        attempts
                )
        );


        // =====================================================
        // PROBLEM 2
        // =====================================================

        System.out.println("\n===== PROBLEM 2 =====");


        String[][] orderedAttempts = {

                {
                    "public",
                    "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                },

                {
                    "protected",
                    "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                },

                {
                    "protected",
                    "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
                }
        };


        System.out.println(
                AccessChecker.firstDeniedAttempt(
                        orderedAttempts
                )
        );


        String[][] noDeniedAttempts = {

                {
                    "public",
                    "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
                },

                {
                    "protected",
                    "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
                }
        };


        System.out.println(
                AccessChecker.firstDeniedAttempt(
                        noDeniedAttempts
                )
        );


        // =====================================================
        // PROBLEM 3
        // =====================================================

        System.out.println("\n===== PROBLEM 3 =====");


        BookInventory b =
                new BookInventory(3);


        b.checkOut();

        b.checkOut();

        b.checkOut();

        // Fourth checkout is rejected
        b.checkOut();


        System.out.println(
                "Available copies: "
                        + b.getCopiesAvailable()
        );


        b.checkIn();

        b.checkIn();

        b.checkIn();

        // Fourth check-in is rejected
        b.checkIn();


        System.out.println(
                "Available copies: "
                        + b.getCopiesAvailable()
        );


        // =====================================================
        // PROBLEM 4
        // =====================================================

        System.out.println("\n===== PROBLEM 4 =====");


        LibraryMember member =
                new LibraryMember();


        member.setMembershipId(
                "LIB-8841"
        );


        member.setName(
                "Priya Nair"
        );


        member.setPremiumMember(
                true
        );


        System.out.println(
                "Membership ID: "
                        + member.getMembershipId()
        );


        System.out.println(
                "Name: "
                        + member.getName()
        );


        System.out.println(
                "Premium: "
                        + member.isPremiumMember()
        );


        // Try to change membershipId
        member.setMembershipId(
                "FAKE-0000"
        );


        System.out.println(
                "After second ID update: "
                        + member.getMembershipId()
        );


        // Write-only security answer
        member.setSecurityAnswer(
                "BlueMountain"
        );


        System.out.println(
                "Security answer stored securely"
        );


        // =====================================================
        // PROBLEM 5
        // =====================================================

        System.out.println("\n===== PROBLEM 5 =====");


        LoanReceipt r =
                new LoanReceipt(
                        "LIB-8841",
                        new String[]{
                                "BK-100",
                                "BK-101"
                        }
                );


        // Get defensive copy
        String[] ids =
                r.getBookIds();


        // Modify the returned array
        ids[0] = "HACKED";


        // Original object remains unchanged
        System.out.println(
                "Original first book ID: "
                        + r.getBookIds()[0]
        );


        // Create corrected receipt
        LoanReceipt corrected =
                r.withCorrectedBookId(
                        1,
                        "BK-102"
                );


        System.out.println(
                "Original IDs: "
                        + String.join(
                                ", ",
                                r.getBookIds()
                        )
        );


        System.out.println(
                "Corrected IDs: "
                        + String.join(
                                ", ",
                                corrected.getBookIds()
                        )
        );


        // =====================================================
        // Nightly Circulation
        // =====================================================

        LoanReceipt[] receipts = {

                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        new String[]{
                                "BK-200"
                        },
                        "Reading Room 3"
                ),

                null,

                new LoanReceipt(
                        "LIB-002",
                        new String[]{
                                "BK-201"
                        }
                )
        };


        System.out.println(
                CirculationLedger.processNightlyCirculation(
                        receipts
                )
        );
    }
}