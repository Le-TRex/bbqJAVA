package it.akademy.bbq.controllers;

import it.akademy.bbq.dao.*;
import it.akademy.bbq.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/barbecues")
public class BarbecueController {
    /* AUTOWIRE DAOS */
    private final BarbecueDao barbecueDao;
    private final GuestDao guestDao;
    private final FoodDao foodDao;

    @Autowired
    public BarbecueController(BarbecueDao barbecueDao, GuestDao guestDao, FoodDao foodDao) {
        this.barbecueDao = barbecueDao;
        this.guestDao = guestDao;
        this.foodDao = foodDao;
    }

    /* CRUD */

    /* GET */
    @GetMapping
    public ResponseEntity<List<Barbecue>> getAllBarbecues() {
        List<Barbecue> barbecues = barbecueDao.findAll();
        return new ResponseEntity<>(barbecues, HttpStatus.OK);
    }

    @GetMapping("/countries/{country}")
    public ResponseEntity<List<Barbecue>> getAllBarbecuesByCountry(@PathVariable String country){
        List<Barbecue> barbecues = barbecueDao.findAllByCountry(country);
        return new ResponseEntity<>(barbecues, HttpStatus.OK);
    }

    @GetMapping("/cities/{city}")
    public ResponseEntity<List<Barbecue>> getAllBarbecuesByCity(@PathVariable String city){
        List<Barbecue> barbecues = barbecueDao.findAllByCity(city);
        return new ResponseEntity<>(barbecues, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Barbecue> getBarbecueById(@PathVariable int id){
        Barbecue barbecue = barbecueDao.findById(id);
        if(barbecue == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(barbecue, HttpStatus.OK);
    }

    /* POST */
    @PostMapping
    public ResponseEntity<Barbecue> addBarbecue(@RequestBody Barbecue barbecue){
        Barbecue newBarbecue = barbecueDao.save(barbecue);
        return new ResponseEntity<>(newBarbecue, HttpStatus.CREATED);
    }

    /* PUT || PATCH */
    @PutMapping("/{id}")
    public ResponseEntity<Barbecue> putBarbecue(@PathVariable int id, @RequestBody Barbecue barbecue) {
        Barbecue barbecueToModify = barbecueDao.findById(id);

        if (barbecueToModify == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        barbecue.setId(id);
        barbecueToModify = barbecueDao.save(barbecue);
        return new ResponseEntity<>(barbecueToModify, HttpStatus.OK);
    }

    @PutMapping("/{barbecueId}/guests/{guestId}")
    public ResponseEntity<Barbecue> addGuestToBarbecue(@PathVariable int barbecueId, @PathVariable int guestId) {
        Barbecue barbecue = barbecueDao.findById(barbecueId);

        if(barbecue == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Guest guest = guestDao.findById(guestId);
        if(guest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Food> foods = guest.getFoods();
        //if guest owns some food

        if (!(foods.isEmpty())) {
            barbecue.getFoods().addAll(foods);
            for (Food food: foods){

                //food sets its bbq
                food.setBarbecue(barbecue);

                //food hasBeenCooked changes
                food.cook();

                //food unset its guest
                food.setGuest(null);

                //save food with new attributes
                food.setId(food.getId());
                foodDao.save(food);
            }

            //guest removes food from it's list
            guest.setFoods(new ArrayList<>());
        }

        //Add guest to bbq and bbq to guest
        barbecue.getGuests().add(guest);
        guest.getBarbecues().add(barbecue);

        barbecue.setId(barbecueId);
        guest.setId(guestId);

        barbecueDao.save(barbecue);
        guestDao.save(guest);

        return new ResponseEntity<>(barbecue, HttpStatus.OK);
    }

    /* DELETE */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBarbecue(@PathVariable int id) {
        Barbecue barbecue = barbecueDao.findById(id);
        if (barbecue == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Guest> guests = barbecue.getGuests();
        if (!(guests.isEmpty())) {
            //remove bbq from each guest's bbq list
            for (Guest guest: guests){
                guest.getBarbecues().removeIf(bbq -> bbq.getId()==id);
                int guestId = guest.getId();
                guest.setId(guestId);
                guestDao.save(guest);
            }
            //nullify bbq's guest list
            barbecue.setGuests(null);
            //delete bbq
            barbecueDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        barbecueDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
