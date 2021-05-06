package com.ezequiel.addresses.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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

    public void updateAddress(Address address) {
        addressRepository.save(address);
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
