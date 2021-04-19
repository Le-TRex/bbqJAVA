package it.akademy.bbq.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Barbecue {
    /* ATTRIBUTES */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String date;

//    @JsonManagedReference(value = "barbecue-guest")
    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name = "barbecue_guest", joinColumns = @JoinColumn(name="barbecueid_fk"),
                inverseJoinColumns = @JoinColumn(name="guestid_fk"))
    private List<Guest> guests;

    @JsonManagedReference(value = "barbecue-food")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Food> foods;

    private String address;
    private String city;
    private String country;

    /* CONSTRUCTORS */
    public Barbecue(){}

    public Barbecue(String date, String address, String city, String country) {
        this.date = date;
        this.address = address;
        this.city = city;
        this.country = country;

        this.guests = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    /* GETTERS & SETTERS */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
