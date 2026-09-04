package ch.schule.bank.junit5;

import ch.schule.Account;
import ch.schule.Booking;
import ch.schule.SalaryAccount;
import ch.schule.SavingsAccount;
import org.junit.jupiter.api.Test;
import ch.schule.BankUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTests {

    @Test
    public void testInit() {
        Account account = new SavingsAccount("S-1000");

        assertEquals("S-1000", account.getId());
        assertEquals(0, account.getBalance());
    }

    @Test
    public void testDeposit() {
        Account account = new SavingsAccount("S-1000");

        assertTrue(account.deposit(10, 1000));
        assertEquals(1000, account.getBalance());

        assertTrue(account.deposit(10, 500));
        assertEquals(1500, account.getBalance());

        assertFalse(account.deposit(11, -100));
        assertEquals(1500, account.getBalance());
    }

    @Test
    public void testWithdraw() {
        Account account = new SalaryAccount("P-1000", -2000);
        account.deposit(10, 1000);

        assertTrue(account.withdraw(11, 400));
        assertEquals(600, account.getBalance());

        assertFalse(account.withdraw(12, -100));
        assertEquals(600, account.getBalance());
    }

    @Test
    public void testReferences() {
        SavingsAccount savingsAccount = new SavingsAccount("S-1000");
        Account account = savingsAccount;

        assertSame(savingsAccount, account);
        assertTrue(account instanceof SavingsAccount);

        Booking booking = new Booking(10, 500);
        account.setBooking(booking);

        assertSame(booking, account.getBooking());
    }

    @Test
    public void testCanTransact() {
        Account account = new SavingsAccount("S-1000");

        assertTrue(account.canTransact(100));

        assertTrue(account.deposit(100, 1000));

        assertTrue(account.canTransact(100));
        assertTrue(account.canTransact(101));
        assertFalse(account.canTransact(99));

        assertFalse(account.deposit(99, 100));
        assertEquals(1000, account.getBalance());
    }

    @Test
    public void testPrint() {
        Account account = new SavingsAccount("S-1000");

        account.deposit(0, 100000);
        account.withdraw(1, 50000);

        String output = captureOutput(account::print);

        assertTrue(output.contains("Kontoauszug 'S-1000'"));
        assertTrue(output.contains("01.01.1970"));
        assertTrue(output.contains("02.01.1970"));

        assertTrue(
                output.contains(
                        BankUtils.formatAmount(100000)
                )
        );

        assertTrue(
                output.contains(
                        BankUtils.formatAmount(-50000)
                )
        );
    }

    @Test
    public void testMonthlyPrint() {
        Account account = new SavingsAccount("S-1000");

        account.deposit(0, 100000);
        account.deposit(15, 200000);
        account.deposit(30, 300000);

        String output = captureOutput(() -> account.print(1970, 1));

        assertTrue(output.contains("Monat: 1.1970"));
        assertTrue(output.contains("01.01.1970"));
        assertTrue(output.contains("16.01.1970"));
        assertFalse(output.contains("01.02.1970"));
    }

    private String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(buffer));
            action.run();
        } finally {
            System.setOut(originalOut);
        }

        return buffer.toString();
    }
}