package at.htl.repository;

import at.htl.entity.Article;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class ArticleRepository extends GenericRepository<Article> {
    @Transactional
    public Article updateEntity(Article newArticle) {
        Article tmp = null;
        if(isPersistent(newArticle)) {
            tmp = findEntityByID(newArticle.get_id());
        }
        if(tmp != null) {
            tmp.set_content(newArticle.get_content());
            tmp.set_name(newArticle.get_name());
            tmp.set_releaseDate((newArticle.get_releaseDate()));
        }

        getEntityManager().merge(tmp);

        return tmp;
    }
}
