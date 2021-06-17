package com.ezequiel.addresses.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ezequiel.addresses.domain.AddressServiceTestFixture.*;
import static org.junit.jupiter.api.Assertions.*;
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
        Address mockedAddress = newMockedAddress();

        given(addressRepository.save(any(Address.class))).willReturn(mockedAddress);

        Address createdAddress = subject.createAddress(mockedAddress);
        assertNotNull(createdAddress);

        assertEqualsProperties(mockedAddress, createdAddress);

        verify(addressRepository).save(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldCreateOneAddressWithoutLatitudeAndLongitude() {
        AddressGeocoding mockedGeocoding = newMockedAddressGeocoding();
        Address mockedAddressWithoutLatitudeAndLongitude = newMockedAddressWithoutLatitudeAndLongitude();

        given(addressFinder.findGeocodingByDescription(anyString())).willReturn(mockedGeocoding);
        given(addressRepository.save(any(Address.class))).willReturn(mockedAddressWithoutLatitudeAndLongitude);

        Address createdAddress = subject.createAddress(mockedAddressWithoutLatitudeAndLongitude);
        assertNotNull(createdAddress);

        assertEqualsProperties(mockedAddressWithoutLatitudeAndLongitude, createdAddress);

        verify(addressRepository).save(any(Address.class));
        verify(addressFinder).findGeocodingByDescription(anyString());

        verifyNoMoreInteractions(addressRepository);
        verifyNoMoreInteractions(addressFinder);
    }

    @Test
    public void shouldUpdateOneAddress() {
        Address mockedAddress = newMockedAddress();

        given(addressRepository.save(any(Address.class))).willReturn(mockedAddress);

        Address updatedAddress = subject.updateAddress(mockedAddress);
        assertNotNull(updatedAddress);

        assertEqualsProperties(mockedAddress, updatedAddress);

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
        Optional<Address> mockedAddress = Optional.of(newMockedAddress());

        given(addressRepository.findById(any(UUID.class))).willReturn(mockedAddress);

        Optional<Address> addressFoundById = subject.findAddressById(UUID.randomUUID());
        assertTrue(addressFoundById.isPresent());

        assertEqualsProperties(mockedAddress.get(), addressFoundById.get());

        verify(addressRepository).findById(any(UUID.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldReturnAllAddress() {
        List<Address> mockedAddresses = newMockedListWithTwoAddresses();

        given(addressRepository.findAll()).willReturn(mockedAddresses);

        List<Address> findAllAddresses = subject.findAllAddresses();
        assertNotNull(findAllAddresses);
        assertEquals(mockedAddresses.size(), findAllAddresses.size());

        assertEqualsProperties(mockedAddresses.get(0), findAllAddresses.get(0));
        assertEqualsProperties(mockedAddresses.get(1), findAllAddresses.get(1));

        verify(addressRepository).findAll();
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldReturnAddressByStreetName() {
        List<Address> mockedAddresses = newMockedListWithTwoAddresses();

        given(addressRepository.findByStreetNameIgnoreCase(anyString())).willReturn(mockedAddresses);

        List<Address> findAddressesByStreetName = subject.findAddressesByStreetName(anyString());
        assertNotNull(findAddressesByStreetName);
        assertEquals(mockedAddresses.size(), findAddressesByStreetName.size());

        assertEqualsProperties(mockedAddresses.get(0), findAddressesByStreetName.get(0));
        assertEqualsProperties(mockedAddresses.get(1), findAddressesByStreetName.get(1));

        verify(addressRepository).findByStreetNameIgnoreCase(anyString());
        verifyNoMoreInteractions(addressRepository);
    }

    private void assertEqualsProperties(Address expected, Address actual) {
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
