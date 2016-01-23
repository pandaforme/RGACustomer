package rest;

import crypt.Crypt;
import db.UserRepo;
import model.User;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginResourceTest {
  @Mock
  private UserRepo userRepo;

  @Mock
  private Crypt crypt;

  @InjectMocks
  private LoginResource loginResource;

  @Test
  public void testLogin() throws Exception {
    when(userRepo.get(any(), any())).thenReturn(Optional.of(new User()));
    when(crypt.encode(any())).thenReturn("test token");

    final User user = new User();
    user.setName("test name");
    user.setPassword("test password");
    final Response response = loginResource.login(user);

    Assert.assertEquals(HttpStatus.OK_200, response.getStatus());
    Assert.assertEquals("test token", response.getEntity().toString());

    verify(userRepo).update(any());
  }

  @Test(expected = BadRequestException.class)
  public void testLoginFail() throws Exception {
    when(userRepo.get(any(), any())).thenReturn(Optional.empty());

    final User user = new User();
    user.setName("test name");
    user.setPassword("test password");
    loginResource.login(user);

    verify(userRepo, times(0)).update(any());
  }

  @Test
  public void testLogout() throws Exception {
    when(userRepo.get(any(), any())).thenReturn(Optional.of(new User()));

    final User user = new User();
    user.setName("test name");
    user.setPassword("test password");
    loginResource.logout(user);

    verify(userRepo).update(any());
  }

  @Test(expected = BadRequestException.class)
  public void testLogoutFail() throws Exception {
    when(userRepo.get(any(), any())).thenReturn(Optional.empty());

    final User user = new User();
    user.setName("test name");
    user.setPassword("test password");
    loginResource.logout(user);

    verify(userRepo, times(0)).update(any());
  }
}