package com.ezequiel.addresses.application;

import com.ezequiel.addresses.domain.Address;
import com.ezequiel.addresses.domain.AddressRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ezequiel.addresses.application.AddressRestTestFixture.newMockedAddress;
import static com.ezequiel.addresses.application.AddressRestTestFixture.newMockedAddressRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AddressRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    public void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    public void shouldCreateOneAddress() {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newMockedAddressRequest())
                .when()
                .post(String.format("http://localhost:%s/addresses-api/v1/addresses", port))
                .then()
                .statusCode(is(HttpStatus.CREATED.value()));
    }

    @Test
    public void shouldCreateOneAddressWithoutLatitudeAndLongitude() {
        AddressRequest addressRequest = newMockedAddressRequest();
        addressRequest.setLongitude(null);
        addressRequest.setLatitude(null);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(addressRequest)
                .when()
                .post(String.format("http://localhost:%s/addresses-api/v1/addresses", port))
                .then()
                .statusCode(is(HttpStatus.CREATED.value()));
    }

    @Test
    public void shouldUpdateOneAddress() {
        UUID addressId = addressRepository.save(newMockedAddress()).getId();
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newMockedAddressRequest())
                .when()
                .put(String.format("http://localhost:%s/addresses-api/v1/addresses/%s", port, addressId))
                .then()
                .statusCode(is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldDeleteOneAddress() {
        UUID addressId = addressRepository.save(newMockedAddress()).getId();
        when()
                .delete(String.format("http://localhost:%s/addresses-api/v1/addresses/%s", port, addressId))
                .then()
                .statusCode(is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldReturnOneAddress() {
        UUID addressId = addressRepository.save(newMockedAddress()).getId();
        when()
                .get(String.format("http://localhost:%s/addresses-api/v1/addresses/%s", port, addressId))
                .then()
                .statusCode(is(HttpStatus.OK.value()))
                .body(notNullValue());
    }

    @Test
    public void shouldReturnAllAddress() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(newMockedAddress());
        addresses.add(newMockedAddress());
        addressRepository.saveAll(addresses);
        when()
                .get(String.format("http://localhost:%s/addresses-api/v1/addresses", port))
                .then()
                .statusCode(is(HttpStatus.OK.value()))
                .body(notNullValue());
    }

    @Test
    public void shouldReturnAddressesByStreetName() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(newMockedAddress());
        addresses.add(newMockedAddress());
        addressRepository.saveAll(addresses);
        when()
                .get(String.format("http://localhost:%s/addresses-api/v1/addresses/search?streetName=%s", port, addresses.get(0).getStreetName()))
                .then()
                .statusCode(is(HttpStatus.OK.value()))
                .body(notNullValue());
    }

}
