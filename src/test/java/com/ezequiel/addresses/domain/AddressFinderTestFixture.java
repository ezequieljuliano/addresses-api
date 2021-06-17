package com.ezequiel.addresses.domain;

import com.github.javafaker.Faker;

import java.util.Locale;

public class AddressFinderTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static AddressGeocoding newMockedAddressGeocoding() {
        AddressGeocoding mockedAddressGeocoding = new AddressGeocoding();
        mockedAddressGeocoding.setLatitude(Double.valueOf(faker.address().latitude().replace(",", ".")));
        mockedAddressGeocoding.setLongitude(Double.valueOf(faker.address().longitude().replace(",", ".")));
        return mockedAddressGeocoding;
    }

}
