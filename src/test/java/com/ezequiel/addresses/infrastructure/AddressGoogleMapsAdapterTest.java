package com.ezequiel.addresses.infrastructure;

import com.ezequiel.addresses.domain.AddressGeocoding;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static com.ezequiel.addresses.infrastructure.AddressGoogleMapsAdapterTestFixture.newMockedGeocodingResults;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
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
        GeocodingResult[] mockedGeocodingResults = newMockedGeocodingResults();

        given(geocodingApiRequest.address(anyString())).willReturn(geocodingApiRequest);
        given(geocodingApiRequest.await()).willReturn(mockedGeocodingResults);

        try (MockedStatic<GeocodingApi> mockedGeocodingApi = Mockito.mockStatic(GeocodingApi.class)) {
            mockedGeocodingApi.when(() -> GeocodingApi.newRequest(eq(geoApiContext))).thenReturn(geocodingApiRequest);
            AddressGeocoding geocodingFoundByDescription = subject.findGeocodingByDescription(anyString());
            assertNotNull(geocodingFoundByDescription);
            assertEquals(mockedGeocodingResults[0].geometry.location.lat, geocodingFoundByDescription.getLatitude());
            assertEquals(mockedGeocodingResults[0].geometry.location.lng, geocodingFoundByDescription.getLongitude());
        }

        verify(geocodingApiRequest).address(anyString());
        verify(geocodingApiRequest).await();
        verifyNoMoreInteractions(geocodingApiRequest);
    }

}
