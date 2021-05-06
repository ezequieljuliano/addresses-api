package com.ezequiel.addresses.application;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddressRequest implements Serializable {

    @NotEmpty
    @Size(max = 255)
    private String streetName;

    @NotEmpty
    @Size(max = 255)
    private String number;

    @Size(max = 255)
    private String complement;

    @NotEmpty
    @Size(max = 255)
    private String neighbourhood;

    @NotEmpty
    @Size(max = 255)
    private String city;

    @NotEmpty
    @Size(max = 255)
    private String state;

    @NotEmpty
    @Size(max = 255)
    private String country;

    @NotEmpty
    @Size(max = 255)
    private String zipcode;

    private Double latitude;

    private Double longitude;

}
