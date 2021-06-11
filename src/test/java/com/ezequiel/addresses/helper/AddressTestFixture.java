package com.ezequiel.addresses.helper;

import com.ezequiel.addresses.application.AddressRequest;
import com.ezequiel.addresses.domain.Address;
import com.ezequiel.addresses.domain.AddressGeocoding;
import com.github.javafaker.Faker;

import java.util.Locale;

public class AddressTestFixture {

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

    public static AddressRequest newMockedAddressRequest() {
        Faker faker = new Faker(new Locale("pt-BR"));
        AddressRequest mockedAddressRequest = new AddressRequest();
        mockedAddressRequest.setStreetName(faker.address().streetName());
        mockedAddressRequest.setNumber(faker.address().streetAddressNumber());
        mockedAddressRequest.setComplement(faker.address().secondaryAddress());
        mockedAddressRequest.setNeighbourhood(faker.address().streetName());
        mockedAddressRequest.setCity(faker.address().city());
        mockedAddressRequest.setState(faker.address().state());
        mockedAddressRequest.setCountry(faker.address().country());
        mockedAddressRequest.setZipcode(faker.address().zipCode());
        mockedAddressRequest.setLatitude(Double.valueOf(faker.address().latitude().replace(",", ".")));
        mockedAddressRequest.setLongitude(Double.valueOf(faker.address().longitude().replace(",", ".")));
        return mockedAddressRequest;
    }

    public static AddressGeocoding newMockedAddressGeocoding() {
        Faker faker = new Faker(new Locale("pt-BR"));
        AddressGeocoding mockedGeocoding = new AddressGeocoding();
        mockedGeocoding.setLatitude(Double.valueOf(faker.address().latitude().replace(",", ".")));
        mockedGeocoding.setLongitude(Double.valueOf(faker.address().longitude().replace(",", ".")));
        return mockedGeocoding;
    }

}
