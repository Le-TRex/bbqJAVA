package it.akademy.bbq.dao;

import it.akademy.bbq.models.*;
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
    List<Barbecue> findAllByFoods(Food food);

    Barbecue findById(int id);

    /* CREATE OR UPDATE BBQ */
    Barbecue save(Barbecue barbecue);

    /* DELETE BBQ */
    void deleteById(int id);
}
