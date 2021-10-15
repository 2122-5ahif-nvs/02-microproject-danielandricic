package at.htl.repository;

import at.htl.entity.Reader;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ReaderRepository extends GenericRepository<Reader> {

    @Transactional
    public Reader updateReader(Reader reader) {
        Reader r = null;
        if(isPersistent(reader)) {
            r = findEntityByID(reader.get_id());
        }
        if(r == null) {
            return null;
        }

        r.set_firstName(reader.get_firstName());
        r.set_lastName(reader.get_lastName());
        r.set_birthDate(reader.get_birthDate());

        this.getEntityManager().merge(r);

        return r;
    }

    public List<Reader> findByFirstName(String firstName) {
        return list("#Reader.findByFirstName", firstName);
    }

    public List<Reader> findByLastName(String lastName) {
        return list("#Reader.findByLastName", lastName);
    }
}
