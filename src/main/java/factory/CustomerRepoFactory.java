package factory;

import db.CustomerRepo;
import db.impl.DummyCustomerRepo;
import org.glassfish.hk2.api.Factory;

public class CustomerRepoFactory implements Factory<CustomerRepo> {
  @Override
  public CustomerRepo provide() {
    return new DummyCustomerRepo();
  }

  @Override
  public void dispose(CustomerRepo instance) {

  }
}
