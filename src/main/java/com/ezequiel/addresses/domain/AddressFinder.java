package com.ezequiel.addresses.domain;

public interface AddressFinder {

    AddressGeocoding findGeocodingByDescription(String addressDescription);

}
