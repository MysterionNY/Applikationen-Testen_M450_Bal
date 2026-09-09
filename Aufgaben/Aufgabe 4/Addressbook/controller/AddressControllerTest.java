package ch.tbz.m450.controller;

import ch.tbz.m450.repository.Address;
import ch.tbz.m450.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    private AddressController addressController;
    private Address address;

    @BeforeEach
    void setUp() {
        addressController = new AddressController(addressService);
        address = new Address(1, "Mert", "Bal", "0791234567", new Date(1_000));
    }

    @Test
    void shouldCreateAddressAndReturnStatus201() {
        when(addressService.save(address)).thenReturn(address);

        ResponseEntity<Address> response = addressController.createAddress(address);

        assertEquals(201, response.getStatusCode().value());
        assertSame(address, response.getBody());
        verify(addressService).save(address);
    }

    @Test
    void shouldReturnAllAddressesWithStatus200() {
        List<Address> addresses = List.of(address);
        when(addressService.getAll()).thenReturn(addresses);

        ResponseEntity<List<Address>> response = addressController.getAddresses();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(addresses, response.getBody());
        verify(addressService).getAll();
    }

    @Test
    void shouldReturnAddressWithStatus200WhenItExists() {
        when(addressService.getAddress(1)).thenReturn(Optional.of(address));

        ResponseEntity<Address> response = addressController.getAddress(1);

        assertEquals(200, response.getStatusCode().value());
        assertSame(address, response.getBody());
        verify(addressService).getAddress(1);
    }

    @Test
    void shouldReturnStatus404WhenAddressDoesNotExist() {
        when(addressService.getAddress(999)).thenReturn(Optional.empty());

        ResponseEntity<Address> response = addressController.getAddress(999);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(addressService).getAddress(999);
    }
}
