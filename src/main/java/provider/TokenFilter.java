package provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import crypt.Crypt;
import db.UserRepo;
import model.User;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@annotation.TokenFilter
@Provider
public class TokenFilter implements ContainerRequestFilter {
  @Inject
  private Crypt crypt;

  @Inject
  private UserRepo userRepo;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    final String token = requestContext.getHeaderString("Token");

    if (token == null)
      throw new BadRequestException("Bad token");

    final User user;
    try {
      user = new ObjectMapper().readValue(crypt.decode(token), User.class);
    } catch (IOException e) {
      throw new BadRequestException("Bad token");
    }

    if (!user.getIsTokenValid())
      throw new BadRequestException("Bad token");

    if (!userRepo.getById(user.getId()).isPresent())
      throw new BadRequestException("Bad token");
  }
}
