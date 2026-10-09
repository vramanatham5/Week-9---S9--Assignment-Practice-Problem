import java.util.Locale;
import java.util.Scanner;

interface NightService {
    default double applyNightFare(double dayFare) {
        return dayFare * 1.20;
    }
}

abstract class Cab {
    private static final double MINIMUM_FARE = 100.0;

    protected abstract double ratePerKm();

    final double fare(double kilometers) {
        return Math.max(kilometers * ratePerKm(), MINIMUM_FARE);
    }
}

final class MiniCab extends Cab {
    @Override
    protected double ratePerKm() {
        return 10.0;
    }
}

final class SedanCab extends Cab implements NightService {
    @Override
    protected double ratePerKm() {
        return 14.0;
    }
}

final class SuvCab extends Cab implements NightService {
    @Override
    protected double ratePerKm() {
        return 18.0;
    }
}

public class Main {
    private static Cab createCab(String type) {
        return switch (type) {
            case "MINI" -> new MiniCab();
            case "SEDAN" -> new SedanCab();
            case "SUV" -> new SuvCab();
            default -> throw new IllegalArgumentException("Unknown cab type: " + type);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int trips = scanner.nextInt();
        double total = 0.0;

        for (int i = 0; i < trips; i++) {
            String type = scanner.next().toUpperCase(Locale.ROOT);
            double kilometers = scanner.nextDouble();
            String time = scanner.next().toUpperCase(Locale.ROOT);
            Cab cab = createCab(type);

            if (time.equals("NIGHT") && !(cab instanceof NightService)) {
                System.out.printf("%s: night service not available%n", type);
                continue;
            }

            double fare = cab.fare(kilometers);
            if (time.equals("NIGHT")) {
                fare = ((NightService) cab).applyNightFare(fare);
            } else if (!time.equals("DAY")) {
                throw new IllegalArgumentException("Unknown trip time: " + time);
            }

            System.out.printf(Locale.US, "%s: %.2f%n", type, fare);
            total += fare;
        }

        System.out.printf(Locale.US, "Total: %.2f%n", total);
    }
}
