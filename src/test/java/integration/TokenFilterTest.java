package integration;

import crypt.Crypt;
import db.UserRepo;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import provider.TokenFilter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenFilterTest extends JerseyTest {
  @Mock
  private Crypt crypt;

  @Mock
  private UserRepo userRepo;

  @Override
  public Application configure() {
    AbstractBinder binder = new AbstractBinder() {
      @Override
      protected void configure() {
        bindFactory(new Factory<Crypt>() {

          @Override
          public Crypt provide() {
            return crypt;
          }

          @Override
          public void dispose(Crypt instance) {

          }
        }).to(Crypt.class);

        bindFactory(new Factory<UserRepo>() {

          @Override
          public UserRepo provide() {
            return userRepo;
          }

          @Override
          public void dispose(UserRepo instance) {

          }
        }).to(UserRepo.class);

      }
    };

    return new ResourceConfig(TokenFilter.class, Resource.class).register(binder);
  }

  @Test
  public void testNormalCase() {
    when(crypt.decode(anyString())).thenReturn("{\"id\":\"76982b60-c1df-11e5-a837-0800200c9a66\", \"name\":\"test\", \"password\":\"password\", \"isTokenValid\":\"true\"}");
    when(userRepo.getById(eq(UUID.fromString("76982b60-c1df-11e5-a837-0800200c9a66")))).thenReturn(Optional.of(new User()));

    Response response = target().path("Test")
                                .request(MediaType.APPLICATION_JSON)
                                .header("Token", UUID.randomUUID().toString())
                                .get();

    Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
  }

  @Test
  public void testTokenNull() {
    try {
      target().path("Test")
              .request(MediaType.APPLICATION_JSON)
              .get();
    } catch (BadRequestException e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void testDecodeError() {
    when(crypt.decode(anyString())).thenReturn("wrong format");
    try {
      target().path("Test")
              .request(MediaType.APPLICATION_JSON)
              .header("Token", UUID.randomUUID().toString())
              .get();
    } catch (BadRequestException e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void testTokenExpire() {
    when(crypt.decode(anyString())).thenReturn("{\"id\":\"76982b60-c1df-11e5-a837-0800200c9a66\", \"name\":\"test\", \"password\":\"password\", \"isTokenValid\":\"false\"}");
    try {
      target().path("Test")
              .request(MediaType.APPLICATION_JSON)
              .header("Token", UUID.randomUUID().toString())
              .get();
    } catch (BadRequestException e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void testUserIdError() {
    when(crypt.decode(anyString())).thenReturn("{\"id\":\"76982b60-c1df-11e5-a837-0800200c9a66\", \"name\":\"test\", \"password\":\"password\", \"isTokenValid\":\"true\"}");
    when(userRepo.getById(eq(UUID.fromString("76982b60-c1df-11e5-a837-0800200c9a66")))).thenReturn(Optional.empty());

    try {
      target().path("Test")
              .request(MediaType.APPLICATION_JSON)
              .header("Token", UUID.randomUUID().toString())
              .get();
    } catch (BadRequestException e) {
      Assert.assertTrue(true);
    }
  }

  @Path("Test")
  public static class Resource {
    @GET
    @annotation.TokenFilter
    public String get() {
      return Response.ok().toString();
    }
  }
}