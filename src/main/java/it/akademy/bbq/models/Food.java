package it.akademy.bbq.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Food {
    /* ATTRIBUTES */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @JsonBackReference(value = "barbecue-food")
    @ManyToOne
    private Barbecue barbecue;

    @JsonBackReference(value = "guest-food")
    @ManyToOne
    private Guest guest;

    /* CONSTRUCTORS */
    public Food(){}

    public Food(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /* GETTERS & SETTERS */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Barbecue getBarbecue() {
        return barbecue;
    }

    public void setBarbecue(Barbecue barbecue) {
        this.barbecue = barbecue;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
}
