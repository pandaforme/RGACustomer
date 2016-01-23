package application;

import crypt.Crypt;
import db.CustomerRepo;
import db.UserRepo;
import factory.CryptFactory;
import factory.CustomerRepoFactory;
import factory.PropertiesFactory;
import factory.UserRepoFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import java.util.Properties;
import java.util.logging.Logger;

@ApplicationPath("/*")
public class MyApplication extends ResourceConfig {
  private final Logger logger = Logger.getLogger(MyApplication.class.getName());

  public MyApplication() {
    super();
    packages("provider", "rest");

    register(new LoggingFilter(logger, true));

    this.register(new AbstractBinder() {
      @Override
      protected void configure() {
        bindFactory(CustomerRepoFactory.class).to(CustomerRepo.class).in(Singleton.class);
        bindFactory(UserRepoFactory.class).to(UserRepo.class).in(Singleton.class);
        bindFactory(CryptFactory.class).to(Crypt.class).in(Singleton.class);
        bindFactory(PropertiesFactory.class).to(Properties.class).in(Singleton.class);
      }
    });
  }
}
