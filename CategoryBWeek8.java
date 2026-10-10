import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

// ============================================================
// QUESTION 1: THE HOSTEL LAUNDRY QUEUE
// ============================================================

interface WashType {
    String getName();
    int getDurationMinutes();
    double getCharge();
}

class QuickWash implements WashType {
    public String getName() { return "Quick"; }
    public int getDurationMinutes() { return 30; }
    public double getCharge() { return 20.0; }
}

class NormalWash implements WashType {
    public String getName() { return "Normal"; }
    public int getDurationMinutes() { return 45; }
    public double getCharge() { return 30.0; }
}

class HeavyWash implements WashType {
    public String getName() { return "Heavy"; }
    public int getDurationMinutes() { return 60; }
    public double getCharge() { return 45.0; }
}

class LaundryStudent {
    private String name;

    public LaundryStudent(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}

class WashCycle {
    private LaundryStudent student;
    private WashingMachine machine;
    private WashType washType;

    public WashCycle(LaundryStudent student, WashingMachine machine, WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }

    public LaundryStudent getStudent() { return student; }
    public WashingMachine getMachine() { return machine; }
    public WashType getWashType() { return washType; }
}

class WashingMachine {
    private String machineId;
    private WashCycle currentCycle;

    public WashingMachine(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineId() { return machineId; }
    public boolean isFree() { return currentCycle == null; }

    public void startWash(LaundryStudent student, WashType type) {
        if (!isFree()) {
            System.out.println("Machine " + machineId + " is currently busy.");
            return;
        }

        currentCycle = new WashCycle(student, this, type);
        System.out.println(type.getName() + " wash started on " + machineId +
                " for " + student.getName() + " (" + type.getDurationMinutes() + " min).");
        System.out.printf(Locale.US, "Charge: ₹%.2f%n", type.getCharge());
    }

    public void completeCycle() {
        if (isFree()) {
            System.out.println(machineId + " has no active cycle.");
            return;
        }

        System.out.println(machineId + " cycle completed.");
        currentCycle = null;
        System.out.println(machineId + " is now free.");
    }
}

// ============================================================
// QUESTION 2: THE ASSIGNMENT SUBMISSION PORTAL
// ============================================================

abstract class Assignment {
    private String title;
    private int maxMarks;
    private LocalDate dueDate;

    public Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() { return title; }
    public int getMaxMarks() { return maxMarks; }
    public LocalDate getDueDate() { return dueDate; }
    public abstract double penaltyPerLateDay();

    public int calculateFinalMarks(int awardedMarks, long lateDays) {
        double penalty = penaltyPerLateDay() * lateDays;
        double finalMarks = awardedMarks * (1.0 - penalty);
        return Math.max(0, (int) Math.round(finalMarks));
    }
}

class CodingAssignment extends Assignment {
    public CodingAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    public double penaltyPerLateDay() { return 0.10; }
}

class WrittenAssignment extends Assignment {
    public WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    public double penaltyPerLateDay() { return 0.20; }
}

class AssignmentStudent {
    private String name;

    public AssignmentStudent(String name) { this.name = name; }
    public String getName() { return name; }
}

class Submission {
    private AssignmentStudent student;
    private Assignment assignment;
    private LocalDate submittedDate;
    private String status = "Submitted";
    private Integer finalMarks;

    public Submission(AssignmentStudent student, Assignment assignment, LocalDate submittedDate) {
        this.student = student;
        this.assignment = assignment;
        this.submittedDate = submittedDate;

        long lateDays = Math.max(0, ChronoUnit.DAYS.between(assignment.getDueDate(), submittedDate));
        System.out.println(student.getName() + "'s submission for '" + assignment.getTitle() +
                "' received (" + (lateDays == 0 ? "on time" : lateDays + " days late") + ").");
        System.out.println("Status: " + status);
    }

    public void grade(int awardedMarks) {
        if (!status.equals("Submitted")) {
            System.out.println("Cannot grade: submission status is " + status);
            return;
        }

        if (awardedMarks < 0 || awardedMarks > assignment.getMaxMarks()) {
            System.out.println("Invalid marks. Must be between 0 and " + assignment.getMaxMarks());
            return;
        }

        long lateDays = Math.max(0, ChronoUnit.DAYS.between(assignment.getDueDate(), submittedDate));
        finalMarks = assignment.calculateFinalMarks(awardedMarks, lateDays);
        status = "Graded";

        System.out.println(student.getName() + " graded: " + finalMarks +
                "/" + assignment.getMaxMarks() +
                (lateDays > 0 ? " after " +
                        Math.round(assignment.penaltyPerLateDay() * lateDays * 100) +
                        "% late penalty." : "."));
        System.out.println("Status: " + status);
    }

