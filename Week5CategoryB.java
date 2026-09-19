public class Week5CategoryB {

    // =========================================================
    // PROBLEM 1
    // Movie Ticket Field Visibility Checker
    // =========================================================

    static class AccessChecker {

        static String classifyAccess(String fieldModifier,
                                     String accessorContext) {

            switch (accessorContext) {

                case "SAME_CLASS":
                    // All access modifiers are accessible
                    // inside the same class.
                    return "ALLOWED";

                case "SAME_PACKAGE":
                    // private is accessible only inside
                    // the declaring class.
                    if (fieldModifier.equals("private")) {
                        return "DENIED";
                    }

                    // default, protected and public
                    // are accessible within the same package.
                    return "ALLOWED";

                case "DIFFERENT_PACKAGE":
                    // Only public is directly accessible
                    // from a different package.
                    if (fieldModifier.equals("public")) {
                        return "ALLOWED";
                    }

                    return "DENIED";

                case "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE":
                    // In a subclass in another package,
                    // protected and public are accessible.
                    if (fieldModifier.equals("protected")
                            || fieldModifier.equals("public")) {
                        return "ALLOWED";
                    }

                    return "DENIED";

                case "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE":
                    // Protected access through a parent-typed
                    // reference is denied in another package.
                    if (fieldModifier.equals("public")) {
                        return "ALLOWED";
                    }

                    return "DENIED";

                default:
                    return "DENIED";
            }
        }


        static String summarizeBatch(String[][] attempts) {

            int allowed = 0;
            int denied = 0;

            for (String[] attempt : attempts) {

                String result = classifyAccess(
                        attempt[0],
                        attempt[1]
                );

                if (result.equals("ALLOWED")) {
                    allowed++;
                } else {
                    denied++;
                }
            }

            return "Allowed: " + allowed
                    + " | Denied: " + denied;
        }
    }


    // MovieTicket with all four access levels
    static class MovieTicket {

        private String seatNumber;

        String screenId;          // default access

        protected double ticketPrice;

        public String movieTitle; // public access


        MovieTicket(String seatNumber,
                    String screenId,
                    double ticketPrice,
                    String movieTitle) {

            this.seatNumber = seatNumber;
            this.screenId = screenId;
            this.ticketPrice = ticketPrice;
            this.movieTitle = movieTitle;
        }
    }


    // =========================================================
    // PROBLEM 2
    // Subclass Ticket Access
    // =========================================================

    static class PremiumMovieTicket extends MovieTicket {

        PremiumMovieTicket(String seatNumber,
                           String screenId,
                           double ticketPrice,
                           String movieTitle) {

            super(
                    seatNumber,
                    screenId,
                    ticketPrice,
                    movieTitle
            );
        }


        void showProtectedPrice() {

            System.out.println(
                    "Protected ticket price: "
                            + ticketPrice
            );
        }
    }


    // =========================================================
    // PROBLEM 3
    // Seat Booking Encapsulation Guard
    // =========================================================

    static class CineScreen {

        private int seatsTotal;

        private int seatsAvailable;


        CineScreen(int seatsTotal) {

            // Constructor validation
            if (seatsTotal <= 0) {
                throw new IllegalArgumentException(
                        "seatsTotal must be positive"
                );
            }

            this.seatsTotal = seatsTotal;
            this.seatsAvailable = seatsTotal;
        }


        void bookSeat() {

            // Booking is allowed only when
            // at least one seat is available.
            if (seatsAvailable > 0) {
                seatsAvailable--;
            }
        }


        void cancelBooking() {

            // Cancellation is allowed only when
            // seatsAvailable is less than seatsTotal.
            if (seatsAvailable < seatsTotal) {
                seatsAvailable++;
            }
        }


        int getSeatsAvailable() {

            return seatsAvailable;
        }
    }


    // =========================================================
    // PROBLEM 4
    // MovieBookingProfile JavaBean & OTP Property
    // =========================================================

    static class MovieBookingProfile {

        private String name;

        private boolean confirmed;

        private String otp;


        // Public no-argument constructor
        public MovieBookingProfile() {
        }


        // Convenience constructor
        // Chains to no-argument constructor
        public MovieBookingProfile(String name) {

            this();

            this.name = name;
        }


        // JavaBean getter
        String getName() {

            return name;
        }


        // JavaBean setter
        void setName(String name) {

            this.name = name;
        }


        // Boolean JavaBean getter
        boolean isConfirmed() {

            return confirmed;
        }


        // Boolean JavaBean setter
        void setConfirmed(boolean confirmed) {

            this.confirmed = confirmed;
        }


        // Write-only OTP property
        void setOtp(String otp) {

            this.otp = otp;
        }

        // No getter for OTP
    }


    // =========================================================
    // PROBLEM 5
    // Immutable Booking Receipt & Nightly Settlement
    // =========================================================

    /*
     * NOTE:
     * BookingReceipt cannot be final because
     * GroupBookingReceipt extends BookingReceipt.
     *
     * The fields themselves are final and the class
     * uses defensive copying to maintain immutability.
     */

    static class BookingReceipt {

        private final String bookingId;

        private final String[] seatNumbers;


        public BookingReceipt(String bookingId,
                              String[] seatNumbers) {

            this.bookingId = bookingId;

            // Defensive copy when receiving the array
            this.seatNumbers = seatNumbers.clone();
        }


        String getBookingId() {

            return bookingId;
        }


        String[] getSeatNumbers() {

            // Defensive copy when returning the array
            return seatNumbers.clone();
        }


        BookingReceipt withUpdatedSeat(int index,
                                       String newSeat) {

            // Create a copy of the original array
            String[] updatedSeats = seatNumbers.clone();

            // Modify only the copied array
            updatedSeats[index] = newSeat;

            // Return a brand-new BookingReceipt
            return new BookingReceipt(
                    bookingId,
                    updatedSeats
            );
        }
    }


    // Group booking variant
    static class GroupBookingReceipt
            extends BookingReceipt {

        private final int groupSize;


        public GroupBookingReceipt(String bookingId,
                                   String[] seatNumbers,
                                   int groupSize) {

            super(bookingId, seatNumbers);

            this.groupSize = groupSize;
        }


        int getGroupSize() {

            return groupSize;
        }
    }


    // Nightly settlement processor
    static String processNightlySettlement(
            BookingReceipt[] receipts) {

        int processed = 0;

        int nullSkipped = 0;

        int group = 0;

        int individual = 0;


        for (BookingReceipt receipt : receipts) {

            // Never crash on null entries
            if (receipt == null) {

                nullSkipped++;

                continue;
            }


            processed++;


            // Check whether the receipt is a group booking
            if (receipt instanceof GroupBookingReceipt) {

                group++;

            } else {

                individual++;
            }
        }


        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + group
                + " group | "
                + individual
                + " individual";
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

                {"default", "SAME_PACKAGE"},

                {"default", "DIFFERENT_PACKAGE"},

                {"public", "DIFFERENT_PACKAGE"}
        };


        System.out.println(
                AccessChecker.summarizeBatch(attempts)
        );


        // =====================================================
        // PROBLEM 2
        // =====================================================

        System.out.println("\n===== PROBLEM 2 =====");


        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
                )
        );


        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                )
        );


        // =====================================================
        // PROBLEM 3
        // =====================================================

        System.out.println("\n===== PROBLEM 3 =====");


        CineScreen c = new CineScreen(2);


        c.bookSeat();

        c.bookSeat();

        // Third booking is rejected
        c.bookSeat();


        System.out.println(
                "Available seats: "
                        + c.getSeatsAvailable()
        );


        c.cancelBooking();

        c.cancelBooking();

        // Third cancellation is rejected
        c.cancelBooking();


        System.out.println(
                "Available seats: "
                        + c.getSeatsAvailable()
        );


        // =====================================================
        // PROBLEM 4
        // =====================================================

        System.out.println("\n===== PROBLEM 4 =====");


        MovieBookingProfile profile =
                new MovieBookingProfile("Rahul Dev");


        System.out.println(
                "Name: "
                        + profile.getName()
        );


        profile.setConfirmed(true);


        System.out.println(
                "Confirmed: "
                        + profile.isConfirmed()
        );


        profile.setOtp("4471");


        System.out.println(
                "OTP set successfully"
        );


        // =====================================================
        // PROBLEM 5
        // =====================================================

        System.out.println("\n===== PROBLEM 5 =====");


        BookingReceipt b =
                new BookingReceipt(
                        "CH-1001",
                        new String[]{"A1", "A2"}
                );


        // Get a copy of the seat array
        String[] seats = b.getSeatNumbers();


        // Modify the returned copy
        seats[0] = "X";


        // Original object remains unchanged
        System.out.println(
                "Original first seat: "
                        + b.getSeatNumbers()[0]
        );


        // Create a new receipt with updated seat
        BookingReceipt updated =
                b.withUpdatedSeat(
                        1,
                        "A3"
                );


        System.out.println(
                "Original seats: "
                        + String.join(
                                ", ",
                                b.getSeatNumbers()
                        )
        );


        System.out.println(
                "Updated seats: "
                        + String.join(
                                ", ",
                                updated.getSeatNumbers()
                        )
        );


        // Nightly settlement
        BookingReceipt[] receipts = {

                new GroupBookingReceipt(
                        "CH-2002",
                        new String[]{"B1", "B2"},
                        2
                ),

                null,

                new BookingReceipt(
                        "CH-3003",
                        new String[]{"C1"}
                )
        };


        System.out.println(
                processNightlySettlement(receipts)
        );
    }
}