import java.util.Locale;
import java.util.Scanner;

interface SaverMode {
    default double applySaver(double units) {
        return units * 0.75;
    }
}

abstract class Appliance {
    private static final double COST_PER_UNIT = 8.0;
    private final double hours;

    protected Appliance(double hours) {
        this.hours = hours;
    }

    protected abstract double powerWatts();

    final double unitsUsed() {
        return powerWatts() * hours / 1000.0;
    }

    final double cost(double units) {
        return units * COST_PER_UNIT;
    }
}

final class Fridge extends Appliance {
    Fridge(double hours) {
        super(hours);
    }

    @Override
    protected double powerWatts() {
        return 150.0;
    }
}

final class AirConditioner extends Appliance implements SaverMode {
    AirConditioner(double hours) {
        super(hours);
    }

    @Override
    protected double powerWatts() {
        return 1500.0;
    }
}

final class Television extends Appliance {
    Television(double hours) {
        super(hours);
    }

    @Override
    protected double powerWatts() {
        return 100.0;
    }
}

final class WashingMachine extends Appliance implements SaverMode {
    WashingMachine(double hours) {
        super(hours);
    }

    @Override
    protected double powerWatts() {
        return 500.0;
    }
}

public class Main {
    private static Appliance createAppliance(String type, double hours) {
        return switch (type) {
            case "FRIDGE" -> new Fridge(hours);
            case "AC" -> new AirConditioner(hours);
            case "TV" -> new Television(hours);
            case "WASHER" -> new WashingMachine(hours);
            default -> throw new IllegalArgumentException("Unknown appliance: " + type);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int applianceCount = Integer.parseInt(scanner.nextLine().trim());
        double totalCost = 0.0;

        for (int i = 0; i < applianceCount; i++) {
            String[] fields = scanner.nextLine().trim().split("\\s+");
            String type = fields[0].toUpperCase(Locale.ROOT);
            double hours = Double.parseDouble(fields[1]);
            boolean saverRequested = fields.length == 3
                    && fields[2].equalsIgnoreCase("SAVER");
            if (fields.length > 3 || (fields.length == 3 && !saverRequested)) {
                throw new IllegalArgumentException("Invalid appliance booking: " + String.join(" ", fields));
            }

            Appliance appliance = createAppliance(type, hours);
            if (saverRequested && !(appliance instanceof SaverMode)) {
                System.out.printf("%s: saver mode not supported%n", type);
                continue;
            }

            double units = appliance.unitsUsed();
            if (saverRequested) {
                units = ((SaverMode) appliance).applySaver(units);
            }
            double cost = appliance.cost(units);
            System.out.printf(Locale.US, "%s: Units=%.2f Cost=%.2f%n", type, units, cost);
            totalCost += cost;
        }

        System.out.printf(Locale.US, "Total Cost: %.2f%n", totalCost);
    }
}
