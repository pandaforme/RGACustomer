package rest;

import annotation.TokenFilter;
import db.CustomerRepo;
import model.Customer;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
  @Inject
  private CustomerRepo customerRepo;

  @GET
  @Path("/customer/{id}")
  @TokenFilter
  public Response get(@PathParam("id") @NotNull UUID id) {
    final Optional<Customer> customer = customerRepo.get(id);
    if (!customer.isPresent())
      return Response.noContent()
                     .build();

    return Response.ok(customer.get(), MediaType.APPLICATION_JSON)
                   .build();
  }

  @GET
  @Path("/customers/{id}")
  @TokenFilter
  public Response getAll(@PathParam("id") @NotNull UUID id) {
    final Optional<List<Customer>> customers = customerRepo.getAll(id);
    if (!customers.isPresent())
      return Response.noContent()
                     .build();

    return Response.ok(customers.get(), MediaType.APPLICATION_JSON)
                   .build();
  }

  @POST
  @Path("/customer/")
  @TokenFilter
  public Response create(@NotNull @Valid Customer customer) {
    customerRepo.create(customer);
    return Response.ok(MediaType.APPLICATION_JSON)
                   .build();
  }

  @PUT
  @Path("/customer/")
  @TokenFilter
  public Response update(@NotNull @Valid Customer customer) {
    customerRepo.update(customer);
    return Response.ok(MediaType.APPLICATION_JSON)
                   .build();
  }

  @DELETE
  @Path("/customer/{id}")
  @TokenFilter
  public Response delete(@PathParam("id") @NotNull UUID id) {
    customerRepo.delete(id);
    return Response.ok(MediaType.APPLICATION_JSON)
                   .build();
  }
}


