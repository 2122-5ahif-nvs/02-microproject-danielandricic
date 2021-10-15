package at.htl.boundary;


import at.htl.entity.Reader;
import at.htl.repository.ReaderRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/Reader")
@Tag(name = "Reader API")
public class ReaderService {

    @Inject
    ReaderRepository repo;

    @Operation(
            summary = "Adds a new Reader",
            description = "The function activates when someone calls the specified url with a JSON-Object and " +
                    "calls the add-method from the Reader-Repository in order to store the new reader."
    )
    @POST
    @Path("/addReader")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addReader(Reader reader) {
        boolean result = repo.addEntity(reader);
        if(result) {
            return Response
                    .ok()
                    .header("tag", "Added!")
                    .build();
        }

        return Response
                .noContent()
                .header("tag", "New reader already added or null!")
                .build();
    }

    @Operation(
            summary = "Updates the Reader",
            description = "The function activates when someone calls the specified url with a JSON-Object and " +
                    "calls the Update-method from the Reader-Repository in order to update the reader."
    )
    @PUT
    @Path("/updateReader")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateReader(Reader reader) {
        Reader tmp = repo.updateReader(reader);
        if(tmp == null) {
            return Response
                    .serverError()
                    .header("tag", "Update failed!")
                    .build();
        }

        return Response
                .ok()
                .header("tag", "Update was successful!")
                .build();
    }

    @Operation(
            summary = "Deletes Reader",
            description = "Search the whole entity, when it finds the entity, then it will delete it!"
    )
    @Transactional
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteReader(Reader r) {
        if(!repo.isPersistent(r)) {
            return Response
                    .serverError()
                    .header("tag", "Reader does not exist!")
                    .build();
        }

        repo.delete(r);
        repo.flush();

        return Response
                .ok()
                .header("tag", "Delete action was successfully!")
                .build();
    }

    @Operation(
            summary = "Deletes the Reader w",
            description = "The function activates when someone calls the specified url with a JSON-Object and " +
                    "calls the delete-method from the Reader-Repository in order to delete the reader."
    )
    @Transactional
    @DELETE
    @Path("/deleteReaderByID/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteReaderByID(@PathParam("id") Long id) {
        return deleteReaderByIDWithQuery(id);
    }

    @Transactional
    @DELETE
    @Path("/deleteReaderByID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReaderByIDWithQuery(@QueryParam("id") Long id) {
        boolean status = repo.deleteById(id);
        if(!status) {
            return Response
                    .serverError()
                    .header("tag", "Delete By ID failed!")
                    .build();
        }

        return Response
                .noContent()
                .header("tag", "Delete by ID was successfull")
                .build();
    }

    @GET
    @Path("/reader/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findReaderByID(@PathParam("id") Long id) {
        Reader r = repo.findEntityByID(id);
        if(r != null) {
            return Response
                    .accepted(r)
                    .header("tag", "Reader with ID: " + id)
                    .build();
        }
        else {
            return Response
                    .noContent()
                    .header("tag", "0 Reader with ID: " + id + " found!")
                    .build();
        }
    }

    @GET
    @Path("/reader")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findReaderByIDWithQuery(@QueryParam("id") Long id) {
        Reader r = repo.findEntityByID(id);
        if(r != null) {
            return Response
                    .accepted(r)
                    .header("tag", "Reader with ID: " + id)
                    .build();
        }
        else {
            return Response
                    .noContent()
                    .header("tag", "0 Reader with ID: " + id + " found!")
                    .build();
        }
    }

    @GET
    @Path("/getReaders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReaders() {
        List<Reader> readerList = repo.getEntityList();

        if(readerList != null) {
            return Response
                    .accepted(readerList)
                    .header("tag", "List of Readers")
                    .build();
        }
        else {
            return Response
                    .noContent()
                    .header("tag", "List is empty")
                    .build();
        }
    }
}
