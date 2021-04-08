package it.akademy.bbq.controllers;

import it.akademy.bbq.dao.CheeseDao;
import it.akademy.bbq.models.Cheese;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cheeses")
public class CheeseController {
    /* AUTOWIRE DAOS */
    private final CheeseDao cheeseDao;

    public CheeseController(CheeseDao cheeseDao) {
        this.cheeseDao = cheeseDao;
    }

    /* CRUD */
    @GetMapping
    public ResponseEntity<List<Cheese>> getAllCheeses(){
        List<Cheese> cheeses = cheeseDao.findAll();
        return new ResponseEntity<>(cheeses, HttpStatus.OK);
    }

    @GetMapping("/?name={name}")
    public ResponseEntity<List<Cheese>> getAllCheesesByName(@PathVariable String name){
        List<Cheese> cheeses = cheeseDao.findAllByName(name);
        return new ResponseEntity<>(cheeses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cheese> getById(@PathVariable int id){
        Cheese cheese = cheeseDao.findById(id);
        if(cheese == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cheese, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cheese> createCheese(@RequestBody Cheese cheese){
        cheeseDao.save(cheese);
        return new ResponseEntity<>(cheese, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cheese> putCheese(@PathVariable int id, @RequestBody Cheese cheese){
        Cheese cheeseToRename = cheeseDao.findById(id);

        if(cheeseToRename == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cheese.setId(id);
        cheeseDao.save(cheese);
        return new ResponseEntity<>(cheese, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCheese(@PathVariable int id){
        Cheese cheese = cheeseDao.findById(id);
        if(cheese == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cheeseDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}