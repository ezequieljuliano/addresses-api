package com.ezequiel.addresses.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static com.ezequiel.addresses.helper.AddressTestFixture.newMockedAddress;

@DataJpaTest
@ActiveProfiles("test")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository subject;

    @AfterEach
    public void tearDown() {
        subject.deleteAll();
    }

    @Test
    public void shouldReturnAddressesByStreetNameIgnoreCase() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(newMockedAddress());
        addresses.add(newMockedAddress());
        subject.saveAll(addresses);

        List<Address> addressesByStreetNameIgnoreCase = subject.findByStreetNameIgnoreCase(addresses.get(0).getStreetName());
        Assertions.assertNotNull(addressesByStreetNameIgnoreCase);
        Assertions.assertFalse(addressesByStreetNameIgnoreCase.isEmpty());
        Assertions.assertTrue(addressesByStreetNameIgnoreCase.stream().anyMatch(a -> a.getStreetName().equals(addresses.get(0).getStreetName())));
    }

}
