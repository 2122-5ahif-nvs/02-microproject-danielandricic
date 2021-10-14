package at.htl.entity;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Schema(name = "Author", description = "The author writes new articles, which is read by the reader.")
@Entity(name = "author")
public class Author extends Person{

    @JsonbTransient
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "author", fetch = FetchType.EAGER)
    private List<Article> articles;

    public Author(String firstName, String lastName, LocalDate birthDate) {
        super(firstName, lastName, birthDate);
    }

    public Author() {
        this("ANONYMOUS", "ANONYMOUS",null);
    }


    @Override
    public String toString() {
        return String.format("Author named %s, born in %s", get_fullname(), get_birthDate().toString());
    }
}
