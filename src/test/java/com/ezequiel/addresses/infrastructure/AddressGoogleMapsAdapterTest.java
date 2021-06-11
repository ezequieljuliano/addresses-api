package com.ezequiel.addresses.infrastructure;

import com.ezequiel.addresses.domain.AddressGeocoding;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AddressGoogleMapsAdapterTest {

    private static final Double LATITUDE = -26.766186557743694;
    private static final Double LONGITUDE = -53.18144004582265;

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
        LatLng latLng = new LatLng(LATITUDE, LONGITUDE);

        GeocodingResult geocodingResult = new GeocodingResult();
        Geometry geometry = new Geometry();
        geometry.location = latLng;
        geocodingResult.geometry = geometry;

        GeocodingResult[] geocodingResults = new GeocodingResult[]{geocodingResult};

        when(geocodingApiRequest.address(anyString())).thenReturn(geocodingApiRequest);
        when(geocodingApiRequest.await()).thenReturn(geocodingResults);

        try (MockedStatic<GeocodingApi> mockedGeocodingApi = Mockito.mockStatic(GeocodingApi.class)) {
            mockedGeocodingApi.when(() -> GeocodingApi.newRequest(eq(geoApiContext))).thenReturn(geocodingApiRequest);
            AddressGeocoding geocoding = subject.findGeocodingByDescription(anyString());
            Assertions.assertNotNull(geocoding);
            Assertions.assertEquals(LATITUDE, geocoding.getLatitude());
            Assertions.assertEquals(LONGITUDE, geocoding.getLongitude());
        }

        verify(geocodingApiRequest).address(anyString());
        verify(geocodingApiRequest).await();
        verifyNoMoreInteractions(geocodingApiRequest);
    }

}
