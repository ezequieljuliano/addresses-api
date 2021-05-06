package com.ezequiel.addresses.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Data
public class Address implements Serializable {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(columnDefinition = "uuid", name = "id", updatable = false)
    private UUID id;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "street_name")
    private String streetName;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "number")
    private String number;

    @Size(max = 255)
    @Column(name = "complement")
    private String complement;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "neighbourhood")
    private String neighbourhood;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "city")
    private String city;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "state")
    private String state;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "country")
    private String country;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.NONE)
    @Column(name = "updated_at", insertable = false)
    private Date updatedAt;

}
