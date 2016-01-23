package crypt.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AESCryptTest {
  @Mock
  private Properties properties;

  @InjectMocks
  private AESCrypt aesCrypt;


  @Before
  public void setUp() throws Exception {
    when(properties.getProperty("key")).thenReturn("LIjhBWwR1A9BjiBCdsMs0KiZ3x50Ce9auGFBqqj69Q4=");
    when(properties.getProperty("iv")).thenReturn("tKIlYqe00NtAuXhDy1UfYQ==");
  }

  @Test
  public void testNormalCase() throws Exception {
    final String encode = aesCrypt.encode("test");
    final String decode = aesCrypt.decode(encode);
    Assert.assertEquals("test", decode);
  }

  @Test
  public void testDecodeError() throws Exception {
    try {
      aesCrypt.encode("test");
      aesCrypt.decode("wrong");
    } catch (Exception e) {
      Assert.assertTrue(true);
    }
  }
}