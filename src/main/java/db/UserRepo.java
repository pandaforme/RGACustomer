package db;

import model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo {
  Optional<User> get(String name, String password);

  Optional<User> getById(UUID id);

  void create(User user);

  void update(User user);

  void delete(UUID uuid);
}
