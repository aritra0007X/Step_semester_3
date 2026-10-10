
interface Ringable {
    String ring();
}

class AlarmClock implements Ringable {
    private String time;

    public AlarmClock(String time) {
        this.time = time;
    }

    public String ring() {
        return "Alarm ringing for " + time;
    }
}

class Doorbell implements Ringable {
    private String location;

    public Doorbell(String location) {
        this.location = location;
    }

    public String ring() {
        return "Doorbell ringing at " + location;
    }
}


// PROBLEM 2: Gallery Description Cards

abstract class ArtPiece {
    private static int counter = 0;
    private final String pieceId;
    protected String title;

    public ArtPiece(String title) {
        this.title = title;
        this.pieceId = "AP-" + (++counter);
    }

    public abstract String describe();

    public String getPieceId() {
        return pieceId;
    }
}

class Painting extends ArtPiece {
    public Painting(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Painting: " + title + ", framed on canvas";
    }
}

class Sculpture extends ArtPiece {
    public Sculpture(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Sculpture: " + title + ", carved from stone";
    }
}


// PROBLEM 3: Backyard Toolshed Routine

abstract class GardenTool {
    public GardenTool() {
    }

    public abstract String use();
}

class CuttingTool extends GardenTool {
    public CuttingTool() {
        super();
    }

    @Override
    public String use() {
        return "Using the tool in the garden, blade sharpened first";
    }
}

class Pruner extends CuttingTool {
    public Pruner() {
        super();
    }

    @Override
    public String use() {
        return super.use() + ", then trimming branches precisely";
    }
}


// PROBLEM 4: Digital Classroom Setup

abstract class ClassroomDevice {
    public ClassroomDevice() {
    }

    public abstract String operate();
}

interface Chargeable {
    String charge();
    String charge(int minutes);
}

class Tablet extends ClassroomDevice implements Chargeable {
    private String assetTag;

    public Tablet(String assetTag) {
        this.assetTag = assetTag;
    }

    @Override
    public String operate() {
        return "Tablet " + assetTag + " displaying lesson";
    }

    @Override
    public String charge() {
        return assetTag + " charging";
    }

    @Override
    public String charge(int minutes) {
        return assetTag + " charging for " + minutes + " minutes";
    }
}


// PROBLEM 5: Skyline Delivery Fleet

abstract class Drone {
    protected String id;

    public Drone(String id) {
        this.id = id;
    }

    public abstract String fly();
}

interface Trackable {
    String getLocation();
}

class DeliveryDrone extends Drone implements Trackable {
    public DeliveryDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return "Delivery drone " + id + " is flying";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

class ScoutDrone extends Drone {
    public ScoutDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return "Scout drone " + id + " is flying";
    }
}

class GroundRobot implements Trackable {
    private String id;

    public GroundRobot(String id) {
        this.id = id;
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}


// MAIN CLASS

public class Week7a {

    static void ringAll(Ringable[] devices) {
        for (Ringable device : devices) {
            System.out.println(device.ring());
        }
    }

    static String getLocationIfTrackable(Object o) {
        if (o instanceof Trackable) {
            Trackable t = (Trackable) o;
            return t.getLocation();
        }

        return "Tracking not available";
    }

    public static void main(String[] args) {

        System.out.println("PROBLEM 1");
        AlarmClock a = new AlarmClock("7:00 AM");
        Doorbell d = new Doorbell("Front Door");

        ringAll(new Ringable[]{a, d});

        System.out.println("\nPROBLEM 2");
        Painting p = new Painting("Sunset Fields");
        Sculpture s = new Sculpture("The Thinker II");

        System.out.println(p.describe());
        System.out.println(s.describe());
        System.out.println("Piece ID: " + p.getPieceId());
        System.out.println("Piece ID: " + s.getPieceId());

        System.out.println("\nPROBLEM 3");
        CuttingTool c = new CuttingTool();
        Pruner pr = new Pruner();

        System.out.println(c.use());
        System.out.println(pr.use());

        System.out.println("\nPROBLEM 4");
        Tablet t = new Tablet("TAB-5");

        System.out.println(t.operate());
        System.out.println(t.charge());
        System.out.println(t.charge(30));

        System.out.println("\nPROBLEM 5");
        DeliveryDrone dd = new DeliveryDrone("DR-1");
        ScoutDrone sd = new ScoutDrone("SC-1");
        GroundRobot gr = new GroundRobot("GR-1");

        System.out.println(getLocationIfTrackable(dd));
        System.out.println(getLocationIfTrackable(sd));
        System.out.println(getLocationIfTrackable(gr));
    }
}
