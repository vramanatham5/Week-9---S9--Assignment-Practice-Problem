import java.util.Locale;
import java.util.Scanner;

interface Insurable {
    double INSURANCE_RATE = 0.02;

    default double insuranceFor(double declaredValue) {
        return declaredValue * INSURANCE_RATE;
    }
}

abstract class Parcel {
    private final double weightKg;
    private final double declaredValue;

    protected Parcel(double weightKg, double declaredValue) {
        this.weightKg = weightKg;
        this.declaredValue = declaredValue;
    }

    protected final double weightKg() {
        return weightKg;
    }

    protected final double standardCharge() {
        return 40.0 + 10.0 * weightKg;
    }

    abstract double shippingCharge();

    final double insurance() {
        return this instanceof Insurable insurable
                ? insurable.insuranceFor(declaredValue)
                : 0.0;
    }

}

final class StandardParcel extends Parcel {
    StandardParcel(double weightKg, double declaredValue) {
        super(weightKg, declaredValue);
    }

    @Override
    double shippingCharge() {
        return standardCharge();
    }
}

final class ExpressParcel extends Parcel implements Insurable {
    ExpressParcel(double weightKg, double declaredValue) {
        super(weightKg, declaredValue);
    }

    @Override
    double shippingCharge() {
        return 80.0 + 15.0 * weightKg();
    }
}

final class FragileParcel extends Parcel implements Insurable {
    FragileParcel(double weightKg, double declaredValue) {
        super(weightKg, declaredValue);
    }

    @Override
    double shippingCharge() {
        return standardCharge() + 50.0;
    }
}

public class Main {
    private static Parcel createParcel(String type, double weightKg, double declaredValue) {
        return switch (type) {
            case "STANDARD" -> new StandardParcel(weightKg, declaredValue);
            case "EXPRESS" -> new ExpressParcel(weightKg, declaredValue);
            case "FRAGILE" -> new FragileParcel(weightKg, declaredValue);
            default -> throw new IllegalArgumentException("Unknown parcel type: " + type);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int parcels = scanner.nextInt();
        double grandTotal = 0.0;

        for (int i = 0; i < parcels; i++) {
            String type = scanner.next().toUpperCase(Locale.ROOT);
            double weightKg = scanner.nextDouble();
            double declaredValue = scanner.nextDouble();
            Parcel parcel = createParcel(type, weightKg, declaredValue);
            double charge = parcel.shippingCharge();
            double insurance = parcel.insurance();
            double total = charge + insurance;

            System.out.printf(
                    Locale.US,
                    "%s: Charge=%.2f Insurance=%.2f Total=%.2f%n",
                    type,
                    charge,
                    insurance,
                    total);
            grandTotal += total;
        }

        System.out.printf(Locale.US, "Grand Total: %.2f%n", grandTotal);
    }
}
