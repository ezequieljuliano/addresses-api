package com.ezequiel.addresses.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.ezequiel.addresses.domain.AddressRepositoryTestFixture.newMockedListWithTwoAddresses;
import static org.junit.jupiter.api.Assertions.*;

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
        List<Address> mockedAddresses = newMockedListWithTwoAddresses();
        subject.saveAll(mockedAddresses);

        String streetNameSavedFromFirstAddress = mockedAddresses.get(0).getStreetName();

        List<Address> addressesByStreetNameIgnoreCase = subject.findByStreetNameIgnoreCase(streetNameSavedFromFirstAddress);

        assertNotNull(addressesByStreetNameIgnoreCase);
        assertFalse(addressesByStreetNameIgnoreCase.isEmpty());
        assertTrue(addressesByStreetNameIgnoreCase.stream().anyMatch(a -> a.getStreetName().equals(streetNameSavedFromFirstAddress)));
    }

}
