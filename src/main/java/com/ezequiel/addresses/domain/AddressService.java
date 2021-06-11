package com.ezequiel.addresses.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressFinder addressFinder;

    public Address createAddress(Address address) {
        if (!containsLatitudeAndLongitude(address)) {
            AddressGeocoding geocoding = addressFinder.findGeocodingByDescription(getAddressDescription(address));
            if (geocoding != null) {
                address.setLatitude(geocoding.getLatitude());
                address.setLongitude(geocoding.getLongitude());
            }
        }
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Address address) {
        addressRepository.delete(address);
    }

    public Optional<Address> findAddressById(UUID id) {
        return addressRepository.findById(id);
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> findAddressesByStreetName(String streetName) {
        return addressRepository.findByStreetNameIgnoreCase(streetName);
    }

    private String getAddressDescription(Address address) {
        return address.getStreetName().concat(", ")
                .concat(address.getNumber()).concat(" - ")
                .concat(address.getNeighbourhood()).concat(", ")
                .concat(address.getCity()).concat(" - ")
                .concat(address.getState()).concat(", ")
                .concat(address.getCountry());
    }

    private boolean containsLatitudeAndLongitude(Address address) {
        return (address.getLatitude() != null) &&
                (address.getLongitude() != null) &&
                (address.getLatitude() > 0) &&
                (address.getLongitude() > 0);
    }

}
