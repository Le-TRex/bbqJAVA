package it.akademy.bbq.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.akademy.bbq.Aliment;

import javax.persistence.*;


@Entity
public class Food implements Aliment {
    /* ATTRIBUTES */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Boolean hasBeenCooked;

    @JsonBackReference(value = "barbecue-food")
    @ManyToOne
    private Barbecue barbecue;

    @JsonBackReference(value = "guest-food")
    @ManyToOne
    private Guest guest;

    /* CONSTRUCTORS */
    public Food(){}

    public Food(int id, String name, Boolean hasBeenCooked) {
        this.id = id;
        this.name = name;
        this.hasBeenCooked = hasBeenCooked;
    }

    /* METHODS (but getters & setters) */
    @Override
    public void cook() {
        this.hasBeenCooked = true;
    }

    /* GETTERS & SETTERS */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBarbecue(Barbecue barbecue) {
        this.barbecue = barbecue;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasBeenCooked() {
        return hasBeenCooked;
    }

    public void setHasBeenCooked(Boolean hasBeenCooked) {
        this.hasBeenCooked = hasBeenCooked;
    }

    public Barbecue getBarbecue() {
        return barbecue;
    }

    public Guest getGuest() {
        return guest;
    }
}
