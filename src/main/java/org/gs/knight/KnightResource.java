package org.gs.knight;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.types.ObjectId;

@Path("/knights")
public class KnightResource {

    @Inject
    KnightRepository repository;

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        Knight knight = repository.findById(new ObjectId(id));
        return Response.ok(knight).build();
    }

    @GET
    public Response get() {
        return Response.ok(repository.listAll()).build();
    }

    @GET
    @Path("/search/{name}")
    public Response search(@PathParam("name") String name) {
        Knight knight = repository.findByName(name);
        return knight != null ? Response.ok(knight).build()
                : Response.status(Status.NOT_FOUND).build();
    }

    @POST
    public Response create(Knight knight) throws URISyntaxException {
        repository.persist(knight);
        return Response.created(new URI("/" + knight.id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, Knight knight) {
        knight.id = new ObjectId(id);
        repository.update(knight);
        return Response.ok(knight).build();

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        Knight knight = repository.findById(new ObjectId(id));
        repository.delete(knight);
        return Response.noContent().build();
    }
}
