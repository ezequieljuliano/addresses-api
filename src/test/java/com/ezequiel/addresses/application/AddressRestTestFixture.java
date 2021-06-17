package com.ezequiel.addresses.application;

import com.ezequiel.addresses.domain.Address;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddressRestTestFixture {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static Address newMockedAddress() {
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

    public static List<Address> newMockedListWithTwoAddresses() {
        List<Address> mockedListAddresses = new ArrayList<>();
        mockedListAddresses.add(newMockedAddress());
        mockedListAddresses.add(newMockedAddress());
        return mockedListAddresses;
    }

    public static AddressRequest newMockedAddressRequest() {
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

    public static AddressRequest newMockedAddressRequestWithoutLatitudeAndLongitude() {
        AddressRequest mockedAddressRequest = newMockedAddressRequest();
        mockedAddressRequest.setLatitude(null);
        mockedAddressRequest.setLongitude(null);
        return mockedAddressRequest;
    }

}
