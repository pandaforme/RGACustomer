package db;

import model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepo {
  Optional<Customer> get(UUID uuid);

  Optional<List<Customer>> getAll(UUID uuid);

  void create(Customer customer);

  void update(Customer customer);

  void delete(UUID uuid);
}
