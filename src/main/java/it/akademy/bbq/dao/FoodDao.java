package it.akademy.bbq.dao;

import it.akademy.bbq.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodDao extends JpaRepository<Food, Integer> {
    /* FIND FOOD */
    List<Food> findAll();
    List<Food> findAllByName(String name);
    Food findById(int id);

    /* CREATE OR SAVE FOOD */
    Food save(Food food);

    /* DELETE FOOD */
    void deleteById(int id);
}
