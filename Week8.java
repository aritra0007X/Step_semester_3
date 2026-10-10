import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ==================================================
// QUESTION 1: VEHICLE RENTAL SYSTEM
// ==================================================

abstract class Vehicle {
    protected String id;
    protected double ratePerDay;
    private boolean available = true;

    public Vehicle(String id, double ratePerDay) {
        this.id = id;
        this.ratePerDay = ratePerDay;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    public Sedan(String id) {
        super(id, 50);
    }

    @Override
    public double calculateCharge(int days) {
        return ratePerDay * days;
    }
}

class SUV extends Vehicle {
    public SUV(String id) {
        super(id, 80);
    }

    @Override
    public double calculateCharge(int days) {
        return ratePerDay * days + 20;
    }
}

class Truck extends Vehicle {
    public Truck(String id) {
        super(id, 100);
    }

    @Override
    public double calculateCharge(int days) {
        return ratePerDay * days + 50;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Vehicle vehicle;
    private Customer customer;
    private int days;
    private boolean active = true;

    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
    }

    public void returnVehicle() {
        if (active) {
            active = false;
            vehicle.setAvailable(true);
            System.out.println(vehicle.getId() + " returned by " + customer.getName());
        }
    }
}

class RentalService {
    public Rental rent(Vehicle vehicle, Customer customer, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getId() + " is currently unavailable");
            return null;
        }

        if (days <= 0) {
            System.out.println("Invalid rental duration");
            return null;
        }

        vehicle.setAvailable(false);
        Rental rental = new Rental(vehicle, customer, days);

        System.out.println(vehicle.getId() + " rented successfully by " + customer.getName());
        System.out.println("Rental charge: $" + vehicle.calculateCharge(days));
        return rental;
    }
}

// ==================================================
// QUESTION 2: EMPLOYEE LEAVE REQUEST WORKFLOW
// ==================================================

abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean canTakeLeave(int days);
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(int days) {
        return days > 0 && days <= 30;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(int days) {
        return days > 0 && days <= 10;
    }
}

class Contractor extends Employee {
    public Contractor(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(int days) {
        return days > 0 && days <= 5;
    }
}

class LeaveRequest {
    private Employee employee;
    private String startDate;
    private String endDate;
    private String status = "Pending";

    public LeaveRequest(Employee employee, String startDate, String endDate, int days) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;

        if (!employee.canTakeLeave(days)) {
            status = "Rejected";
        }
    }

    public void review(String decision, String reviewer) {
        if (!status.equals("Pending")) {
            System.out.println("Request already reviewed: " + status);
            return;
        }

        if (decision.equals("Approved") || decision.equals("Rejected")) {
            status = decision;
            System.out.println(employee.getName() + "'s leave request (" + startDate + "-"
                    + endDate + ") " + status.toLowerCase() + " by " + reviewer);
        } else {
            System.out.println("Invalid review decision");
        }
    }

    public void changeStatus(String newStatus) {
        if (!status.equals("Pending")) {
            System.out.println("Cannot change leave request status from " + status + " to " + newStatus);
        } else {
            System.out.println("Only a reviewer can approve or reject a pending request");
        }
    }

    public String getStatus() {
        return status;
    }
}

// ==================================================
// QUESTION 3: ONLINE EXAMINATION SYSTEM
// ==================================================

abstract class Question {
    protected String questionText;
    protected int points;

    public Question(String questionText, int points) {
        this.questionText = questionText;
        this.points = points;
    }

    public abstract boolean evaluate(String answer);

    public int getPoints() {
        return points;
    }
}

class MCQ extends Question {
    private String correctAnswer;

    public MCQ(String text, String correctAnswer, int points) {
        super(text, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer == null ? "" : answer.trim());
    }
}

class TrueFalseQuestion extends Question {
    private String correctAnswer;

    public TrueFalseQuestion(String text, String correctAnswer, int points) {
        super(text, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer == null ? "" : answer.trim());
    }
}

class ShortAnswerQuestion extends Question {
    private String correctAnswer;

