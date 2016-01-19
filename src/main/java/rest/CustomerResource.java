package rest;

import db.CustomerRepo;
import model.Customer;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
  @Inject
  private CustomerRepo customerRepo;

  @GET
  @Path("/customers/{id}")
  public Response get(@PathParam("id") @NotNull UUID id) {
    return Response.ok(customerRepo.get(id), MediaType.APPLICATION_JSON)
                   .build();
  }

  @GET
  @Path("/customers/")
  public Response getAll() {
    return Response.ok(customerRepo.getAll(), MediaType.APPLICATION_JSON)
                   .build();
  }

  @POST
  @Path("/customers/")
  public Response create(@NotNull @Valid Customer customer) {
    customerRepo.create(customer);
    return Response.ok(MediaType.APPLICATION_JSON)
                   .build();
  }

  @PUT
  @Path("/customers/")
  public Response update(@NotNull @Valid Customer customer) {
    customerRepo.update(customer);
    return Response.ok(MediaType.APPLICATION_JSON)
                   .build();
  }

  @DELETE
  @Path("/customers/{id}")
  public Response delete(@PathParam("id") @NotNull UUID id) {
    customerRepo.delete(id);
    return Response.ok(MediaType.APPLICATION_JSON)
                   .build();
  }
}


