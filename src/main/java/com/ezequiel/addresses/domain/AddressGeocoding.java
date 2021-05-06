package com.ezequiel.addresses.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressGeocoding implements Serializable {

    private Double latitude;
    private Double longitude;

}
