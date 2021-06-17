package com.ezequiel.addresses.application;

import com.ezequiel.addresses.domain.Address;
import com.ezequiel.addresses.domain.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ezequiel.addresses.application.AddressControllerTestFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AddressControllerTest {

    private AddressController subject;
    private AddressService addressService;

    @BeforeEach
    public void setUp() {
        addressService = Mockito.mock(AddressService.class);
        subject = new AddressController(addressService, new AddressMapperImpl());
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldCreateOneAddress() {
        given(addressService.createAddress(any(Address.class))).willReturn(newMockedAddress());

        ResponseEntity<?> addressCreatedResponse = subject.createAddress(newMockedAddressRequest());

        assertNotNull(addressCreatedResponse);
        assertNull(addressCreatedResponse.getBody());
        assertNotNull(addressCreatedResponse.getHeaders().getLocation());
        assertEquals(HttpStatus.CREATED, addressCreatedResponse.getStatusCode());

        verify(addressService).createAddress(any(Address.class));
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldCreateOneAddressWithoutLatitudeAndLongitude() {
        given(addressService.createAddress(any(Address.class))).willReturn(newMockedAddressWithoutLatitudeAndLongitude());

        ResponseEntity<?> addressCreatedResponse = subject.createAddress(newMockedAddressRequestWithoutLatitudeAndLongitude());

        assertNotNull(addressCreatedResponse);
        assertNull(addressCreatedResponse.getBody());
        assertNotNull(addressCreatedResponse.getHeaders().getLocation());
        assertEquals(HttpStatus.CREATED, addressCreatedResponse.getStatusCode());

        verify(addressService).createAddress(any(Address.class));
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldUpdateOneAddress() {
        Optional<Address> mockedAddress = Optional.of(newMockedAddress());

        given(addressService.findAddressById(any(UUID.class))).willReturn(mockedAddress);
        given(addressService.updateAddress(any(Address.class))).willReturn(mockedAddress.get());

        ResponseEntity<?> addressUpdatedResponse = subject.updateAddress(UUID.randomUUID(), newMockedAddressRequest());
        assertNotNull(addressUpdatedResponse);
        assertNull(addressUpdatedResponse.getBody());
        assertEquals(HttpStatus.OK, addressUpdatedResponse.getStatusCode());

        verify(addressService).findAddressById(any(UUID.class));
        verify(addressService).updateAddress(mockedAddress.get());
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldDeleteOneAddress() {
        Optional<Address> mockedAddress = Optional.of(newMockedAddress());

        given(addressService.findAddressById(any(UUID.class))).willReturn(mockedAddress);
        doNothing().when(addressService).deleteAddress(mockedAddress.get());

        ResponseEntity<?> addressDeletedResponse = subject.deleteAddressById(UUID.randomUUID());
        assertNotNull(addressDeletedResponse);
        assertNull(addressDeletedResponse.getBody());
        assertEquals(HttpStatus.OK, addressDeletedResponse.getStatusCode());

        verify(addressService).findAddressById(any(UUID.class));
        verify(addressService).deleteAddress(mockedAddress.get());
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldReturnOneAddress() {
        Optional<Address> mockedAddress = Optional.of(newMockedAddress());

        given(addressService.findAddressById(any(UUID.class))).willReturn(mockedAddress);

        ResponseEntity<AddressResponse> addressFoundByIdResponse = subject.findAddressById(UUID.randomUUID());

        assertNotNull(addressFoundByIdResponse);
        assertNotNull(addressFoundByIdResponse.getBody());
        assertEquals(HttpStatus.OK, addressFoundByIdResponse.getStatusCode());
        assertEqualsProperties(mockedAddress.get(), addressFoundByIdResponse.getBody());

        verify(addressService).findAddressById(any(UUID.class));
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldReturnAllAddress() {
        List<Address> mockedAddresses = newMockedListWithTwoAddresses();

        given(addressService.findAllAddresses()).willReturn(mockedAddresses);

        ResponseEntity<List<AddressResponse>> findAllAddressesResponse = subject.findAllAddresses();

        assertNotNull(findAllAddressesResponse);
        assertNotNull(findAllAddressesResponse.getBody());
        assertEquals(HttpStatus.OK, findAllAddressesResponse.getStatusCode());
        assertEquals(mockedAddresses.size(), findAllAddressesResponse.getBody().size());

        assertEqualsProperties(mockedAddresses.get(0), findAllAddressesResponse.getBody().get(0));
        assertEqualsProperties(mockedAddresses.get(1), findAllAddressesResponse.getBody().get(1));

        verify(addressService).findAllAddresses();
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldReturnAddressesByStreetName() {
        List<Address> mockedAddresses = newMockedListWithTwoAddresses();

        given(addressService.findAddressesByStreetName(anyString())).willReturn(mockedAddresses);

        ResponseEntity<List<AddressResponse>> addressesFoundByStreetName = subject.findAddressesByStreetName(anyString());

        assertNotNull(addressesFoundByStreetName);
        assertNotNull(addressesFoundByStreetName.getBody());
        assertEquals(HttpStatus.OK, addressesFoundByStreetName.getStatusCode());
        assertEquals(mockedAddresses.size(), addressesFoundByStreetName.getBody().size());

        assertEqualsProperties(mockedAddresses.get(0), addressesFoundByStreetName.getBody().get(0));
        assertEqualsProperties(mockedAddresses.get(1), addressesFoundByStreetName.getBody().get(1));

        verify(addressService).findAddressesByStreetName(anyString());
        verifyNoMoreInteractions(addressService);
    }

    private void assertEqualsProperties(Address expected, AddressResponse actual) {
        assertEquals(expected.getStreetName(), actual.getStreetName());
        assertEquals(expected.getNumber(), actual.getNumber());
        assertEquals(expected.getComplement(), actual.getComplement());
        assertEquals(expected.getNeighbourhood(), actual.getNeighbourhood());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getState(), actual.getState());
        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getZipcode(), actual.getZipcode());
        assertEquals(expected.getLatitude(), actual.getLatitude());
        assertEquals(expected.getLongitude(), actual.getLongitude());
    }

}
