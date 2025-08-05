package com.garage.backend.address.repository;

import com.garage.backend.address.entity.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressesRepository extends JpaRepository<Addresses, UUID> {

    // Find addresses by city and state
    List<Addresses> findByCityAndState(String city, String state);

    // Find addresses by postal code
    List<Addresses> findByPostalCode(String postalCode);

    // Find addresses by country
    List<Addresses> findByCountry(String country);

    // Search addresses by city containing text
    List<Addresses> findByCityContainingIgnoreCase(String city);

    // Search addresses by state containing text
    List<Addresses> findByStateContainingIgnoreCase(String state);

    // Custom query to find addresses with specific criteria
    @Query("SELECT a FROM Addresses a WHERE " +
           "(:city IS NULL OR a.city = :city) AND " +
           "(:state IS NULL OR a.state = :state) AND " +
           "(:country IS NULL OR a.country = :country)")
    List<Addresses> findAddressesByCriteria(@Param("city") String city,
                                           @Param("state") String state,
                                           @Param("country") String country);

    // Check if address exists with same details
    boolean existsByStreetAddressAndCityAndStateAndPostalCodeAndCountry(
            String streetAddress, String city, String state, String postalCode, String country);
}
