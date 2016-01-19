package db;

import model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepo {
  Customer get(UUID uuid);

  List<Customer> getAll();

  void create(Customer customer);

  void update(Customer customer);

  void delete(UUID uuid);
}
