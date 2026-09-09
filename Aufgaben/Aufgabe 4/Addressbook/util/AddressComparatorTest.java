package ch.tbz.m450.util;

import ch.tbz.m450.repository.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ch.tbz.m450.util.AddressComparator.SortField.FIRSTNAME;
import static ch.tbz.m450.util.AddressComparator.SortField.REGISTRATION_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressComparatorTest {

    private Address mertBal;
    private Address berndZehner;
    private Address weyoAller;

    @BeforeEach
    void setUp() {
        mertBal = new Address(3, "Mert", "Bal", "0793333333", new Date(3_000));
        berndZehner = new Address(2, "Bernd", "Zehner", "0792222222", new Date(2_000));
        weyoAller = new Address(1, "Weyo", "Aller", "0791111111", new Date(1_000));
    }

    @Test
    void shouldSortByLastnameThenFirstnameByDefault() {
        List<Address> addresses = new ArrayList<>(List.of(berndZehner, mertBal, weyoAller));

        addresses.sort(new AddressComparator());

        assertEquals(List.of(weyoAller, mertBal, berndZehner), addresses);
    }

    @Test
    void shouldCompareEqualLastnameByFirstname() {
        AddressComparator comparator = new AddressComparator();

        assertTrue(comparator.compare(mertBal, berndZehner) < 0);
        assertTrue(comparator.compare(berndZehner, mertBal) > 0);
    }

    @Test
    void shouldSortByFirstnameWhenConfigured() {
        List<Address> addresses = new ArrayList<>(List.of(weyoAller, berndZehner, mertBal));

        addresses.sort(new AddressComparator(FIRSTNAME));

        assertEquals(List.of(mertBal, berndZehner, weyoAller), addresses);
    }

    @Test
    void shouldSortByRegistrationDateWhenConfigured() {
        List<Address> addresses = new ArrayList<>(List.of(mertBal, weyoAller, berndZehner));

        addresses.sort(new AddressComparator(REGISTRATION_DATE));

        assertEquals(List.of(weyoAller, berndZehner, mertBal), addresses);
    }
}
