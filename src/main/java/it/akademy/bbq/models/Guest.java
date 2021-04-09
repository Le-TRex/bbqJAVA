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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Barbecue> getBarbecues() {
        return barbecues;
    }

    public void setBarbecues(List<Barbecue> barbecues) {
        this.barbecues = barbecues;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
