public class Week4Assignment {

    // =====================================================
    // A1. OVERLOADED CONSTRUCTORS FOR HACKATHON REGISTRATION
    // =====================================================

    static class Participant {

        String name;
        String teamName;
        boolean registered;

        // Constructor with team name
        public Participant(String name, String teamName) {

            this.name = name;
            this.teamName = teamName;
            this.registered = true;
        }

        // Constructor for solo participant
        public Participant(String name) {

            this(name, "Unassigned");
        }

        void printStatus() {

            System.out.println(
                    name
                            + " | "
                            + teamName
                            + " | Registered: "
                            + registered
            );
        }
    }


    // =====================================================
    // A2. THIS KEYWORD - CANTEEN INVENTORY
    // =====================================================

    static class Item {

        String itemName;
        int stock;

        public Item(String itemName, int stock) {

            this.itemName = itemName;
            this.stock = stock;
        }

        public void restock(int stock) {

            this.stock += stock;
        }
    }


    // =====================================================
    // A3. FINAL METHOD - PARKING OVERSTAY FINE
    // =====================================================

    static class ParkingTicket {

        String vehicleNo;
        double ratePerMinute;

        public ParkingTicket(
                String vehicleNo,
                double ratePerMinute) {

            this.vehicleNo = vehicleNo;
            this.ratePerMinute = ratePerMinute;
        }

        public final double calculateFine(
                int overstayMinutes) {

            return overstayMinutes * ratePerMinute;
        }

        public final void printReceipt(
                int overstayMinutes) {

            double fine =
                    calculateFine(overstayMinutes);

            System.out.println(
                    vehicleNo
                            + " - Fine: Rs "
                            + fine
            );
        }
    }


    // =====================================================
    // A4. STATIC BLOCK - LIBRARY MEMBERSHIP CARD
    // =====================================================

    static class MembershipCard {

        static String libraryName;
        static String validUntil;

        String studentName;

        // Runs exactly once when class is loaded
        static {

            libraryName = "SRM Central Library";
            validUntil = "May 2027";

            System.out.println(
                    "Library info loaded"
            );
        }

        public MembershipCard(String studentName) {

            this.studentName = studentName;
        }

        public void printConfirmation() {

            System.out.println(
                    "Membership card issued: "
                            + studentName
            );
        }
    }


    // =====================================================
    // A5. INSTANCEOF - CANTEEN PAYMENT DISPATCH
    // =====================================================

    static class Payment {

        public double pay(double amount) {

            System.out.println(
                    "Paid (cash): Rs "
                            + amount
            );

            return amount;
        }
    }


    static class CardPayment extends Payment {

        public double payWithProcessingFee(
                double amount) {

            double total =
                    amount + (amount * 0.02);

            System.out.println(
                    "Charged (card, incl. fee): Rs "
                            + total
            );

            return total;
        }
    }


    static double processTransaction(
            Payment payment,
            double amount) {

        if (payment instanceof CardPayment) {

            CardPayment cardPayment =
                    (CardPayment) payment;

            return cardPayment
                    .payWithProcessingFee(amount);

        } else {

            return payment.pay(amount);
        }
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {


        // =================================================
        // A1. HACKATHON REGISTRATION
        // =================================================

        System.out.println(
                "\n========== A1: HACKATHON REGISTRATION =========="
        );

        String[] names = {
                "Ravi",
                "Meera",
                "Karthik",
                "Divya"
        };

        String[] teamNames = {
                "ByteBusters",
                "",
                "CodeCrafters",
                ""
        };

        for (int i = 0; i < names.length; i++) {

            Participant participant;

            if (teamNames[i].isEmpty()) {

                participant =
                        new Participant(names[i]);

            } else {

                participant =
                        new Participant(
                                names[i],
                                teamNames[i]
                        );
            }

            participant.printStatus();
        }


        // =================================================
        // A2. CANTEEN INVENTORY
        // =================================================

        System.out.println(
                "\n========== A2: CANTEEN INVENTORY =========="
        );

        Item[] items = {

                new Item("Samosa", 15),
                new Item("Tea Powder", 40),
                new Item("Bread", 8),
                new Item("Biscuit Packs", 25)
        };

        for (Item item : items) {

            item.restock(20);

            System.out.println(
                    item.itemName
                            + " | Final Stock: "
                            + item.stock
            );
        }


        // =================================================
        // A3. PARKING FINES
        // =================================================

        System.out.println(
                "\n========== A3: PARKING FINES =========="
        );

        String[] vehicleNos = {
                "TN09AB1234",
                "TN22CD5678",
                "TN09EF9012",
                "TN10GH3456"
        };

        double[] ratePerMinute = {
                2,
                2,
                3,
                2
        };

        int[] overstayMinutes = {
                15,
                0,
                -5,
                8
        };

        for (int i = 0; i < vehicleNos.length; i++) {

            ParkingTicket ticket =
                    new ParkingTicket(
                            vehicleNos[i],
                            ratePerMinute[i]
                    );

            if (overstayMinutes[i] > 0) {

                ticket.printReceipt(
                        overstayMinutes[i]
                );

            } else {

                System.out.println(
                        vehicleNos[i]
                                + " - No fine, within allotted time"
                );
            }
        }


        // =================================================
        // A4. STATIC BLOCK
        // =================================================

        System.out.println(
                "\n========== A4: LIBRARY MEMBERSHIP =========="
        );

        String[] studentNames = {
                "Ananya",
                "Rohan",
                "Priya",
                "Arjun",
                "Sneha"
        };

        for (String studentName : studentNames) {

            MembershipCard card =
                    new MembershipCard(studentName);

            card.printConfirmation();
        }


        // =================================================
        // A5. INSTANCEOF PAYMENT DISPATCH
        // =================================================

        System.out.println(
                "\n========== A5: PAYMENT DISPATCH =========="
        );

        Payment[] payments = {

                new CardPayment(),
                new Payment(),
                new CardPayment(),
                new Payment(),
                new CardPayment()
        };

        double[] amounts = {
                100,
                50,
                200,
                75,
                120
        };

        double totalCollected = 0;

        for (int i = 0; i < payments.length; i++) {

            double charged =
                    processTransaction(
                            payments[i],
                            amounts[i]
                    );

            totalCollected += charged;
        }

        System.out.println(
                "Total Collected: Rs "
                        + totalCollected
        );
    }
}