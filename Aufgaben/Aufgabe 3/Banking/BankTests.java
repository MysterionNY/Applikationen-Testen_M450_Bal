package ch.schule.bank.junit5;

import ch.schule.Account;
import ch.schule.Bank;
import ch.schule.SavingsAccount;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BankTests {

    @Test
    public void testCreate() {
        Bank bank = new Bank();

        assertEquals(
                "S-1000",
                bank.createSavingsAccount()
        );

        assertEquals(
                "Y-1001",
                bank.createPromoYouthSavingsAccount()
        );

        // Positive Kreditlimite ist ungültig
        assertNull(
                bank.createSalaryAccount(1000)
        );

        assertEquals(
                "P-1002",
                bank.createSalaryAccount(-1000)
        );

        assertEquals(
                0,
                bank.getBalance("S-1000")
        );

        assertEquals(
                0,
                bank.getBalance("UNKNOWN")
        );

        Account account =
                new SavingsAccount("REF-1");

        bank.setAccount(account);

        assertSame(
                account,
                bank.getAccount()
        );
    }

    @Test
    public void testDeposit() {
        Bank bank = new Bank();

        String id =
                bank.createSavingsAccount();

        assertTrue(
                bank.deposit(id, 10, 1000)
        );

        assertEquals(
                1000,
                bank.getBalance(id)
        );

        // Konto existiert nicht
        assertFalse(
                bank.deposit(
                        "UNKNOWN",
                        10,
                        1000
                )
        );

        // Negativer Betrag
        assertFalse(
                bank.deposit(id, 11, -100)
        );

        // Älteres Datum
        assertFalse(
                bank.deposit(id, 9, 100)
        );

        assertEquals(
                1000,
                bank.getBalance(id)
        );
    }

    @Test
    public void testWithdraw() {
        Bank bank = new Bank();

        String id =
                bank.createSavingsAccount();

        bank.deposit(
                id,
                10,
                1000
        );

        assertTrue(
                bank.withdraw(id, 11, 400)
        );

        assertEquals(
                600,
                bank.getBalance(id)
        );

        // Mehr Geld als vorhanden
        assertFalse(
                bank.withdraw(id, 12, 700)
        );

        // Unbekanntes Konto
        assertFalse(
                bank.withdraw(
                        "UNKNOWN",
                        12,
                        100
                )
        );

        // Negativer Betrag
        assertFalse(
                bank.withdraw(id, 12, -100)
        );

        assertEquals(
                600,
                bank.getBalance(id)
        );
    }

    @Test
    public void testPrint() {
        Bank bank = new Bank();

        String id =
                bank.createSavingsAccount();

        bank.deposit(
                id,
                0,
                100000
        );

        String output =
                captureOutput(
                        () -> bank.print(id)
                );

        assertTrue(
                output.contains(
                        "Kontoauszug 'S-1000'"
                )
        );

        assertTrue(
                output.contains(
                        "01.01.1970"
                )
        );

        String unknownOutput =
                captureOutput(
                        () ->
                                bank.print(
                                        "UNKNOWN"
                                )
                );

        assertEquals(
                "",
                unknownOutput
        );
    }

    @Test
    public void testMonthlyPrint() {
        Bank bank = new Bank();

        String id =
                bank.createSavingsAccount();

        bank.deposit(
                id,
                0,
                100000
        );

        bank.deposit(
                id,
                30,
                200000
        );

        String output =
                captureOutput(
                        () ->
                                bank.print(
                                        id,
                                        1970,
                                        1
                                )
                );

        assertTrue(
                output.contains(
                        "Monat: 1.1970"
                )
        );

        assertTrue(
                output.contains(
                        "01.01.1970"
                )
        );

        assertFalse(
                output.contains(
                        "01.02.1970"
                )
        );

        String unknownOutput =
                captureOutput(
                        () ->
                                bank.print(
                                        "UNKNOWN",
                                        1970,
                                        1
                                )
                );

        assertEquals(
                "",
                unknownOutput
        );
    }

    @Test
    public void testBalance() {
        Bank bank = new Bank();

        assertEquals(
                0,
                bank.getBalance()
        );

        String first =
                bank.createSavingsAccount();

        String second =
                bank.createSavingsAccount();

        bank.deposit(
                first,
                1,
                1000
        );

        bank.deposit(
                second,
                1,
                2000
        );

        assertEquals(
                1000,
                bank.getBalance(first)
        );

        assertEquals(
                2000,
                bank.getBalance(second)
        );

        assertEquals(
                -3000,
                bank.getBalance()
        );
    }

    @Test
    public void testTop5() {
        Bank bank =
                createBankWithSixAccounts();

        String output =
                captureOutput(
                        bank::printTop5
                );

        String[] lines =
                output
                        .strip()
                        .split("\\R");

        assertEquals(
                5,
                lines.length
        );

        assertEquals(
                "S-1001: 600",
                lines[0]
        );

        assertEquals(
                "S-1003: 500",
                lines[1]
        );

        assertEquals(
                "S-1005: 400",
                lines[2]
        );

        assertEquals(
                "S-1002: 300",
                lines[3]
        );

        assertEquals(
                "S-1004: 200",
                lines[4]
        );
    }

    @Test
    public void testBottom5() {
        Bank bank =
                createBankWithSixAccounts();

        String output =
                captureOutput(
                        bank::printBottom5
                );

        String[] lines =
                output
                        .strip()
                        .split("\\R");

        assertEquals(
                5,
                lines.length
        );

        assertEquals(
                "S-1000: 100",
                lines[0]
        );

        assertEquals(
                "S-1004: 200",
                lines[1]
        );

        assertEquals(
                "S-1002: 300",
                lines[2]
        );

        assertEquals(
                "S-1005: 400",
                lines[3]
        );

        assertEquals(
                "S-1003: 500",
                lines[4]
        );
    }

    private Bank createBankWithSixAccounts() {
        Bank bank = new Bank();

        long[] balances = {
                100,
                600,
                300,
                500,
                200,
                400
        };

        for (long balance : balances) {
            String id =
                    bank.createSavingsAccount();

            bank.deposit(
                    id,
                    1,
                    balance
            );
        }

        return bank;
    }

    private String captureOutput(
            Runnable action
    ) {
        PrintStream originalOut =
                System.out;

        ByteArrayOutputStream buffer =
                new ByteArrayOutputStream();

        try {
            System.setOut(
                    new PrintStream(buffer)
            );

            action.run();

        } finally {
            System.setOut(originalOut);
        }

        return buffer.toString();
    }
}