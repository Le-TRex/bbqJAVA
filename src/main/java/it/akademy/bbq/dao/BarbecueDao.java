package it.akademy.bbq.dao;

import it.akademy.bbq.models.Barbecue;
import it.akademy.bbq.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarbecueDao  extends JpaRepository<Barbecue, Integer> {
    /* FIND BBQ */
    List<Barbecue> findAll();
    List<Barbecue> findAllByCountry(String country);
    List<Barbecue> findAllByCity(String city);

    List<Barbecue> findAllByGuests(Guest guest);

    Barbecue findById(int id);

    /* CREATE OR UPDATE BBQ */
    Barbecue save(Barbecue barbecue);

    /* DELETE BBQ */
    void deleteById(int id);
}
