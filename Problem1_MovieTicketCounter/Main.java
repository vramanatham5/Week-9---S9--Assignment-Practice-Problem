import java.util.Locale;
import java.util.Scanner;

abstract class Seat {
    private static final double CONVENIENCE_FEE = 20.0;

    protected abstract double ticketPrice();

    final double bookingAmount(int count) {
        return count * (ticketPrice() + CONVENIENCE_FEE);
    }
}

final class RegularSeat extends Seat {
    @Override
    protected double ticketPrice() {
        return 150.0;
    }
}

final class PremiumSeat extends Seat {
    @Override
    protected double ticketPrice() {
        return 250.0;
    }
}

final class ReclinerSeat extends Seat {
    @Override
    protected double ticketPrice() {
        return 400.0;
    }
}

public class Main {
    private static Seat createSeat(String type) {
        return switch (type) {
            case "REGULAR" -> new RegularSeat();
            case "PREMIUM" -> new PremiumSeat();
            case "RECLINER" -> new ReclinerSeat();
            default -> throw new IllegalArgumentException("Unknown seat type: " + type);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int bookings = scanner.nextInt();
        double total = 0.0;

        for (int i = 0; i < bookings; i++) {
            String type = scanner.next().toUpperCase(Locale.ROOT);
            int count = scanner.nextInt();
            double amount = createSeat(type).bookingAmount(count);
            System.out.printf(Locale.US, "%s: %.2f%n", type, amount);
            total += amount;
        }

        System.out.printf(Locale.US, "Total: %.2f%n", total);
    }
}
