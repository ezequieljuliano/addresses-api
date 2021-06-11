package com.ezequiel.addresses.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AddressFinderTest {

    private static final Double LATITUDE = -26.766186557743694;
    private static final Double LONGITUDE = -53.18144004582265;

    private AddressFinder subject;

    @BeforeEach
    public void setUp() {
        subject = Mockito.mock(AddressFinder.class);
    }

    @Test
    public void shouldReturnGeocodingByDescription() {
        AddressGeocoding geocoding = new AddressGeocoding(LATITUDE, LONGITUDE);

        when(subject.findGeocodingByDescription(anyString())).thenReturn(geocoding);

        AddressGeocoding geocodingByDescription = subject.findGeocodingByDescription(anyString());

        Assertions.assertNotNull(geocodingByDescription);
        Assertions.assertEquals(LATITUDE, geocodingByDescription.getLatitude());
        Assertions.assertEquals(LONGITUDE, geocodingByDescription.getLongitude());

        verify(subject).findGeocodingByDescription(anyString());
        verifyNoMoreInteractions(subject);
    }

}
