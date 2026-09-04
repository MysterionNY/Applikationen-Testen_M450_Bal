package ch.schule.bank.junit5;

import ch.schule.Booking;
import org.junit.jupiter.api.Test;
import ch.schule.BankUtils;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTests {

    @Test
    public void testInitialization() {
        Booking booking = new Booking(360, 250000);

        assertEquals(360, booking.getDate());
        assertEquals(250000, booking.getAmount());
    }

    @Test
    public void testPrint() {
        Booking booking = new Booking(0, 100000);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer =
                new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(buffer));

            booking.print(200000);

        } finally {
            System.setOut(originalOut);
        }

        String output = buffer.toString();

        String expected =
                BankUtils.formatBankDate(0)
                        + " "
                        + BankUtils.formatAmount(100000)
                        + " "
                        + BankUtils.formatAmount(300000);

        assertTrue(output.contains(expected));
    }
}