package com.ezequiel.addresses.infrastructure;

import com.ezequiel.addresses.domain.AddressFinder;
import com.ezequiel.addresses.domain.AddressGeocoding;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AddressGoogleMapsAdapter implements AddressFinder {

    @Value("${google.geocoding.api-key}")
    private String googleGeoApiKey;

    @Override
    @SneakyThrows
    public AddressGeocoding findGeocodingByDescription(String addressDescription) {
        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(googleGeoApiKey).build();
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, addressDescription).await();
        if (results.length > 0) {
            LatLng location = results[0].geometry.location;
            return new AddressGeocoding(location.lat, location.lng);
        }
        return null;
    }

}
