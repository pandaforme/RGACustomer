package factory;

import org.glassfish.hk2.api.Factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFactory implements Factory<Properties> {
  @Override
  public Properties provide() {
    Properties properties   = new Properties();
    String     propFileName = "config.properties";

    final InputStream inputStream = getClass().getClassLoader()
                                              .getResourceAsStream(propFileName);

    if (inputStream == null) {
      throw new RuntimeException("config.properties doesn't exist");
    }

    try {
      properties.load(inputStream);
      return properties;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void dispose(Properties instance) {

  }
}
