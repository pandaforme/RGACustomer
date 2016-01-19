package application;

import annotation.UserParam;
import db.CustomerRepo;
import factory.RepoFactory;
import factory.UserParamValueFactoryProvider;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.jersey.listing.ApiListingResourceJSON;
import lombok.extern.java.Log;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.slf4j.bridge.SLF4JBridgeHandler;
import utils.ApiAuthorizationFilterImpl;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/*")
@Log
public class MyApplication extends ResourceConfig {
  public MyApplication() {
    super();
    // Optionally remove existing handlers attached to j.u.l root logger
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
    // the initialization phase of your application
    SLF4JBridgeHandler.install();

    packages("provider", "rest");

    register(new LoggingFilter(log, true));

    this.register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(UserParamValueFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);
        bind(UserParamValueFactoryProvider.InjectionResolver.class).to(new TypeLiteral<InjectionResolver<UserParam>>() {
        }).in(Singleton.class);
        bindFactory(RepoFactory.class).to(CustomerRepo.class).in(Singleton.class);
      }
    });

    swagger();
  }

  private void swagger() {
    register(ApiListingResourceJSON.class);
    register(SwaggerSerializers.class);

    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("1.0.1");
    beanConfig.setResourcePackage("rest"); // replace with your packages
    beanConfig.setBasePath("http://localhost:8080/jersey-starterkit/");
    beanConfig.setDescription("My RESTful resources");
    beanConfig.setTitle("My RESTful API");
    beanConfig.setFilterClass(ApiAuthorizationFilterImpl.class.getName());
    beanConfig.setPrettyPrint(true);
    beanConfig.setScan(true);
  }
}
