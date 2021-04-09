package it.akademy.bbq.controllers;

import it.akademy.bbq.dao.VegetableDao;
import it.akademy.bbq.models.Vegetable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vegetables")
public class VegetableController {
    /* AUTOWIRE DAOS */
    private final VegetableDao vegetableDao;

    public VegetableController(VegetableDao vegetableDao) {
        this.vegetableDao = vegetableDao;
    }

    /* CRUD */
    @GetMapping
    public ResponseEntity<List<Vegetable>> getAllVegetables(){
        List<Vegetable> vegetables = vegetableDao.findAll();
        return new ResponseEntity<>(vegetables, HttpStatus.OK);
    }

    @GetMapping("/name={name}")
    public ResponseEntity<List<Vegetable>> getAllVegetablesByName(@PathVariable String name){
        List<Vegetable> vegetables = vegetableDao.findAllByName(name);
        return new ResponseEntity<>(vegetables, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vegetable> getById(@PathVariable int id){
        Vegetable vegetable = vegetableDao.findById(id);
        if(vegetable == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vegetable, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Vegetable> createVegetable(@RequestBody Vegetable vegetable){
        vegetableDao.save(vegetable);
        return new ResponseEntity<>(vegetable, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Vegetable> putVegetable(@PathVariable int id, @RequestBody Vegetable vegetable){
        Vegetable vegetableToRename = vegetableDao.findById(id);

        if(vegetableToRename == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vegetable.setId(id);
        vegetableDao.save(vegetable);
        return new ResponseEntity<>(vegetable, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVegetable(@PathVariable int id){
        Vegetable vegetable = vegetableDao.findById(id);
        if(vegetable == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vegetableDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}