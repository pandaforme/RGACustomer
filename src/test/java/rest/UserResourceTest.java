package rest;

import annotation.UserParam;
import db.CustomerRepo;
import factory.UserParamValueFactoryProvider;
import lombok.extern.slf4j.Slf4j;
import model.Customer;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class UserResourceTest extends JerseyTest {
  private CustomerRepo repo;

  @Override
  public Application configure() {
    enable(TestProperties.DUMP_ENTITY);
    enable(TestProperties.LOG_TRAFFIC);

    AbstractBinder binder = new AbstractBinder() {
      @Override
      protected void configure() {
        bindFactory(new Factory<CustomerRepo>() {
          @Override
          public CustomerRepo provide() {
            repo = mock(CustomerRepo.class);
            return repo;
          }

          @Override
          public void dispose(CustomerRepo instance) {

          }
        }).to(CustomerRepo.class);

        bind(UserParamValueFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);

        bind(UserParamValueFactoryProvider.InjectionResolver.class).to(new TypeLiteral<InjectionResolver<UserParam>>() {
        }).in(Singleton.class);
      }
    };

    return new ResourceConfig(CustomerResource.class).register(binder);
  }

  @Test
  public void normal() {
    when(repo.get(any())).thenReturn(Customer.builder()
                                             .age(30)
                                             .email("test@gamil.com")
                                             .name("test")
                                             .build());

    Response response = target("user").request(MediaType.APPLICATION_JSON)
                                      .header("userId", UUID.randomUUID())
                                      .get();

    Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
  }

  @Test
  public void badRequest() {
    when(repo.get(any())).thenReturn(Customer.builder()
                                             .age(300)
                                             .email("test@gamil.com")
                                             .name("test")
                                             .build());

    Response response = target("user").request(MediaType.APPLICATION_JSON)
                                      .header("userId", UUID.randomUUID())
                                      .get();

    Assert.assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
  }

  @Test
  public void badUserId() {
    when(repo.get(any())).thenReturn(Customer.builder()
                                             .age(300)
                                             .email("test@gamil.com")
                                             .name("test")
                                             .build());

    Response response = target("user").request(MediaType.APPLICATION_JSON)
                                      .header("userId", "123456")
                                      .get();

    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, response.getStatus());
  }
}