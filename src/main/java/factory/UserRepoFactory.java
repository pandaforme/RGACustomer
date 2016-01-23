package factory;

import db.UserRepo;
import db.impl.DummyUserRepo;
import org.glassfish.hk2.api.Factory;

public class UserRepoFactory implements Factory<UserRepo> {
  @Override
  public UserRepo provide() {
    return new DummyUserRepo();
  }

  @Override
  public void dispose(UserRepo instance) {

  }
}
