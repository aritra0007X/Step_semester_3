public class Week6IndividualAssignment {

    // =========================================================
    // PROBLEM 1
    // Gym Membership Foundation & Batch Trial Sign-up Validator
    // =========================================================

    static class GymMember {

        protected String memberId;
        protected int monthlyFee;
        protected int sessionsAttended;

        // Problem 5
        public final String membershipNumber;

        private static int membersEnrolled = 0;

        // Problem 3
        private int[] lateFeeHistory = new int[10];
        private int lateFeeCount = 0;

        // Problem 5
        private int feesPaid = 0;
        private String lastPaymentMode;

        // Constructor for Problem 1
        public GymMember(String memberId, int monthlyFee) {

            if (memberId == null ||
                memberId.trim().isEmpty() ||
                memberId.length() < 4) {

                throw new IllegalArgumentException(
                    "Invalid member ID"
                );
            }

            if (monthlyFee <= 0) {
                throw new IllegalArgumentException(
                    "Monthly fee must be positive"
                );
            }

            this.memberId = memberId;
            this.monthlyFee = monthlyFee;

            membersEnrolled++;

            this.membershipNumber =
                "GYM-" + (2000 + membersEnrolled);
        }

        // Constructor required in Problem 5
        public GymMember(int monthlyFee) {

            if (monthlyFee <= 0) {
                throw new IllegalArgumentException(
                    "Monthly fee must be positive"
                );
            }

            this.memberId = "UNKNOWN";
            this.monthlyFee = monthlyFee;

            membersEnrolled++;

            this.membershipNumber =
                "GYM-" + (2000 + membersEnrolled);
        }

        // ---------------------------------------------------------
        // Problem 1
        // ---------------------------------------------------------

        void attendSession() {
            sessionsAttended++;
        }

        int getSessionsAttended() {
            return sessionsAttended;
        }

        // ---------------------------------------------------------
        // Problem 2 / 4
        // ---------------------------------------------------------

        String displayInfo() {
            return "Standard | Sessions: " + sessionsAttended;
        }

        // ---------------------------------------------------------
        // Problem 3
        // ---------------------------------------------------------

        protected void chargeLateFee(int amount) {

            if (lateFeeCount < 10) {
                lateFeeHistory[lateFeeCount] = amount;
                lateFeeCount++;
            }
        }

        int[] getLateFeeHistory() {

            int[] copy = new int[lateFeeCount];

            for (int i = 0; i < lateFeeCount; i++) {
                copy[i] = lateFeeHistory[i];
            }

            return copy;
        }

        int getTotalLateFees() {

            int total = 0;

            for (int i = 0; i < lateFeeCount; i++) {
                total += lateFeeHistory[i];
            }

            return total;
        }

        // ---------------------------------------------------------
        // Problem 1
        // ---------------------------------------------------------

        static String signUpBatch(
                String[] memberIds,
                int monthlyFee) {

            int signedUp = 0;
            int rejected = 0;

            for (String id : memberIds) {

                try {
                    new GymMember(id, monthlyFee);
                    signedUp++;

                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }

            return "Signed Up: " + signedUp
                    + " | Rejected: " + rejected;
        }

        // ---------------------------------------------------------
        // Problem 5
        // ---------------------------------------------------------

        void payFee(int amount) {

            if (amount > 0) {
                feesPaid += amount;
            }
        }

        void payFee(int amount, String mode) {

            lastPaymentMode = mode;

            // Reuse the one-argument method
            payFee(amount);
        }

        int getFeesPaid() {
            return feesPaid;
        }

        static int getMembersEnrolled() {
            return membersEnrolled;
        }

        static boolean isValidReferralCode(String code) {

            if (code == null || code.length() != 4) {
                return false;
            }

            if (code.charAt(0) != 'G') {
                return false;
            }

            if (!Character.isDigit(code.charAt(1))) {
                return false;
            }

            if (!Character.isDigit(code.charAt(2))) {
                return false;
            }

            if (!Character.isUpperCase(code.charAt(3))) {
                return false;
            }

            return true;
        }
    }


    // =========================================================
    // PROBLEM 1 + PROBLEM 3
    // Premium Member
    // =========================================================

    static class PremiumMember extends GymMember {

        private String trainerName;

        public PremiumMember(
                String memberId,
                int monthlyFee,
                String trainerName) {

            super(memberId, monthlyFee);

            this.trainerName = trainerName;
        }

        String getTrainerName() {
            return trainerName;
        }

        @Override
        String displayInfo() {

            return "Premium | Trainer: "
                    + trainerName
                    + " | Sessions: "
                    + sessionsAttended;
        }

        // Problem 3
        @Override
        protected void chargeLateFee(int amount) {

            // Premium gets 50% discount
            super.chargeLateFee(amount / 2);
        }
    }


    // =========================================================
    // PROBLEM 2
    // Elite Member
    // =========================================================

    static class EliteMember extends PremiumMember {

        private String lockerNumber;

        public EliteMember(
                String memberId,
                int monthlyFee,
                String trainerName,
                String lockerNumber) {

            super(memberId, monthlyFee, trainerName);

            this.lockerNumber = lockerNumber;
        }

        @Override
        String displayInfo() {

            return "Elite | Trainer: "
                    + getTrainerName()
                    + " | Locker: "
                    + lockerNumber
                    + " | Sessions: "
                    + sessionsAttended;
        }
    }


    // =========================================================
    // PROBLEM 2
    // Group Class Member
    // =========================================================

    static class GroupClassMember extends GymMember {

        private String className;

        // Constructor from Problem 2
        public GroupClassMember(
                String memberId,
                int monthlyFee,
                String className) {

            super(memberId, monthlyFee);

            this.className = className;
        }

        // Constructor required in Problem 5
        public GroupClassMember(
                int monthlyFee,
                String className) {

            super(monthlyFee);

            this.className = className;
        }

        String getClassName() {
            return className;
        }

        @Override
        String displayInfo() {

            return "Group Class Member | Class: "
                    + className
                    + " | Sessions: "
                    + sessionsAttended;
        }
    }


    // =========================================================
    // PROBLEM 2
    // classifyGeneration()
    // =========================================================

    static String classifyGeneration(GymMember member) {

        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        }

        if (member instanceof PremiumMember) {
            return "Second generation";
        }

        return "Base generation";
    }


    // =========================================================
    // PROBLEM 2
    // getTotalSessionsAttended()
    // =========================================================

    static int getTotalSessionsAttended(
            GymMember[] members) {

        int total = 0;

        for (GymMember member : members) {

            // Polymorphism
            total += member.getSessionsAttended();
        }

        return total;
    }


    // =========================================================
    // PROBLEM 4
    // Monthly Attendance Announcer
    // =========================================================

    static String batchPrint(GymMember[] members) {

        StringBuilder announcement = new StringBuilder();

        for (GymMember member : members) {

            // Polymorphic method call
            announcement.append(member.displayInfo());

            // Safe downcast only for PremiumMember
            if (member instanceof PremiumMember) {

                PremiumMember premium =
                        (PremiumMember) member;

                announcement
                        .append(" [Trainer via downcast: ")
                        .append(premium.getTrainerName())
                        .append("]");
            }

            announcement.append(" | ");
        }

        return announcement.toString();
    }


    // =========================================================
    // PROBLEM 5
    // Weekly Check-in Settlement
    // =========================================================

    static String processWeeklyCheckIn(
            GymMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (GymMember member : members) {

            // Null safety
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof GroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + group + " group | "
                + individual + " individual";
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        System.out.println(
            "========== PROBLEM 1 =========="
        );

        // Invalid ID
        try {

            GymMember g1 =
                    new GymMember("GM1", 1000);

        } catch (IllegalArgumentException e) {

            System.out.println(
                "GM1 construction rejected"
            );
        }

        // Premium member
        PremiumMember p =
                new PremiumMember(
                    "MEM01",
                    2000,
                    "Coach Riya"
                );

        p.attendSession();
        p.attendSession();

        System.out.println(
            "Sessions attended: "
            + p.getSessionsAttended()
        );

        // Batch signup
        String batchResult =
                GymMember.signUpBatch(
                    new String[]{
                        "MEM1",
                        "GM1",
                        "MEM2",
                        " ",
                        "MEM3"
                    },
                    1000
                );

        System.out.println(batchResult);


        // =====================================================
        System.out.println(
            "\n========== PROBLEM 2 =========="
        );

        GymMember standard =
                new GymMember("MEM1", 1000);

        PremiumMember premium =
                new PremiumMember(
                    "MEM2",
                    2000,
                    "Coach Riya"
                );

        EliteMember elite =
                new EliteMember(
                    "MEM3",
                    3000,
                    "Coach Arjun",
                    "L12"
                );

        GroupClassMember group =
                new GroupClassMember(
                    "MEM4",
                    1500,
                    "Zumba"
                );

        System.out.println(standard.displayInfo());
        System.out.println(premium.displayInfo());
        System.out.println(elite.displayInfo());
        System.out.println(group.displayInfo());

        System.out.println(
            classifyGeneration(elite)
        );

        System.out.println(
            classifyGeneration(group)
        );

        premium.attendSession();
        premium.attendSession();
        premium.attendSession();

        elite.attendSession();
        elite.attendSession();

        group.attendSession();
        group.attendSession();
        group.attendSession();
        group.attendSession();

        GymMember[] mixedMembers = {
            premium,
            elite,
            group
        };

        System.out.println(
            "Total sessions: "
            + getTotalSessionsAttended(
                mixedMembers
            )
        );


        // =====================================================
        System.out.println(
            "\n========== PROBLEM 3 =========="
        );

        PremiumMember latePremium =
                new PremiumMember(
                    "MEM5",
                    2000,
                    "Coach Riya"
                );

        latePremium.chargeLateFee(200);

        System.out.println(
            "Total late fees: "
            + latePremium.getTotalLateFees()
        );

        int[] history =
                latePremium.getLateFeeHistory();

        history[0] = 999;

        System.out.println(
            "After modifying returned array: "
            + latePremium.getLateFeeHistory()[0]
        );


        // =====================================================
        System.out.println(
            "\n========== PROBLEM 4 =========="
        );

        GymMember[] announcementMembers = {

            new GymMember("MEM6", 1000),

            new PremiumMember(
                "MEM7",
                2000,
                "Coach Riya"
            )
        };

        System.out.println(
            batchPrint(announcementMembers)
        );

        // Demonstration of unsafe cast
        GymMember plain =
                new GymMember("MEM8", 1000);

        try {

            PremiumMember bad =
                    (PremiumMember) plain;

        } catch (ClassCastException e) {

            System.out.println(
                "ClassCastException at runtime"
            );
        }


        // =====================================================
        System.out.println(
            "\n========== PROBLEM 5 =========="
        );

        GymMember m1 =
                new GymMember(1000);

        System.out.println(
            "Membership Number: "
            + m1.membershipNumber
        );

        System.out.println(
            "Members Enrolled: "
            + GymMember.getMembersEnrolled()
        );

        System.out.println(
            "G45B: "
            + GymMember.isValidReferralCode("G45B")
        );

        System.out.println(
            "G4B: "
            + GymMember.isValidReferralCode("G4B")
        );

        System.out.println(
            "X45B: "
            + GymMember.isValidReferralCode("X45B")
        );

        m1.payFee(500);
        m1.payFee(500, "UPI");

        System.out.println(
            "Fees Paid: "
            + m1.getFeesPaid()
        );

        GroupClassMember weeklyGroup =
                new GroupClassMember(
                    1500,
                    "Zumba"
                );

        GymMember weeklyIndividual =
                new GymMember(1000);

        GymMember[] weeklyBatch = {
            weeklyGroup,
            null,
            weeklyIndividual
        };

        System.out.println(
            processWeeklyCheckIn(weeklyBatch)
        );
    }
}