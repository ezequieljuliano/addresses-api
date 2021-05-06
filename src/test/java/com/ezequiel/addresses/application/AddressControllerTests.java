package com.ezequiel.addresses.application;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerTests {

    private static final String HOST = "http://localhost:";
    private static final String ADDRESSES_RESOURCE = "/addresses-api/v1/addresses";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAddress() {
        ResponseEntity<?> createResponse = createAddress(createAddressContent());
        Assertions.assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        Assertions.assertTrue(createResponse.getHeaders().containsKey("Location"));
        Assertions.assertEquals(HttpStatus.OK, findAddressById(createResponse.getHeaders().getLocation()).getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, deleteAddressById(createResponse.getHeaders().getLocation()).getStatusCode());
    }

    @Test
    public void testCreateAddressWithoutLatitudeAndLongitude() {
        ResponseEntity<?> createResponse = createAddress(createAddressContentWithoutLatitudeAndLongitude());
        Assertions.assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        Assertions.assertTrue(createResponse.getHeaders().containsKey("Location"));
        ResponseEntity<AddressResponse> findResponse = findAddressById(createResponse.getHeaders().getLocation());
        Assertions.assertEquals(HttpStatus.OK, findResponse.getStatusCode());
        Assertions.assertNotNull(findResponse.getBody());
        Assertions.assertNotNull(findResponse.getBody().getId());
        Assertions.assertEquals(37.422416, findResponse.getBody().getLatitude());
        Assertions.assertEquals(-122.0838694, findResponse.getBody().getLongitude());
        Assertions.assertEquals(HttpStatus.OK, deleteAddressById(createResponse.getHeaders().getLocation()).getStatusCode());
    }

    @Test
    public void testUpdateAddress() {
        ResponseEntity<?> createResponse = createAddress(createAddressContent());
        Assertions.assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        ResponseEntity<AddressResponse> findResponse;
        findResponse = findAddressById(createResponse.getHeaders().getLocation());
        Assertions.assertEquals(HttpStatus.OK, findResponse.getStatusCode());
        Assertions.assertNotNull(findResponse.getBody());
        Assertions.assertNotNull(findResponse.getBody().getId());
        AddressRequest updatedContent = updateAddressContent(new AddressRequest());
        ResponseEntity<?> updateResponse = updateAddress(findResponse.getBody().getId(), updatedContent);
        Assertions.assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        findResponse = findAddressById(createResponse.getHeaders().getLocation());
        Assertions.assertEquals(HttpStatus.OK, findResponse.getStatusCode());
        Assertions.assertNotNull(findResponse.getBody());
        Assertions.assertNotNull(findResponse.getBody().getId());
        Assertions.assertEquals("New Street Name", findResponse.getBody().getStreetName());
        Assertions.assertEquals("New Number", findResponse.getBody().getNumber());
        Assertions.assertEquals("New Complement", findResponse.getBody().getComplement());
        Assertions.assertEquals("New Neighbourhood", findResponse.getBody().getNeighbourhood());
        Assertions.assertEquals("New City", findResponse.getBody().getCity());
        Assertions.assertEquals("New State", findResponse.getBody().getState());
        Assertions.assertEquals("New Country", findResponse.getBody().getCountry());
        Assertions.assertEquals("New Zipcode", findResponse.getBody().getZipcode());
        Assertions.assertEquals(20.0, findResponse.getBody().getLatitude());
        Assertions.assertEquals(10.0, findResponse.getBody().getLongitude());
        Assertions.assertEquals(HttpStatus.OK, deleteAddressById(createResponse.getHeaders().getLocation()).getStatusCode());
    }

    @Test
    public void testDeleteAddressById() {
        ResponseEntity<?> createResponse = createAddress(createAddressContent());
        Assertions.assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, deleteAddressById(createResponse.getHeaders().getLocation()).getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, findAddressById(createResponse.getHeaders().getLocation()).getStatusCode());
    }

    @Test
    public void testFindAddressById() {
        ResponseEntity<?> createResponse = createAddress(createAddressContent());
        Assertions.assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        ResponseEntity<AddressResponse> findResponse = findAddressById(createResponse.getHeaders().getLocation());
        Assertions.assertEquals(HttpStatus.OK, findResponse.getStatusCode());
        Assertions.assertNotNull(findResponse.getBody());
        Assertions.assertNotNull(findResponse.getBody().getId());
        Assertions.assertEquals("Street Name", findResponse.getBody().getStreetName());
        Assertions.assertEquals("Number", findResponse.getBody().getNumber());
        Assertions.assertEquals("Complement", findResponse.getBody().getComplement());
        Assertions.assertEquals("Neighbourhood", findResponse.getBody().getNeighbourhood());
        Assertions.assertEquals("City", findResponse.getBody().getCity());
        Assertions.assertEquals("State", findResponse.getBody().getState());
        Assertions.assertEquals("Country", findResponse.getBody().getCountry());
        Assertions.assertEquals("Zipcode", findResponse.getBody().getZipcode());
        Assertions.assertEquals(2.0, findResponse.getBody().getLatitude());
        Assertions.assertEquals(1.0, findResponse.getBody().getLongitude());
        Assertions.assertEquals(HttpStatus.OK, deleteAddressById(createResponse.getHeaders().getLocation()).getStatusCode());
    }

    @Test
    public void testFindAllAddresses() {
        ResponseEntity<?> createResponse = createAddress(createAddressContent());
        Assertions.assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        ResponseEntity<AddressResponse[]> findAllResponse = findAllAddresses();
        Assertions.assertEquals(HttpStatus.OK, findAllResponse.getStatusCode());
        Assertions.assertNotNull(findAllResponse.getBody());
        Assertions.assertTrue(findAllResponse.getBody().length > 0);
        Assertions.assertNotNull(findAllResponse.getBody()[0].getId());
        Assertions.assertEquals(HttpStatus.OK, deleteAddressById(createResponse.getHeaders().getLocation()).getStatusCode());
    }

    private AddressRequest createAddressContent() {
        AddressRequest content = new AddressRequest();
        content.setStreetName("Street Name");
        content.setNumber("Number");
        content.setComplement("Complement");
        content.setNeighbourhood("Neighbourhood");
        content.setCity("City");
        content.setState("State");
        content.setCountry("Country");
        content.setZipcode("Zipcode");
        content.setLatitude(2.0);
        content.setLongitude(1.0);
        return content;
    }

    private AddressRequest createAddressContentWithoutLatitudeAndLongitude() {
        AddressRequest content = new AddressRequest();
        content.setStreetName("Amphitheatre Parkway");
        content.setNumber("1600");
        content.setComplement("Complement");
        content.setNeighbourhood("Santa Clara County");
        content.setCity("Mountain View,");
        content.setState("California");
        content.setCountry("US");
        content.setZipcode("94043");
        return content;
    }

    private AddressRequest updateAddressContent(AddressRequest content) {
        content.setStreetName("New Street Name");
        content.setNumber("New Number");
        content.setComplement("New Complement");
        content.setNeighbourhood("New Neighbourhood");
        content.setCity("New City");
        content.setState("New State");
        content.setCountry("New Country");
        content.setZipcode("New Zipcode");
        content.setLatitude(20.0);
        content.setLongitude(10.0);
        return content;
    }

    private ResponseEntity<?> createAddress(AddressRequest content) {
        return restTemplate.postForEntity(getUrl(), new HttpEntity<>(content), AddressResponse.class);
    }

    private ResponseEntity<?> updateAddress(UUID id, AddressRequest content) {
        return restTemplate.exchange(getUrl(id), HttpMethod.PUT, new HttpEntity<>(content), AddressResponse.class);
    }

    private ResponseEntity<?> deleteAddressById(URI uri) {
        return restTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(null), AddressResponse.class);
    }

    private ResponseEntity<AddressResponse> findAddressById(URI uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(null), AddressResponse.class);
    }

    private ResponseEntity<AddressResponse[]> findAllAddresses() {
        return restTemplate.exchange(getUrl(), HttpMethod.GET, new HttpEntity<>(null), AddressResponse[].class);
    }

    private String getUrl() {
        return HOST + port + ADDRESSES_RESOURCE;
    }

    private String getUrl(UUID id) {
        return getUrl() + "/" + id.toString();
    }

}
