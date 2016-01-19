package rest;

import db.CustomerRepo;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {
  @Inject
  private CustomerRepo customerRepo;

  @GET
  @Path("/login/")
  public Response login(@HeaderParam("username") @NotNull String username,
                        @HeaderParam("password") @NotNull String password) {
    return Response.ok( MediaType.APPLICATION_JSON)
                   .build();
  }

  @GET
  @Path("/logout")
  public Response getAll() {
    return Response.ok(customerRepo.getAll(), MediaType.APPLICATION_JSON)
                   .build();
  }
}


