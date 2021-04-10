package it.akademy.bbq.controllers;

import it.akademy.bbq.dao.*;
import it.akademy.bbq.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {
    /* AUTOWIRE DAOS */
    private final GuestDao guestDao;
    private final BarbecueDao barbecueDao;
    private final FoodDao foodDao;

    @Autowired
    public GuestController(GuestDao guestDao, BarbecueDao barbecueDao, FoodDao foodDao) {
        this.barbecueDao = barbecueDao;
        this.guestDao = guestDao;
        this.foodDao = foodDao;
    }

    /* CRUD */
    /* GET */
    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        List<Guest> guests = guestDao.findAll();
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @GetMapping("/firstnames/{firstname}")
    public ResponseEntity<List<Guest>> getAllGuestsByFirstname(@PathVariable String firstname) {
        List<Guest> guests = guestDao.findAllByFirstname(firstname);
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @GetMapping("/lastnames/{lastname}")
    public ResponseEntity<List<Guest>> getAllGuestsByLastname(@PathVariable String lastname) {
        List<Guest> guests = guestDao.findAllByLastname(lastname);
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @GetMapping("/countries/{country}")
    public ResponseEntity<List<Guest>> getAllGuestsByCountry(@PathVariable String country) {
        List<Guest> guests = guestDao.findAllByCountry(country);
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @GetMapping("/cities/{city}")
    public ResponseEntity<List<Guest>> getAllGuestsByCity(@PathVariable String city) {
        List<Guest> guests = guestDao.findAllByCity(city);
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable int id) {
        Guest guest = guestDao.findById(id);
        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(guest, HttpStatus.OK);
    }

    @GetMapping("/{id}/barbecues") //Find all guest's barbecues
    public ResponseEntity<List<Barbecue>> findAllByGuests(@PathVariable int id){
        Guest guest = guestDao.findById(id);
        if(guest ==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Barbecue> barbecues = barbecueDao.findAllByGuests(guest);

        return new ResponseEntity<>(barbecues,HttpStatus.OK);
    }

    /* POST */
    @PostMapping
    public ResponseEntity<Guest> addGuest(@RequestBody Guest guest){
        guestDao.save(guest);
        return new ResponseEntity<>(guest, HttpStatus.CREATED);
    }

    /* PUT || PATCH */
    @PutMapping("/{id}")
    public ResponseEntity<Guest> putGuest(@PathVariable int id, @RequestBody Guest guest){
        Guest modifiedGuest = guestDao.findById(id);

        if(modifiedGuest == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        guest.setId(id);
        modifiedGuest = guestDao.save(guest);
        return new ResponseEntity<>(modifiedGuest, HttpStatus.OK);
    }

    @PutMapping("/{guestId}/foods/{foodId}")
    public ResponseEntity<Guest> addFoodToGuest(@PathVariable int guestId, @PathVariable int foodId){
        Guest guest = guestDao.findById(guestId);
        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Food food = foodDao.findById(foodId);
        if (food == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        guest.getFoods().add(food);
        food.setGuest(guest);
        guest.setId(guestId);
        guestDao.save(guest);
        return new ResponseEntity<>(guest, HttpStatus.OK);
    }

    /* DELETE */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable int id) {
        Guest guest = guestDao.findById(id);
        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Barbecue> barbecues = barbecueDao.findAllByGuests(guest);
        if (barbecues != null) {
            for (Barbecue barbecue: barbecues) {
                barbecue.getGuests().removeIf(g -> g.getId() == id);
                barbecueDao.save(barbecue);
            }
        }
        guestDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
