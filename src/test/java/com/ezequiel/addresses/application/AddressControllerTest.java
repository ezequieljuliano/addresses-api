package com.ezequiel.addresses.application;

import com.ezequiel.addresses.domain.Address;
import com.ezequiel.addresses.domain.AddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ezequiel.addresses.helper.AddressTestFixture.newMockedAddress;
import static com.ezequiel.addresses.helper.AddressTestFixture.newMockedAddressRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AddressControllerTest {

    private AddressController subject;
    private AddressService addressService;
    private final AddressMapper addressMapper = new AddressMapperImpl();

    @BeforeEach
    public void setUp() {
        addressService = Mockito.mock(AddressService.class);
        subject = new AddressController(addressService, addressMapper);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldCreateOneAddress() {
        Address address = newMockedAddress();

        given(addressService.createAddress(any(Address.class))).willReturn(address);

        ResponseEntity<?> createAddressResponse = subject.createAddress(newMockedAddressRequest());
        Assertions.assertNotNull(createAddressResponse);
        Assertions.assertNull(createAddressResponse.getBody());
        Assertions.assertNotNull(createAddressResponse.getHeaders().getLocation());
        Assertions.assertEquals(HttpStatus.CREATED, createAddressResponse.getStatusCode());

        verify(addressService).createAddress(any(Address.class));
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldCreateOneAddressWithoutLatitudeAndLongitude() {
        Address address = newMockedAddress();
        address.setLongitude(null);
        address.setLatitude(null);

        given(addressService.createAddress(any(Address.class))).willReturn(address);

        AddressRequest addressRequest = newMockedAddressRequest();
        addressRequest.setLongitude(null);
        addressRequest.setLatitude(null);

        ResponseEntity<?> createAddressResponse = subject.createAddress(addressRequest);
        Assertions.assertNotNull(createAddressResponse);
        Assertions.assertNull(createAddressResponse.getBody());
        Assertions.assertNotNull(createAddressResponse.getHeaders().getLocation());
        Assertions.assertEquals(HttpStatus.CREATED, createAddressResponse.getStatusCode());

        verify(addressService).createAddress(any(Address.class));
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldUpdateOneAddress() {
        Optional<Address> address = Optional.of(newMockedAddress());

        given(addressService.findAddressById(any(UUID.class))).willReturn(address);
        given(addressService.updateAddress(any(Address.class))).willReturn(address.get());

        ResponseEntity<?> updateAddressResponse = subject.updateAddress(UUID.randomUUID(), newMockedAddressRequest());
        Assertions.assertNotNull(updateAddressResponse);
        Assertions.assertNull(updateAddressResponse.getBody());
        Assertions.assertEquals(HttpStatus.OK, updateAddressResponse.getStatusCode());

        verify(addressService).findAddressById(any(UUID.class));
        verify(addressService).updateAddress(address.get());
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldDeleteOneAddress() {
        Optional<Address> address = Optional.of(newMockedAddress());

        given(addressService.findAddressById(any(UUID.class))).willReturn(address);
        doNothing().when(addressService).deleteAddress(address.get());

        ResponseEntity<?> deleteAddressResponse = subject.deleteAddressById(UUID.randomUUID());
        Assertions.assertNotNull(deleteAddressResponse);
        Assertions.assertNull(deleteAddressResponse.getBody());
        Assertions.assertEquals(HttpStatus.OK, deleteAddressResponse.getStatusCode());

        verify(addressService).findAddressById(any(UUID.class));
        verify(addressService).deleteAddress(address.get());
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldReturnOneAddress() {
        Optional<Address> address = Optional.of(newMockedAddress());

        given(addressService.findAddressById(any(UUID.class))).willReturn(address);

        ResponseEntity<AddressResponse> findAddressByIdResponse = subject.findAddressById(UUID.randomUUID());
        Assertions.assertNotNull(findAddressByIdResponse);
        Assertions.assertNotNull(findAddressByIdResponse.getBody());
        Assertions.assertEquals(HttpStatus.OK, findAddressByIdResponse.getStatusCode());
        assertEqualsProperties(address.get(), findAddressByIdResponse.getBody());

        verify(addressService).findAddressById(any(UUID.class));
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldReturnAllAddress() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(newMockedAddress());
        addresses.add(newMockedAddress());

        given(addressService.findAllAddresses()).willReturn(addresses);

        ResponseEntity<List<AddressResponse>> findAllAddressesResponse = subject.findAllAddresses();
        Assertions.assertNotNull(findAllAddressesResponse);
        Assertions.assertNotNull(findAllAddressesResponse.getBody());
        Assertions.assertEquals(HttpStatus.OK, findAllAddressesResponse.getStatusCode());
        Assertions.assertEquals(2, findAllAddressesResponse.getBody().size());

        assertEqualsProperties(addresses.get(0), findAllAddressesResponse.getBody().get(0));
        assertEqualsProperties(addresses.get(1), findAllAddressesResponse.getBody().get(1));

        verify(addressService).findAllAddresses();
        verifyNoMoreInteractions(addressService);
    }

    @Test
    public void shouldReturnAddressesByStreetName() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(newMockedAddress());
        addresses.add(newMockedAddress());

        given(addressService.findAddressesByStreetName(anyString())).willReturn(addresses);

        ResponseEntity<List<AddressResponse>> findAddressesByStreetName = subject.findAddressesByStreetName(anyString());
        Assertions.assertNotNull(findAddressesByStreetName);
        Assertions.assertNotNull(findAddressesByStreetName.getBody());
        Assertions.assertEquals(HttpStatus.OK, findAddressesByStreetName.getStatusCode());
        Assertions.assertEquals(2, findAddressesByStreetName.getBody().size());

        assertEqualsProperties(addresses.get(0), findAddressesByStreetName.getBody().get(0));
        assertEqualsProperties(addresses.get(1), findAddressesByStreetName.getBody().get(1));

        verify(addressService).findAddressesByStreetName(anyString());
        verifyNoMoreInteractions(addressService);
    }

    private void assertEqualsProperties(Address expected, AddressResponse actual) {
        Assertions.assertEquals(expected.getStreetName(), actual.getStreetName());
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
        Assertions.assertEquals(expected.getComplement(), actual.getComplement());
        Assertions.assertEquals(expected.getNeighbourhood(), actual.getNeighbourhood());
        Assertions.assertEquals(expected.getCity(), actual.getCity());
        Assertions.assertEquals(expected.getState(), actual.getState());
        Assertions.assertEquals(expected.getCountry(), actual.getCountry());
        Assertions.assertEquals(expected.getZipcode(), actual.getZipcode());
        Assertions.assertEquals(expected.getLatitude(), actual.getLatitude());
        Assertions.assertEquals(expected.getLongitude(), actual.getLongitude());
    }

}