    public void resubmit() {
        if (status.equals("Graded")) {
            System.out.println("Cannot resubmit: '" + assignment.getTitle() + "' has already been graded.");
        } else {
            System.out.println("Resubmission accepted for '" + assignment.getTitle() + "'.");
        }
    }
}

// ============================================================
// QUESTION 3: THE CAMPUS PREMIERE TICKET COUNTER
// ============================================================

abstract class Seat {
    private String seatId;

    public Seat(String seatId) { this.seatId = seatId; }
    public String getSeatId() { return seatId; }
    public abstract double getPrice();
}

class RegularSeat extends Seat {
    public RegularSeat(String seatId) { super(seatId); }
    public double getPrice() { return 150.0; }
}

class PremiumSeat extends Seat {
    public PremiumSeat(String seatId) { super(seatId); }
    public double getPrice() { return 250.0; }
}

class ReclinerSeat extends Seat {
    public ReclinerSeat(String seatId) { super(seatId); }
    public double getPrice() { return 400.0; }
}

class TicketCustomer {
    private String name;

    public TicketCustomer(String name) { this.name = name; }
    public String getName() { return name; }
}

class Show {
    private String showName;
    private LocalDateTime startTime;
    private Map<String, Seat> bookedSeats = new HashMap<>();

    public Show(String showName, LocalDateTime startTime) {
        this.showName = showName;
        this.startTime = startTime;
    }

    public String getShowName() { return showName; }
    public boolean hasStarted(LocalDateTime now) { return !now.isBefore(startTime); }
    public boolean isSeatAvailable(Seat seat) { return !bookedSeats.containsKey(seat.getSeatId()); }

    public boolean reserveSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            if (!isSeatAvailable(seat)) {
                System.out.println("Seat " + seat.getSeatId() + " is already booked for this show.");
                return false;
            }
        }
        for (Seat seat : seats) {
            bookedSeats.put(seat.getSeatId(), seat);
        }
        return true;
    }

    public void releaseSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            bookedSeats.remove(seat.getSeatId());
        }
    }
}

class TicketBooking {
    private TicketCustomer customer;
    private Show show;
    private List<Seat> seats;
    private boolean active = true;

    public TicketBooking(TicketCustomer customer, Show show, List<Seat> seats) {
        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>(seats);
    }

    public void cancel(LocalDateTime now) {
        if (!active) {
            System.out.println(customer.getName() + "'s booking is already cancelled.");
        } else if (show.hasStarted(now)) {
            System.out.println("Cannot cancel: the show has already started.");
        } else {
            show.releaseSeats(seats);
            active = false;
            StringJoiner ids = new StringJoiner(", ");
            for (Seat seat : seats) ids.add(seat.getSeatId());
            System.out.println(customer.getName() + "'s booking cancelled. Seats " + ids + " released.");
        }
    }
}

class TicketService {
    public TicketBooking book(TicketCustomer customer, Show show, List<Seat> seats) {
        if (seats == null || seats.isEmpty()) {
            System.out.println("Booking must contain at least one seat.");
            return null;
        }
        if (seats.size() > 6) {
            System.out.println("Maximum 6 seats per booking.");
            return null;
        }

        Set<String> uniqueIds = new HashSet<>();
        for (Seat seat : seats) {
            if (!uniqueIds.add(seat.getSeatId())) {
                System.out.println("Duplicate seat in booking: " + seat.getSeatId());
                return null;
            }
        }

        if (!show.reserveSeats(seats)) return null;

        StringJoiner ids = new StringJoiner(", ");
        double total = 0;
        for (Seat seat : seats) {
            ids.add(seat.getSeatId());
            total += seat.getPrice();
        }

        System.out.println("Booking confirmed for " + customer.getName() + ": " + ids + ".");
        System.out.printf(Locale.US, "Total: ₹%.2f%n", total);
        return new TicketBooking(customer, show, seats);
    }
}

// ============================================================
// QUESTION 4: THE FITZONE MEMBERSHIP DESK
// ============================================================

