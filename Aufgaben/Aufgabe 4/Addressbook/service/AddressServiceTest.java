package ch.tbz.m450.service;

import ch.tbz.m450.repository.Address;
import ch.tbz.m450.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address address1;
    private Address address2;

    @BeforeEach
    void setUp() {
        address1 = new Address(1, "Mert", "Bal", "0791111111", new Date(1_000));
        address2 = new Address(2, "Max", "Muster", "0792222222", new Date(2_000));
    }

    @Test
    void shouldSaveAddressWithoutUsingRealDatabase() {
        when(addressRepository.save(address1)).thenReturn(address1);

        Address result = addressService.save(address1);

        assertSame(address1, result);
        verify(addressRepository).save(address1);
    }

    @Test
    void shouldReturnAllAddressesSorted() {
        when(addressRepository.findAll()).thenReturn(List.of(address2, address1));

        List<Address> result = addressService.getAll();

        assertEquals(List.of(address1, address2), result);
        verify(addressRepository).findAll();
    }

    @Test
    void shouldReturnAddressWhenIdExists() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address1));

        Optional<Address> result = addressService.getAddress(1);

        assertTrue(result.isPresent());
        assertSame(address1, result.get());
        verify(addressRepository).findById(1);
    }

    @Test
    void shouldReturnEmptyOptionalWhenIdDoesNotExist() {
        when(addressRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Address> result = addressService.getAddress(999);

        assertTrue(result.isEmpty());
        verify(addressRepository).findById(999);
    }
}
