package it.akademy.bbq.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Guest {
    /* ATTRIBUTES */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastname;
    private String city;
    private String country;

//    @JsonBackReference(value = "barbecue-guest")
    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY, mappedBy = "guests")
    private List<Barbecue> barbecues;

    @JsonManagedReference(value = "guest-food")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Food> foods;

    /* CONSTRUCTORS */
    public Guest(){}

    public Guest(String firstname, String lastname, String city, String country) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;

        this.barbecues = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    /* GETTERS & SETTERS */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Barbecue> getBarbecues() {
        return barbecues;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