interface MembershipPlan {
    String getName();
    int getMonths();
    double calculateFee();
}

class MonthlyPlan implements MembershipPlan {
    public String getName() { return "Monthly"; }
    public int getMonths() { return 1; }
    public double calculateFee() { return 1000.0; }
}

class QuarterlyPlan implements MembershipPlan {
    public String getName() { return "Quarterly"; }
    public int getMonths() { return 3; }
    public double calculateFee() { return 1000.0 * 3 * 0.90; }
}

class AnnualPlan implements MembershipPlan {
    public String getName() { return "Annual"; }
    public int getMonths() { return 12; }
    public double calculateFee() { return 1000.0 * 12 * 0.75; }
}

class GymMember {
    private String name;

    public GymMember(String name) { this.name = name; }
    public String getName() { return name; }
}

class Membership {
    private GymMember member;
    private MembershipPlan plan;
    private String status = "Active";

    public Membership(GymMember member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;
        System.out.println(plan.getName() + " membership created for " + member.getName() + ".");
        System.out.printf(Locale.US, "Fee: ₹%.2f. Status: %s.%n", plan.calculateFee(), status);
    }

    public String getStatus() { return status; }

    public void checkIn() {
        if (status.equals("Active")) {
            System.out.println(member.getName() + " checked in successfully.");
        } else {
            System.out.println("Check-in denied: " + member.getName() + "'s membership is " + status + ".");
        }
    }

    public void freeze() {
        if (status.equals("Active")) {
            status = "Frozen";
            System.out.println(member.getName() + "'s membership frozen. Status: " + status + ".");
        } else if (status.equals("Expired")) {
            System.out.println("Cannot freeze an Expired membership.");
        } else {
            System.out.println("Membership is already Frozen.");
        }
    }

    public void unfreeze() {
        if (status.equals("Frozen")) {
            status = "Active";
            System.out.println(member.getName() + "'s membership unfrozen. Status: " + status + ".");
        } else if (status.equals("Expired")) {
            System.out.println("Cannot unfreeze an Expired membership.");
        } else {
            System.out.println("Membership is already Active.");
        }
    }

    public void expire() {
        if (!status.equals("Expired")) {
            status = "Expired";
            System.out.println(member.getName() + "'s membership expired. Status: " + status + ".");
        }
    }
}

// ============================================================
// QUESTION 5: THE CAMPUS NOTICE BROADCASTER
// ============================================================

interface NotificationChannel {
    String getName();
    void send(String studentName, String message);
}

class EmailChannel implements NotificationChannel {
    public String getName() { return "Email"; }
    public void send(String studentName, String message) {
        System.out.println("[Email → " + studentName + "] " + message);
    }
}

class SmsChannel implements NotificationChannel {
    public String getName() { return "SMS"; }
    public void send(String studentName, String message) {
        System.out.println("[SMS → " + studentName + "] " + message);
    }
}

class AppChannel implements NotificationChannel {
    public String getName() { return "App"; }
    public void send(String studentName, String message) {
        System.out.println("[App → " + studentName + "] " + message);
    }
}

class NoticeStudent {
    private String name;
    private String department;
    private List<NotificationChannel> channels = new ArrayList<>();

    public NoticeStudent(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getName() { return name; }
    public String getDepartment() { return department; }

    public void addChannel(NotificationChannel channel) {
        if (channel != null) channels.add(channel);
    }

    public List<NotificationChannel> getChannels() {
        return Collections.unmodifiableList(channels);
    }
}

class Notice {
    private String title;
    private Set<String> targetDepartments;

    public Notice(String title, Set<String> targetDepartments) {
        this.title = title;
        this.targetDepartments = new HashSet<>(targetDepartments);
    }

    public String getTitle() { return title; }
    public Set<String> getTargetDepartments() {
        return Collections.unmodifiableSet(targetDepartments);
    }

    public boolean isValid() {
        return title != null && !title.trim().isEmpty() && !targetDepartments.isEmpty();
    }
}

class NoticeBoard {
    private List<NoticeStudent> students = new ArrayList<>();

    public void registerStudent(NoticeStudent student) {
        students.add(student);
    }

