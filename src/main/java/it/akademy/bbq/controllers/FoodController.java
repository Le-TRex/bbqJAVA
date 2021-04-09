package it.akademy.bbq.controllers;


import it.akademy.bbq.dao.BarbecueDao;
import it.akademy.bbq.dao.FoodDao;
import it.akademy.bbq.models.Barbecue;
import it.akademy.bbq.models.Food;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {
    /* AUTOWIRE DAOS */
    private final FoodDao foodDao;
    private final BarbecueDao barbecueDao;

    public FoodController(FoodDao foodDao, BarbecueDao barbecueDao) {
        this.foodDao = foodDao;
        this.barbecueDao = barbecueDao;
    }

    /* CRUD */
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods(){
        List<Food> foods = foodDao.findAll();
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/names/{name}")
    public ResponseEntity<List<Food>> getAllFoodsByName(@PathVariable String name){
        List<Food> foods = foodDao.findAllByName(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getById(@PathVariable int id){
        Food food = foodDao.findById(id);
        if(food == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody Food food){
        foodDao.save(food);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Food> putFood(@PathVariable int id, @RequestBody Food food){
        Food foodToRename = foodDao.findById(id);

        if(foodToRename == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        food.setId(id);
        foodDao.save(food);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable int id){
        Food food = foodDao.findById(id);
        if(food == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Barbecue> barbecues = barbecueDao.findAllByFoods(food);
        if ( barbecues == null ) {
            foodDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (Barbecue barbecue: barbecues) {
            barbecue.getFoods().removeIf(f -> f.getId()==id);
            barbecueDao.save(barbecue);
        }

        foodDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
