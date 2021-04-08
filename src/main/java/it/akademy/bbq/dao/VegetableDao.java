package it.akademy.bbq.dao;

import it.akademy.bbq.models.Vegetable;
import it.akademy.bbq.models.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VegetableDao extends JpaRepository<Vegetable, Integer> {
    /* FIND VEGETABLE */
    List<Vegetable> findAll();
    List<Vegetable> findAllByName(String name);
    Vegetable findById(int id);

    /* CREATE OR SAVE VEGETABLE */
    Vegetable save(Vegetable vegetable);

    /* DELETE VEGETABLE */
    void deleteById(int id);
}
