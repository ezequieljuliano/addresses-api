package com.ezequiel.addresses.application;

import com.ezequiel.addresses.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface AddressMapper {

    AddressResponse createAddressResponse(Address address);

    Address createAddress(AddressRequest addressRequest);

    void updateAddress(AddressRequest addressRequest, @MappingTarget Address address);

}
