public class PriceCalculation {

    static double calculatePrice(double baseprice, double specialprice, double extraprice, int extras, double discount) 
    {
        double addon_discount;
        double result;

        if (extras >= 3)
            addon_discount = 10;
        else if (extras >= 5)
            addon_discount = 15;
        else
            addon_discount = 0;

        if (discount > addon_discount)
            addon_discount = discount;

        result = baseprice / 100.0 * (100 - discount) + specialprice
                + extraprice / 100.0 * (100 - addon_discount);

        return result;
    }

    static boolean test_calculate_price() {

        boolean test_ok = true;

        // Test 1: Keine Rabatte
        test_ok &= checkTest(
                "Keine Rabatte",
                35000,
                calculatePrice(30000, 2000, 3000, 0, 0)
        );

        // Test 2: Nur Händlerrabatt von 5 %
        test_ok &= checkTest(
                "5% Haendlerrabatt",
                33500,
                calculatePrice(30000, 2000, 3000, 2, 5)
        );

        // Test 3: 3 Extras -> 10 % Zubehörrabatt
        test_ok &= checkTest(
                "3 Extras",
                33200,
                calculatePrice(30000, 2000, 3000, 3, 5)
        );

        // Test 4: 4 Extras -> weiterhin 10 %
        test_ok &= checkTest(
                "4 Extras",
                33200,
                calculatePrice(30000, 2000, 3000, 4, 5)
        );

        // Test 5: 5 Extras -> 15 % Zubehörrabatt
        test_ok &= checkTest(
                "5 Extras",
                33050,
                calculatePrice(30000, 2000, 3000, 5, 5)
        );

        return test_ok;
    }

    static boolean checkTest(String name, double expected, double actual) {

        boolean passed = Math.abs(expected - actual) < 0.001;

        System.out.println(
                name
                        + " | Erwartet: " + expected
                        + " | Erhalten: " + actual
                        + " | " + (passed ? "OK" : "FEHLER")
        );

        return passed;
    }

    public static void main(String[] args) {

        boolean result = test_calculate_price();

        if (result) {
            System.out.println("\nAlle Tests erfolgreich.");
        } else {
            System.out.println("\nMindestens ein Test ist fehlgeschlagen.");
        }
    }
}