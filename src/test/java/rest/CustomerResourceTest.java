package rest;

import db.CustomerRepo;
import model.Customer;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerResourceTest {
  @Mock
  private CustomerRepo customerRepo;

  @InjectMocks
  private CustomerResource customerResource;

  @Test
  public void testGetHasContent() throws Exception {
    when(customerRepo.get(any())).thenReturn(Optional.of(new Customer()));
    final Response response = customerResource.get(UUID.randomUUID());

    Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
    Assert.assertNotNull(response.getEntity().toString());
  }

  @Test
  public void testGetNoContent() throws Exception {
    when(customerRepo.get(any())).thenReturn(Optional.empty());
    final Response response = customerResource.get(UUID.randomUUID());

    Assert.assertEquals(HttpStatus.NO_CONTENT_204, response.getStatus());
  }

  @Test
  public void testGetAllHasContent() throws Exception {
    when(customerRepo.getAll(any())).thenReturn(Optional.of(Collections.emptyList()));
    final Response response = customerResource.getAll(UUID.randomUUID());

    Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
    Assert.assertNotNull(response.getEntity().toString());
  }

  @Test
  public void testGetAllNoContent() throws Exception {
    when(customerRepo.getAll(any())).thenReturn(Optional.empty());
    final Response response = customerResource.getAll(UUID.randomUUID());

    Assert.assertEquals(HttpStatus.NO_CONTENT_204, response.getStatus());
  }

  @Test
  public void testCreate() throws Exception {
    final Response response = customerResource.create(new Customer());

    Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
  }

  @Test
  public void testUpdate() throws Exception {
    final Response response = customerResource.update(new Customer());

    Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
  }

  @Test
  public void testDelete() throws Exception {
    final Response response = customerResource.delete(UUID.randomUUID());

    Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
  }
}