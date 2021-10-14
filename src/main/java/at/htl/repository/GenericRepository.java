package at.htl.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class GenericRepository<T> implements PanacheRepository<T> {

    @Inject
    Logger logger;

    public GenericRepository() {

    }

    @Transactional
    public boolean addEntity(T entity) {
        try {
            if (isPersistent(entity)) {
                return false;
            }
            persistAndFlush(entity);
        } catch (NullPointerException ex) {
            logger.warn("Entity is null!");
            return false;
        }

        return true;
    }

    public T findEntityByID(Long id) {
        return findById(id);
    }

    public List<T> getEntityList() {
        return listAll();
    }
}
