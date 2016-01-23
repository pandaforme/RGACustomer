package factory;

import crypt.Crypt;
import crypt.impl.AESCrypt;
import org.glassfish.hk2.api.Factory;

public class CryptFactory implements Factory<Crypt> {
  @Override
  public Crypt provide() {
    return new AESCrypt();
  }

  @Override
  public void dispose(Crypt instance) {

  }
}
