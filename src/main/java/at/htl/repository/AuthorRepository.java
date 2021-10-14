package at.htl.repository;

import at.htl.entity.Author;
import at.htl.entity.Reader;

import javax.transaction.Transactional;
import java.util.List;

public class AuthorRepository extends GenericRepository<Author> {

    @Transactional
    public Author updateAuthor(Author a) {
        Author tmp = null;
        if(isPersistent(a)) {
            tmp = findEntityByID(a.get_id());
        }
        if(tmp == null) {
            return null;
        }

        tmp.set_firstName(a.get_firstName());
        tmp.set_lastName(a.get_lastName());
        tmp.set_birthDate(a.get_birthDate());

        this.getEntityManager().merge(tmp);

        return tmp;
    }

    public List<Author> findByFirstName(String firstName) {
        return list("#Reader.findByFirstName", firstName);
    }

    public List<Author> findByLastName(String lastName) {
        return list("#Reader.findByLastName", lastName);
    }
}
