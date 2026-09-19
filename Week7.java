import java.util.*;
public class Week7 {

    // ==================== PROBLEM 1 ====================

    static abstract class Toy {
        private static int counter = 1000;
        private final String toyId;

        public Toy() {
            counter++;
            toyId = "TOY-" + counter;
        }

        public abstract String makeSound();

        public String getToyId() {
            return toyId;
        }
    }

    static class ToyCar extends Toy {
        private String name;

        public ToyCar(String name) {
            super();
            this.name = name;
        }

        @Override
        public String makeSound() {
            return name + ": Vroom vroom!";
        }
    }

    static class ToyRobot extends Toy {
        private String name;

        public ToyRobot(String name) {
            super();
            this.name = name;
        }

        @Override
        public String makeSound() {
            return name + ": Beep boop!";
        }
    }


    // ==================== PROBLEM 2 ====================

    interface Printable {
        String printLabel();
    }

    static class PackageBox implements Printable {
        private String trackingId;

        public PackageBox(String trackingId) {
            this.trackingId = trackingId;
        }

        @Override
        public String printLabel() {
            return "Package label: " + trackingId;
        }
    }

    static class Invoice implements Printable {
        private String invoiceNumber;

        public Invoice(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        @Override
        public String printLabel() {
            return "Invoice label: " + invoiceNumber;
        }
    }

    static void printAll(Printable[] items) {
        for (Printable item : items) {
            System.out.println(item.printLabel());
        }
    }


    // ==================== PROBLEM 3 ====================

    static abstract class Instrument {

        public Instrument() {
            super();
        }

        public abstract String play();
    }

    static class StringInstrument extends Instrument {

        public StringInstrument() {
            super();
        }

        @Override
        public String play() {
            return "Strumming the strings";
        }
    }

    static class Violin extends StringInstrument {

        public Violin() {
            super();
        }

        @Override
        public String play() {
            return super.play()
                    + ", with a bow drawn across four strings";
        }
    }


    // ==================== PROBLEM 4 ====================

    static abstract class KitchenTool {
        private int speedLevel = 1;

        public KitchenTool() {
            super();
        }

        public abstract String prepare();

        public int getSpeedLevel() {
            return speedLevel;
        }

        public void setSpeedLevel(int speedLevel) {
            if (speedLevel >= 1 && speedLevel <= 5) {
                this.speedLevel = speedLevel;
            }
        }
    }

    interface Washable {
        String clean();
    }

    static class Blender extends KitchenTool implements Washable {

        public Blender() {
            super();
        }

        @Override
        public String prepare() {
            return "Blending at speed " + getSpeedLevel();
        }

        @Override
        public String clean() {
            return "Blender rinsed and dried";
        }
    }


    // ==================== PROBLEM 5 ====================

    static abstract class DeliveryNote {

        public DeliveryNote() {
            super();
        }

        public abstract String confirmDelivery();

        public String confirmDelivery(String signature) {
            return confirmDelivery() + ", signed by " + signature;
        }
    }

    static class ParcelNote extends DeliveryNote {
        private String trackingId;

        public ParcelNote(String trackingId) {
            super();
            this.trackingId = trackingId;
        }

        @Override
        public String confirmDelivery() {
            return "Parcel " + trackingId + " delivered";
        }
    }

    static class LetterNote extends DeliveryNote {
        private String trackingId;

        public LetterNote(String trackingId) {
            super();
            this.trackingId = trackingId;
        }

        @Override
        public String confirmDelivery() {
            return "Letter " + trackingId + " delivered";
        }
    }

    static void logAll(DeliveryNote[] notes) {
        for (DeliveryNote note : notes) {
            System.out.println(note.confirmDelivery());
        }
    }


    // ==================== MAIN METHOD ====================

    public static void main(String[] args) {

        // ---------- Problem 1 ----------
        System.out.println("===== PROBLEM 1 =====");

        ToyCar c = new ToyCar("Speedster");
        ToyRobot r = new ToyRobot("Bolt");

        System.out.println(c.makeSound());
        System.out.println(r.makeSound());
        System.out.println(c.getToyId());
        System.out.println(r.getToyId());


        // ---------- Problem 2 ----------
        System.out.println("\n===== PROBLEM 2 =====");

        PackageBox p = new PackageBox("TRK-88");
        Invoice i = new Invoice("INV-42");

        System.out.println(p.printLabel());
        System.out.println(i.printLabel());

        Printable[] items = {p, i};
        printAll(items);


        // ---------- Problem 3 ----------
        System.out.println("\n===== PROBLEM 3 =====");

        StringInstrument s = new StringInstrument();
        Violin v = new Violin();

        System.out.println(s.play());
        System.out.println(v.play());


        // ---------- Problem 4 ----------
        System.out.println("\n===== PROBLEM 4 =====");

        Blender b = new Blender();

        b.setSpeedLevel(3);
        System.out.println(b.getSpeedLevel());

        b.setSpeedLevel(9);
        System.out.println("After invalid value: " + b.getSpeedLevel());

        System.out.println(b.prepare());
        System.out.println(b.clean());


        // ---------- Problem 5 ----------
        System.out.println("\n===== PROBLEM 5 =====");

        ParcelNote parcel = new ParcelNote("TRK-1");
        LetterNote letter = new LetterNote("TRK-2");

        System.out.println(parcel.confirmDelivery());
        System.out.println(parcel.confirmDelivery("J. Smith"));

        DeliveryNote ref = parcel;

        DeliveryNote[] notes = {ref, letter};

        logAll(notes);
    }
}