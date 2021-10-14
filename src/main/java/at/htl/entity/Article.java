package at.htl.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.time.LocalDate;

import static java.time.LocalDate.now;

@Entity(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private String _name;
    private String _content;
    private LocalDate _releaseDate;

    public Article(String name, String content, LocalDate releaseDate) {
        this._name = name;
        this._content = content;
        this._releaseDate = releaseDate;
    }

    public Article() {

    }

    public Long get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        if(name.isEmpty() || name.isBlank())
            name = "UNKNOWN";
        this._name = name;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String content) {
        if(content.isEmpty() || content.isBlank())
            content = "This article is empty";
        this._content = content;
    }

    public LocalDate get_releaseDate() {
        return _releaseDate;
    }

    public void set_releaseDate(LocalDate releaseDate) {
        if(releaseDate == null) releaseDate = now().minusDays(1);
        this._releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return String.format("The Article with ID: %d was published under the name %s on %t", get_id(), get_name(), get_releaseDate());
    }
}
