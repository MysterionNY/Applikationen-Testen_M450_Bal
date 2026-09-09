package ch.tbz.m450.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AddressTest {

    private Date registrationDate;
    private Address address;

    @BeforeEach
    void setUp() {
        registrationDate = new Date(1_700_000_000_000L);
        address = new Address(1, "Mert", "Bal", "0791234567", registrationDate);
    }

    @Test
    void shouldCreateAddressWithAllValues() {
        assertEquals(1, address.getId());
        assertEquals("Mert", address.getFirstname());
        assertEquals("Bal", address.getLastname());
        assertEquals("0791234567", address.getPhonenumber());
        assertEquals(registrationDate, address.getRegistrationDate());
    }

    @Test
    void shouldCreateAddressWithNoArgsConstructorAndSetValues() {
        Address emptyAddress = new Address();

        emptyAddress.setId(2);
        emptyAddress.setFirstname("Max");
        emptyAddress.setLastname("Muster");
        emptyAddress.setPhonenumber("0780000000");
        emptyAddress.setRegistrationDate(registrationDate);

        assertNotNull(emptyAddress);
        assertEquals(2, emptyAddress.getId());
        assertEquals("Max", emptyAddress.getFirstname());
        assertEquals("Muster", emptyAddress.getLastname());
        assertEquals("0780000000", emptyAddress.getPhonenumber());
        assertEquals(registrationDate, emptyAddress.getRegistrationDate());
    }
}
