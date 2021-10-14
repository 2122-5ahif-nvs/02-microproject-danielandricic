package at.htl.entity;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Schema(name = "Person", description = "Person-class")
@Entity(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private String _firstName;
    private String _lastName;
    private LocalDate _birthDate;

    public Person(String _firstName, String _lastName, LocalDate _birthDate) {
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._birthDate = _birthDate;
    }

    public Person() {

    }

    public Long get_id() {
        return _id;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String firstName) {
        if(firstName.isEmpty() || firstName.isBlank()) {
            firstName = "ANONYMOUS";
        }
        this._firstName = firstName;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String lastName) {
        if(lastName.isEmpty() || lastName.isBlank()) {
            lastName = "ANONYMOUS";
        }
        else {
            this._lastName = lastName;
        }
    }

    public LocalDate get_birthDate() {
        return _birthDate;
    }

    public void set_birthDate(LocalDate birthDate) {
        if(birthDate == null)
            birthDate = LocalDate.now().minusYears(1);
        this._birthDate = birthDate;
    }

    public String get_fullname() {
        return String.format("%s %s", get_firstName(), get_lastName());
    }
}
