package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crypt.Crypt;
import db.UserRepo;
import model.User;
import utils.Whirlpool;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {
  @Inject
  private UserRepo userRepo;

  @Inject
  private Crypt crypt;

  @POST
  @Path("/login/")
  public Response login(@NotNull User user) {
    final Optional<User> dbUser = userRepo.get(user.getName(), Whirlpool.WhirlPoolHash(user.getPassword()));
    if (!dbUser.isPresent())
      throw new BadRequestException("Bad username or password");

    String json = null;
    try {
      json = new ObjectMapper().writeValueAsString(dbUser.get());
    } catch (JsonProcessingException e) {
      new RuntimeException(e);
    }

    final String token = crypt.encode(json);

    dbUser.get().setIsTokenValid(true);
    userRepo.update(dbUser.get());

    return Response.ok(token, MediaType.APPLICATION_JSON)
                   .build();
  }

  @POST
  @Path("/logout")
  public Response logout(@NotNull User user) {
    final Optional<User> dbUser = userRepo.get(user.getName(), Whirlpool.WhirlPoolHash(user.getPassword()));
    if (!dbUser.isPresent())
      throw new BadRequestException("Bad username or password");

    dbUser.get().setIsTokenValid(false);
    userRepo.update(dbUser.get());

    return Response.ok(MediaType.APPLICATION_JSON)
                   .build();
  }
}


