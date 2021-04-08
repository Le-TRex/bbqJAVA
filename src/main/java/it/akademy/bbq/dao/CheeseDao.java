package it.akademy.bbq.dao;

import it.akademy.bbq.models.Cheese;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheeseDao extends JpaRepository<Cheese, Integer> {
    /* FIND CHEESE */
    List<Cheese> findAll();
    List<Cheese> findAllByName(String name);
    Cheese findById(int id);

    /* CREATE OR SAVE CHEESE */
    Cheese save(Cheese cheese);

    /* DELETE CHEESE */
    void deleteById(int id);
}
