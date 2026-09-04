package ch.schule.bank.junit5;

import ch.schule.SalaryAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SalaryAccountTests {

    @Test
    public void test() {
        SalaryAccount account =
                new SalaryAccount("P-1000", -1000);

        // Konto darf bis zur Kreditlimite ins Minus
        assertTrue(account.withdraw(1, 500));
        assertEquals(-500, account.getBalance());

        assertTrue(account.withdraw(2, 500));
        assertEquals(-1000, account.getBalance());

        // Kreditlimite überschritten
        assertFalse(account.withdraw(3, 1));

        // Negativer Betrag
        assertFalse(account.withdraw(3, -100));

        assertEquals(-1000, account.getBalance());
    }
}