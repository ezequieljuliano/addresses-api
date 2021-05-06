package com.ezequiel.addresses.application;

import com.ezequiel.addresses.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface AddressConverter {

    AddressResponse createAddressResponseFrom(Address address);

    Address createAddressFrom(AddressRequest addressRequest);

    void updateAddress(AddressRequest addressRequest, @MappingTarget Address address);

}
