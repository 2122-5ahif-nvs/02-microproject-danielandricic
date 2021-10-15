package at.htl.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "reader")
@NamedQueries({
        @NamedQuery(name = "Reader.findByLastName", query = "SELECT r FROM reader r WHERE r._lastName = :lastname"),
        @NamedQuery(name = "Reader.findByFirstName", query = "SELECT r FROM reader r WHERE r._firstName = :firstname")
})
public class Reader extends Person{

    @JsonbTransient
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "reader", fetch = FetchType.EAGER)
    private List<Article> articles;

    public Reader(String firstname, String lastname, LocalDate birthDate) {
        super(firstname, lastname, birthDate);
    }

    public Reader() {
        this("UNKNOWN", "UNKNOWN", null);
    }


    @Override
    public String toString() {
        return String.format("Reader named %s with ID: %d, born %s", get_fullname(), get_id(), get_birthDate());
    }
}