    public void postNotice(Notice notice) {
        if (notice == null || !notice.isValid()) {
            System.out.println("Cannot post notice: A title and at least one target department are required.");
            return;
        }

        StringJoiner departments = new StringJoiner(", ");
        for (String department : notice.getTargetDepartments()) departments.add(department);
        System.out.println("Notice '" + notice.getTitle() + "' posted to " + departments + ".");

        for (NoticeStudent student : students) {
            if (notice.getTargetDepartments().contains(student.getDepartment())) {
                for (NotificationChannel channel : student.getChannels()) {
                    channel.send(student.getName(), notice.getTitle());
                }
            }
        }
    }
}

// ============================================================
// MAIN CLASS: DEMONSTRATE ALL FIVE QUESTIONS
// ============================================================

public class CategoryBWeek8 {
    public static void main(String[] args) {
        System.out.println("===== QUESTION 1: HOSTEL LAUNDRY QUEUE =====");
        LaundryStudent asha = new LaundryStudent("Asha");
        LaundryStudent ravi = new LaundryStudent("Ravi");
        LaundryStudent neha = new LaundryStudent("Neha");
        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        m1.startWash(asha, new QuickWash());
        m1.startWash(ravi, new HeavyWash());
        m2.startWash(ravi, new HeavyWash());
        m1.completeCycle();
        m1.startWash(neha, new NormalWash());

        System.out.println("\n===== QUESTION 2: ASSIGNMENT SUBMISSION =====");
        Assignment coding = new CodingAssignment("Linked List Lab", 50, LocalDate.of(2027, 3, 10));
        Assignment written = new WrittenAssignment("Design Essay", 50, LocalDate.of(2027, 3, 12));
        AssignmentStudent aStudent = new AssignmentStudent("Asha");
        AssignmentStudent rStudent = new AssignmentStudent("Ravi");

        Submission s1 = new Submission(aStudent, coding, LocalDate.of(2027, 3, 10));
        Submission s2 = new Submission(rStudent, written, LocalDate.of(2027, 3, 14));
        s1.grade(45);
        s2.grade(40);
        s1.resubmit();

        System.out.println("\n===== QUESTION 3: MOVIE TICKET COUNTER =====");
        TicketService ticketService = new TicketService();
        LocalDateTime showTime = LocalDateTime.of(2027, 5, 1, 19, 0);
        LocalDateTime beforeShow = LocalDateTime.of(2027, 5, 1, 18, 0);
        Show show = new Show("7 PM Show", showTime);

        TicketCustomer ticketAsha = new TicketCustomer("Asha");
        TicketCustomer ticketRavi = new TicketCustomer("Ravi");
        TicketCustomer ticketNeha = new TicketCustomer("Neha");

        TicketBooking ashaBooking = ticketService.book(ticketAsha, show, Arrays.asList(
                new RegularSeat("A1"), new RegularSeat("A2"), new PremiumSeat("F5")));
        ticketService.book(ticketRavi, show, Arrays.asList(new RegularSeat("A2")));
        ticketService.book(ticketRavi, show, Arrays.asList(new ReclinerSeat("R1")));

        if (ashaBooking != null) ashaBooking.cancel(beforeShow);
        ticketService.book(ticketNeha, show, Arrays.asList(new RegularSeat("A2")));

        System.out.println("\n===== QUESTION 4: FITZONE MEMBERSHIP =====");
        Membership ashaMembership = new Membership(new GymMember("Asha"), new QuarterlyPlan());
        Membership raviMembership = new Membership(new GymMember("Ravi"), new MonthlyPlan());

        ashaMembership.checkIn();
        ashaMembership.freeze();
        ashaMembership.checkIn();
        raviMembership.expire();
        raviMembership.freeze();

        System.out.println("\n===== QUESTION 5: CAMPUS NOTICE BROADCASTER =====");
        NoticeBoard board = new NoticeBoard();

        NoticeStudent noticeAsha = new NoticeStudent("Asha", "CSE");
        noticeAsha.addChannel(new EmailChannel());
        noticeAsha.addChannel(new AppChannel());

        NoticeStudent noticeRavi = new NoticeStudent("Ravi", "ECE");
        noticeRavi.addChannel(new SmsChannel());

        board.registerStudent(noticeAsha);
        board.registerStudent(noticeRavi);

        board.postNotice(new Notice("Lab Closed Tomorrow", new HashSet<>(Arrays.asList("CSE"))));
        board.postNotice(new Notice("Fee Deadline Extended", new HashSet<>(Arrays.asList("CSE", "ECE"))));
        board.postNotice(new Notice("Sports Day", new HashSet<>()));
    }
}
