package com.ezequiel.addresses.domain;

import com.github.javafaker.Faker;

import java.util.Locale;

public class AddressServiceTestFixture {

    public static Address newMockedAddress() {
        Faker faker = new Faker(new Locale("pt-BR"));
        Address mockedAddress = new Address();
        mockedAddress.setStreetName(faker.address().streetName());
        mockedAddress.setNumber(faker.address().streetAddressNumber());
        mockedAddress.setComplement(faker.address().secondaryAddress());
        mockedAddress.setNeighbourhood(faker.address().streetName());
        mockedAddress.setCity(faker.address().city());
        mockedAddress.setState(faker.address().state());
        mockedAddress.setCountry(faker.address().country());
        mockedAddress.setZipcode(faker.address().zipCode());
        mockedAddress.setLatitude(Double.valueOf(faker.address().latitude().replace(",", ".")));
        mockedAddress.setLongitude(Double.valueOf(faker.address().longitude().replace(",", ".")));
        return mockedAddress;
    }

    public static AddressGeocoding newMockedAddressGeocoding() {
        Faker faker = new Faker(new Locale("pt-BR"));
        AddressGeocoding mockedGeocoding = new AddressGeocoding();
        mockedGeocoding.setLatitude(Double.valueOf(faker.address().latitude().replace(",", ".")));
        mockedGeocoding.setLongitude(Double.valueOf(faker.address().longitude().replace(",", ".")));
        return mockedGeocoding;
    }

}
