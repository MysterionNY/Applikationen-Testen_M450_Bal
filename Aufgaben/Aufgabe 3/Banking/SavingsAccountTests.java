package ch.schule.bank.junit5;

import ch.schule.SavingsAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTests {

    @Test
    public void test() {
        SavingsAccount account = new SavingsAccount("S-1000");

        // Ohne Guthaben kann nichts abgehoben werden
        assertFalse(account.withdraw(1, 1));

        assertTrue(account.deposit(1, 1000));

        assertTrue(account.withdraw(2, 400));
        assertEquals(600, account.getBalance());

        // Mehr abheben als vorhanden
        assertFalse(account.withdraw(3, 601));

        // Negativer Betrag
        assertFalse(account.withdraw(3, -100));

        assertEquals(600, account.getBalance());
    }
}