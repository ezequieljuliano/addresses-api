package com.ezequiel.addresses.infrastructure;

import com.github.javafaker.Faker;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

import java.util.Locale;

public class AddressGoogleMapsAdapterTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static GeocodingResult[] newMockedGeocodingResults() {
        double latitude = Double.parseDouble(faker.address().latitude().replace(",", "."));
        double longitude = Double.parseDouble(faker.address().latitude().replace(",", "."));
        LatLng latLng = new LatLng(latitude, longitude);
        GeocodingResult geocodingResult = new GeocodingResult();
        Geometry geometry = new Geometry();
        geometry.location = latLng;
        geocodingResult.geometry = geometry;
        return new GeocodingResult[]{geocodingResult};
    }

}
