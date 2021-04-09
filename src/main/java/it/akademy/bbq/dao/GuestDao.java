package it.akademy.bbq.dao;

import it.akademy.bbq.models.Barbecue;
import it.akademy.bbq.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestDao extends JpaRepository<Guest, Integer> {
    /* FIND GUEST */
    List<Guest> findAll();
    List<Guest> findAllByFirstname(String  firstname);
    List<Guest> findAllByLastname(String lastname);
    List<Guest> findAllByCity(String city);
    List<Guest> findAllByCountry(String country);

    Guest findById(int id);

    /* CREATE OR UPDATE GUEST */
    Guest save(Guest guest);

    /* DELETE GUEST */
    void deleteById(int id);
}
