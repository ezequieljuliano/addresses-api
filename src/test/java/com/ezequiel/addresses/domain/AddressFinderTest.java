package com.ezequiel.addresses.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.ezequiel.addresses.domain.AddressFinderTestFixture.newMockedAddressGeocoding;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AddressFinderTest {

    private AddressFinder subject;

    @BeforeEach
    public void setUp() {
        subject = Mockito.mock(AddressFinder.class);
    }

    @Test
    public void shouldReturnGeocodingByDescription() {
        AddressGeocoding mockedAddressGeocoding = newMockedAddressGeocoding();

        given(subject.findGeocodingByDescription(anyString())).willReturn(mockedAddressGeocoding);

        AddressGeocoding addressGeocodingFoundByDescription = subject.findGeocodingByDescription(anyString());

        assertNotNull(addressGeocodingFoundByDescription);
        assertEquals(mockedAddressGeocoding.getLatitude(), addressGeocodingFoundByDescription.getLatitude());
        assertEquals(mockedAddressGeocoding.getLongitude(), addressGeocodingFoundByDescription.getLongitude());

        verify(subject).findGeocodingByDescription(anyString());
        verifyNoMoreInteractions(subject);
    }

}