    public ShortAnswerQuestion(String text, String correctAnswer, int points) {
        super(text, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer == null ? "" : answer.trim());
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Examination {
    private String title;
    private List<Question> questions = new ArrayList<>();

    public Examination(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}

class Attempt {
    private Student student;
    private Examination exam;
    private Map<Integer, String> answers = new HashMap<>();
    private boolean submitted = false;

    public Attempt(Student student, Examination exam) {
        this.student = student;
        this.exam = exam;
        System.out.println(exam.getTitle() + " started by " + student.getName());
    }

    public void answer(int questionNumber, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers for a submitted examination");
            return;
        }

        if (questionNumber < 1 || questionNumber > exam.getQuestions().size()) {
            System.out.println("Invalid question number");
            return;
        }

        answers.put(questionNumber, answer);
        System.out.println("Answer recorded for Question " + questionNumber);
    }

    public void submit() {
        if (submitted) {
            System.out.println("Examination already submitted");
            return;
        }

        submitted = true;
        int score = 0;
        int total = 0;

        System.out.println(exam.getTitle() + " submitted by " + student.getName());

        for (int i = 0; i < exam.getQuestions().size(); i++) {
            Question question = exam.getQuestions().get(i);
            total += question.getPoints();

            String answer = answers.getOrDefault(i + 1, "");
            boolean correct = question.evaluate(answer);

            if (correct) {
                score += question.getPoints();
            }

            System.out.println("Question " + (i + 1) + ": "
                    + (correct ? "Correct (" + question.getPoints() + " points)" : "Incorrect (0 points)"));
        }

        System.out.println("Total score: " + score + "/" + total);
    }
}

// ==================================================
// QUESTION 4: HOTEL BOOKING SYSTEM
// ==================================================

abstract class Room {
    protected String roomNumber;
    protected double rate;

    public Room(String roomNumber, double rate) {
        this.roomNumber = roomNumber;
        this.rate = rate;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public abstract double calculatePrice(long nights);
}

class StandardRoom extends Room {
    public StandardRoom(String roomNumber) {
        super(roomNumber, 100);
    }

    @Override
    public double calculatePrice(long nights) {
        return rate * nights;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String roomNumber) {
        super(roomNumber, 150);
    }

    @Override
    public double calculatePrice(long nights) {
        return rate * nights;
    }
}

class Suite extends Room {
    public Suite(String roomNumber) {
        super(roomNumber, 250);
    }

    @Override
    public double calculatePrice(long nights) {
        return rate * nights;
    }
}

class Reservation {
    private String customer;
    private Room room;
    private LocalDate start;
    private LocalDate end;
    private LocalDate cancellationDeadline;
    private boolean active = true;

    public Reservation(String customer, Room room, LocalDate start, LocalDate end,
                       LocalDate cancellationDeadline) {
        this.customer = customer;
        this.room = room;
        this.start = start;
        this.end = end;
        this.cancellationDeadline = cancellationDeadline;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isActive() {
        return active;
    }

    public boolean overlaps(LocalDate newStart, LocalDate newEnd) {
        return active && newStart.isBefore(end) && newEnd.isAfter(start);
    }

    public void cancel(LocalDate today) {
        if (!active) {
            System.out.println("Reservation is not active");
        } else if (today.isAfter(cancellationDeadline)) {
            System.out.println("Cancellation deadline has passed");
        } else {
            active = false;
            System.out.println("Reservation for " + customer + ", Room "
                    + room.getRoomNumber() + " cancelled successfully");
        }
    }
}

class HotelService {
    private List<Reservation> reservations = new ArrayList<>();

    public boolean isAvailable(Room room, LocalDate start, LocalDate end) {
        if (start == null || end == null || !start.isBefore(end)) {
            return false;
        }

        for (Reservation reservation : reservations) {
            if (reservation.getRoom() == room && reservation.overlaps(start, end)) {
                return false;
            }
        }

        return true;
    }

    public Reservation book(String customer, Room room, LocalDate start, LocalDate end,
                            LocalDate cancellationDeadline) {
        if (!isAvailable(room, start, end)) {
            System.out.println("Room " + room.getRoomNumber() + " is not available");
            return null;
        }

        Reservation reservation = new Reservation(customer, room, start, end, cancellationDeadline);
        reservations.add(reservation);

        long nights = ChronoUnit.DAYS.between(start, end);
        System.out.println("Reservation confirmed for " + customer + ", Room "
                + room.getRoomNumber() + " (" + start + " to " + end + ")");
        System.out.println("Price: $" + room.calculatePrice(nights));

        return reservation;
    }
}

// ==================================================
// QUESTION 5: PAYMENT PROCESSING SYSTEM
// ==================================================

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Order {
    private String orderId;
    private String customer;
    private List<Product> products = new ArrayList<>();
    private String status = "Pending";

    public Order(String orderId, String customer) {
        this.orderId = orderId;
        this.customer = customer;
        System.out.println("Order created for " + customer);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public void markPaid() {
        status = "Paid";
    }

    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            System.out.println("Invalid product or quantity");
            return;
        }

        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public double getTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }
}

interface PaymentMethod {
    boolean processPayment(double amount);
    String getName();
}

class CreditCardPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        return true; // Simulated successful payment
    }

    @Override
    public String getName() {
        return "Credit Card";
    }
}

class PayPalPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        return false; // Simulated failed payment
    }

    @Override
    public String getName() {
        return "PayPal";
    }
}

class BankTransferPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        return true; // Simulated successful payment
    }

    @Override
    public String getName() {
        return "Bank Transfer";
    }
}

class PaymentService {
    public void pay(Order order, PaymentMethod method) {
        if (order.isEmpty()) {
            System.out.println("Cannot process payment for an empty order");
            return;
        }

        if (order.getStatus().equals("Paid")) {
            System.out.println("Order " + order.getOrderId() + " is already paid");
            return;
        }

        System.out.println("Payment initiated via " + method.getName()
                + " for Order " + order.getOrderId());

        if (method.processPayment(order.getTotal())) {
            order.markPaid();
            System.out.println("Payment for Order " + order.getOrderId() + " successful");
        } else {
            System.out.println("Payment for Order " + order.getOrderId() + " failed");
        }

        System.out.println("Order status: " + order.getStatus());
    }
}

// ==================================================
// MAIN CLASS
// ==================================================

public class Week8 {
    public static void main(String[] args) {

        // QUESTION 1
        System.out.println("\n--- QUESTION 1: VEHICLE RENTAL ---");
        RentalService rentalService = new RentalService();
        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");

        Sedan sedan = new Sedan("Sedan A");
        SUV suv = new SUV("SUV B");

        Rental rental = rentalService.rent(sedan, c1, 3);
        rentalService.rent(sedan, c2, 2);
        if (rental != null) {
            rental.returnVehicle();
        }
        rentalService.rent(suv, c3, 5);

        // QUESTION 2
        System.out.println("\n--- QUESTION 2: LEAVE REQUEST ---");
        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");

        LeaveRequest johnLeave = new LeaveRequest(john, "Jan 1", "Jan 5", 5);
        System.out.println("Leave request submitted for John. Status: " + johnLeave.getStatus());
        johnLeave.review("Approved", "Alice");
        System.out.println("Status: " + johnLeave.getStatus());
        johnLeave.changeStatus("Pending");

        LeaveRequest janeLeave = new LeaveRequest(jane, "Feb 10", "Feb 11", 2);
        System.out.println("Leave request submitted for Jane. Status: " + janeLeave.getStatus());
        janeLeave.review("Rejected", "Bob");
        System.out.println("Status: " + janeLeave.getStatus());

        // QUESTION 3
        System.out.println("\n--- QUESTION 3: ONLINE EXAMINATION ---");
        Student student = new Student("Student 1");
        Examination exam = new Examination("Exam A");
        exam.addQuestion(new MCQ("Choose the correct option", "C", 5));
        exam.addQuestion(new TrueFalseQuestion("Java is purely procedural", "False", 5));

        Attempt attempt = new Attempt(student, exam);
        attempt.answer(1, "C");
        attempt.answer(2, "True");
        attempt.submit();
        attempt.answer(1, "A");

        // QUESTION 4
        System.out.println("\n--- QUESTION 4: HOTEL BOOKING ---");
        HotelService hotel = new HotelService();
        Room standard = new StandardRoom("101");
        Room deluxe = new DeluxeRoom("201");

        LocalDate start1 = LocalDate.of(2027, 1, 1);
        LocalDate end1 = LocalDate.of(2027, 1, 5);

        System.out.println("Room 101 available: " + hotel.isAvailable(standard, start1, end1));
        Reservation booking = hotel.book("Customer A", standard, start1, end1,
                LocalDate.of(2026, 12, 25));

        hotel.book("Customer B", standard, LocalDate.of(2027, 1, 3),
                LocalDate.of(2027, 1, 7), LocalDate.of(2026, 12, 25));

        if (booking != null) {
            booking.cancel(LocalDate.of(2026, 12, 20));
        }

        hotel.book("Customer C", deluxe, LocalDate.of(2027, 2, 10),
                LocalDate.of(2027, 2, 12), LocalDate.of(2027, 2, 1));

        // QUESTION 5
        System.out.println("\n--- QUESTION 5: PAYMENT PROCESSING ---");
        PaymentService paymentService = new PaymentService();

        Order orderX = new Order("X", "Customer X");
        orderX.addProduct(new Product("Product A", 100), 2);
        orderX.addProduct(new Product("Product B", 50), 1);
        paymentService.pay(orderX, new CreditCardPayment());

        Order orderY = new Order("Y", "Customer Y");
        paymentService.pay(orderY, new CreditCardPayment());

        Order orderZ = new Order("Z", "Customer Z");
        orderZ.addProduct(new Product("Product C", 200), 1);
        paymentService.pay(orderZ, new PayPalPayment());
    }
}
