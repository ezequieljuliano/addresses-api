package com.ezequiel.addresses.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ezequiel.addresses.helper.AddressTestFixture.newMockedAddress;
import static com.ezequiel.addresses.helper.AddressTestFixture.newMockedAddressGeocoding;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    private AddressService subject;
    private AddressRepository addressRepository;
    private AddressFinder addressFinder;

    @BeforeEach
    public void setUp() {
        addressRepository = Mockito.mock(AddressRepository.class);
        addressFinder = Mockito.mock(AddressFinder.class);
        subject = new AddressService(addressRepository, addressFinder);
    }

    @Test
    public void shouldCreateOneAddress() {
        Address address = newMockedAddress();

        given(addressRepository.save(any(Address.class))).willReturn(address);

        Address createdAddress = subject.createAddress(address);
        Assertions.assertNotNull(createdAddress);

        assertEqualsProperties(address, createdAddress);

        verify(addressRepository).save(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldCreateOneAddressWithoutLatitudeAndLongitude() {
        AddressGeocoding geocoding = newMockedAddressGeocoding();

        Address address = newMockedAddress();
        address.setLongitude(null);
        address.setLongitude(null);

        given(addressFinder.findGeocodingByDescription(anyString())).willReturn(geocoding);
        given(addressRepository.save(any(Address.class))).willReturn(address);

        Address createdAddress = subject.createAddress(address);
        Assertions.assertNotNull(createdAddress);

        assertEqualsProperties(address, createdAddress);

        verify(addressRepository).save(any(Address.class));
        verify(addressFinder).findGeocodingByDescription(anyString());

        verifyNoMoreInteractions(addressRepository);
        verifyNoMoreInteractions(addressFinder);
    }

    @Test
    public void shouldUpdateOneAddress() {
        Address address = newMockedAddress();

        given(addressRepository.save(any(Address.class))).willReturn(address);

        Address updatedAddress = subject.updateAddress(address);
        Assertions.assertNotNull(updatedAddress);

        assertEqualsProperties(address, updatedAddress);

        verify(addressRepository).save(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldDeleteOneAddress() {
        doNothing().when(addressRepository).delete(any(Address.class));

        subject.deleteAddress(newMockedAddress());

        verify(addressRepository).delete(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldReturnOneAddress() {
        Optional<Address> address = Optional.of(newMockedAddress());

        given(addressRepository.findById(any(UUID.class))).willReturn(address);

        Optional<Address> findAddressById = subject.findAddressById(UUID.randomUUID());
        Assertions.assertTrue(findAddressById.isPresent());

        assertEqualsProperties(address.get(), findAddressById.get());

        verify(addressRepository).findById(any(UUID.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldReturnAllAddress() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(newMockedAddress());
        addresses.add(newMockedAddress());

        given(addressRepository.findAll()).willReturn(addresses);

        List<Address> findAllAddresses = subject.findAllAddresses();
        Assertions.assertNotNull(findAllAddresses);
        Assertions.assertEquals(2, findAllAddresses.size());

        assertEqualsProperties(addresses.get(0), findAllAddresses.get(0));
        assertEqualsProperties(addresses.get(1), findAllAddresses.get(1));

        verify(addressRepository).findAll();
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldReturnAddressByStreetName() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(newMockedAddress());
        addresses.add(newMockedAddress());

        given(addressRepository.findByStreetNameIgnoreCase(anyString())).willReturn(addresses);

        List<Address> findAddressesByStreetName = subject.findAddressesByStreetName(anyString());
        Assertions.assertNotNull(findAddressesByStreetName);
        Assertions.assertEquals(2, findAddressesByStreetName.size());

        assertEqualsProperties(addresses.get(0), findAddressesByStreetName.get(0));
        assertEqualsProperties(addresses.get(1), findAddressesByStreetName.get(1));

        verify(addressRepository).findByStreetNameIgnoreCase(anyString());
        verifyNoMoreInteractions(addressRepository);
    }

    private void assertEqualsProperties(Address expected, Address actual) {
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
