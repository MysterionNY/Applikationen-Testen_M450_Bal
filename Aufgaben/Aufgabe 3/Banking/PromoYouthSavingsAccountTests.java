package ch.schule.bank.junit5;

import ch.schule.PromoYouthSavingsAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PromoYouthSavingsAccountTests {

    @Test
    public void test() {
        PromoYouthSavingsAccount account =
                new PromoYouthSavingsAccount("Y-1000");

        // 10'000 + 1 % Bonus = 10'100
        assertTrue(account.deposit(1, 10000));
        assertEquals(10100, account.getBalance());

        // Negative Einzahlung darf nicht möglich sein
        assertFalse(account.deposit(2, -100));
        assertEquals(10100, account.getBalance());
    }
}