package at.htl.boundary;

import at.htl.entity.Article;
import at.htl.repository.ArticleRepository;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Article API",
                description = "CRUD-Operations for the object Article",
                version = "1.0",
                contact = @Contact(name = "danielandricic", url = "https://github.com/danielandricic")
        ),
        tags = {
                @Tag(name = "api", description = "Article API"),
                @Tag(name = "article", description = "Creating new articles")
        }
)
@Schema(name = "Article Endpoints", description = "")
@Path("/api/article")
@Tag(name = "Article Ressource",
        description = "Article endpoints, where you can add/read/update/delete your articles")
public class ArticleService {

    @Inject
    ArticleRepository repo;

    @Operation(
            summary = "Returns all Articles",
            description = "The function activates when someone calls the specified URL with api/Article/getAllArticles and " +
                    "Selects all Articles from the Database and returns it as a List!"
    )
    @GET
    @Path("/getAllArticles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArticles() {
        List<Article> allArticles = repo.getEntityList();
        if(allArticles != null || !allArticles.isEmpty()) {
            return Response
                    .accepted(allArticles)
                    .header("tag", "A list of Articles")
                    .build();
        }
        else {
            return Response
                    .noContent()
                    .header("tag", "List empty!")
                    .build();
        }
    }

    @Operation(
            summary = "Adds one Article",
            description = "The function activates when someone calls the specified URL with api/Article/addArticle. " +
                    "It adds a new Article when you add a JsonObject to the request." +
                    "The parts of the JsonObject would be parsed to the specific datatype which would be in use and finally it would" +
                    "build a new Article object which would be added via Panache-Method persistAndFlush()."
    )
    @POST
    @Path("/addArticle")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addArticle(Article article)
    {
        boolean result = repo.addEntity(article);
        if(result) {
            return Response
                    .ok("Article added!")
                    .build();
        }
        else {
            return Response
                    .noContent()
                    .header("tag", "Article already added or null!")
                    .build();
        }
    }

    @Operation(
            summary = "Updates an Article by ID",
            description = "The function activates when someone calls the specified URL with api/Article/updateArticle." +
                    "It takes the JsonObject from the given request and sets the new data in the specified Article." +
                    "The last step is, that Panache would flush it also to the database so the db stays up to date."
    )
    @PUT
    @Path("/updateArticle")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateArticle(Article newArticle) {
        Article updatedArticle = (Article) repo.updateEntity(newArticle);

        if(updatedArticle != null) {
            return Response
                    .ok(String.format("Article with ID: %d was updated", updatedArticle.get_id()))
                    .header("tag", String.format("Article with ID: %d updated successfully!", updatedArticle.get_id()))
                    .build();
        }
        else {
            return Response
                    .noContent()
                    .header("tag", "Article not found!")
                    .build();
        }
    }

    @Operation(
            summary = "Searches an Article by ID",
            description = "The function activates when someone calls the specified URL with api/Article/findArticleById/{id}." +
                    "In this function the article would be searched by ID."
    )
    @GET
    @Path("/findArticleById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findArticleById(@PathParam("id") Long id) {
        Article a1 = (Article) repo.findEntityByID(id);

        if(a1 == null) {
            return Response
                    .noContent()
                    .header("tag", "Article by id not found!")
                    .build();
        }

        return Response
                .ok(a1)
                .header("tag", "Article found!")
                .build();
    }

    @Operation(
            summary = "Deletes an Article by ID",
            description = "The function activates when someone calls the specified URL with api/Article/deleteArticleByID/{id}." +
                    "The first thing this function does, is that the specified would be searched with the given ID." +
                    "If it exists, the function would delete it with the PanachRepository build in method and it would" +
                    "automated delete it from the database."
    )
    @Transactional
    @DELETE
    @Path("/deleteArticleByID/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteArticleByID(@PathParam("id") Long id) {
        boolean isDeleted = repo.deleteById(id);

        if(isDeleted) {
            return Response
                    .ok("Article successfully deleted!")
                    .build();
        }

        return Response
                .noContent()
                .header("tag", "Article not deleted! This ID wasn't found.")
                .build();
    }

    @Transactional
    @DELETE
    @Path("/deleteArticle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteArticle(Article article) {

        if(repo.isPersistent(article))
        repo.delete(article);

        if(deletedArticle != null) {

        }

        return Response
                .serverError()
                .header("tag", String.format("Article not found with ID: %d", article.get_id()))
                .build();
    }
}
