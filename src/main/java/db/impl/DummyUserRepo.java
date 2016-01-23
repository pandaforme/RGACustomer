package db.impl;

import db.UserRepo;
import model.User;

import java.util.Optional;
import java.util.UUID;

public class DummyUserRepo implements UserRepo {
  @Override
  public Optional<User> get(String name, String password) {
    return Optional.of(new User());
  }

  @Override
  public Optional<User> getById(UUID id) {
    return Optional.of(new User());
  }

  @Override
  public void create(User user) {

  }

  @Override
  public void update(User user) {

  }

  @Override
  public void delete(UUID uuid) {

  }
}
