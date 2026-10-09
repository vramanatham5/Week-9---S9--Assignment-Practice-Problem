import java.util.Locale;
import java.util.Scanner;

interface BusUser {
}

abstract class Student {
    private static final double TRANSPORT_FEE = 12000.0;
    private final String name;

    protected Student(String name) {
        this.name = name;
    }

    final String name() {
        return name;
    }

    abstract double tuitionFee();

    protected double additionalFee() {
        return 0.0;
    }

    final double totalFee() {
        double transportFee = this instanceof BusUser ? TRANSPORT_FEE : 0.0;
        return tuitionFee() + additionalFee() + transportFee;
    }
}

final class DayScholar extends Student implements BusUser {
    DayScholar(String name) {
        super(name);
    }

    @Override
    double tuitionFee() {
        return 40000.0;
    }
}

final class Hosteller extends Student {
    Hosteller(String name) {
        super(name);
    }

    @Override
    double tuitionFee() {
        return 40000.0;
    }

    @Override
    protected double additionalFee() {
        return 60000.0;
    }
}

final class ScholarshipStudent extends Student implements BusUser {
    ScholarshipStudent(String name) {
        super(name);
    }

    @Override
    double tuitionFee() {
        return 20000.0;
    }
}

public class Main {
    private static Student createStudent(String type, String name) {
        return switch (type) {
            case "DAY_SCHOLAR" -> new DayScholar(name);
            case "HOSTELLER" -> new Hosteller(name);
            case "SCHOLAR" -> new ScholarshipStudent(name);
            default -> throw new IllegalArgumentException("Unknown student type: " + type);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int students = scanner.nextInt();
        double totalCollected = 0.0;

        for (int i = 0; i < students; i++) {
            String type = scanner.next().toUpperCase(Locale.ROOT);
            String name = scanner.next();
            Student student = createStudent(type, name);
            double fee = student.totalFee();
            System.out.printf(Locale.US, "%s: %.2f%n", student.name(), fee);
            totalCollected += fee;
        }

        System.out.printf(Locale.US, "Total Collected: %.2f%n", totalCollected);
    }
}
