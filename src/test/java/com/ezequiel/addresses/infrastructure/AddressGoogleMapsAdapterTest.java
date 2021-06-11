package com.ezequiel.addresses.infrastructure;

import com.ezequiel.addresses.domain.AddressGeocoding;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static com.ezequiel.addresses.infrastructure.AddressGoogleMapsAdapterTestFixture.newMockedGeocodingResults;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AddressGoogleMapsAdapterTest {

    private AddressGoogleMapsAdapter subject;
    private GeoApiContext geoApiContext;
    private GeocodingApiRequest geocodingApiRequest;

    @BeforeEach
    public void setUp() {
        geoApiContext = Mockito.mock(GeoApiContext.class);
        geocodingApiRequest = Mockito.mock(GeocodingApiRequest.class);
        subject = new AddressGoogleMapsAdapter(geoApiContext);
    }

    @Test
    public void shouldReturnGeocodingByDescription() throws Exception {
        GeocodingResult[] geocodingResults = newMockedGeocodingResults();

        when(geocodingApiRequest.address(anyString())).thenReturn(geocodingApiRequest);
        when(geocodingApiRequest.await()).thenReturn(geocodingResults);

        try (MockedStatic<GeocodingApi> mockedGeocodingApi = Mockito.mockStatic(GeocodingApi.class)) {
            mockedGeocodingApi.when(() -> GeocodingApi.newRequest(eq(geoApiContext))).thenReturn(geocodingApiRequest);
            AddressGeocoding geocoding = subject.findGeocodingByDescription(anyString());
            Assertions.assertNotNull(geocoding);
            Assertions.assertEquals(geocodingResults[0].geometry.location.lat, geocoding.getLatitude());
            Assertions.assertEquals(geocodingResults[0].geometry.location.lng, geocoding.getLongitude());
        }

        verify(geocodingApiRequest).address(anyString());
        verify(geocodingApiRequest).await();
        verifyNoMoreInteractions(geocodingApiRequest);
    }

}
