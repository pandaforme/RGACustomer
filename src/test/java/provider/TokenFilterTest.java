package provider;

import crypt.Crypt;
import db.UserRepo;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenFilterTest {
  @Mock
  private Crypt crypt;

  @Mock
  private UserRepo userRepo;

  @Mock
  private ContainerRequestContext containerRequestContext;

  @InjectMocks
  private TokenFilter tokenFilter;

  @Test
  public void testNormalCase() {
    when(containerRequestContext.getHeaderString(eq("Token"))).thenReturn("76982b60-c1df-11e5-a837-0800200c9a66");
    when(crypt.decode(anyString())).thenReturn("{\"id\":\"76982b60-c1df-11e5-a837-0800200c9a66\", \"name\":\"test\", \"password\":\"password\", \"isTokenValid\":\"true\"}");
    when(userRepo.getById(eq(UUID.fromString("76982b60-c1df-11e5-a837-0800200c9a66")))).thenReturn(Optional.of(new User()));

    tokenFilter.filter(containerRequestContext);
    Assert.assertTrue(true);
  }

  @Test(expected = BadRequestException.class)
  public void testTokenNull() {
    when(containerRequestContext.getHeaderString(eq("Token"))).thenReturn(null);

    tokenFilter.filter(containerRequestContext);
  }

  @Test(expected = BadRequestException.class)
  public void testDecodeError() {
    when(containerRequestContext.getHeaderString(eq("Token"))).thenReturn("Bad Token");
    when(crypt.decode(anyString())).thenReturn("wrong format");

    tokenFilter.filter(containerRequestContext);
  }

  @Test(expected = BadRequestException.class)
  public void testTokenExpire() {
    when(containerRequestContext.getHeaderString(eq("Token"))).thenReturn("Bad Token");
    when(crypt.decode(anyString())).thenReturn("{\"id\":\"76982b60-c1df-11e5-a837-0800200c9a66\", \"name\":\"test\", \"password\":\"password\", \"isTokenValid\":\"false\"}");

    tokenFilter.filter(containerRequestContext);
  }

  @Test(expected = BadRequestException.class)
  public void testUserIdError() {
    when(containerRequestContext.getHeaderString(eq("Token"))).thenReturn("Bad Token");
    when(crypt.decode(anyString())).thenReturn("{\"id\":\"76982b60-c1df-11e5-a837-0800200c9a66\", \"name\":\"test\", \"password\":\"password\", \"isTokenValid\":\"true\"}");
    when(userRepo.getById(eq(UUID.fromString("76982b60-c1df-11e5-a837-0800200c9a66")))).thenReturn(Optional.empty());

    tokenFilter.filter(containerRequestContext);
  }
}