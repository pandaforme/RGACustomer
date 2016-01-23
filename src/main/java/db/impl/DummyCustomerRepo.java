package db.impl;

import db.CustomerRepo;
import model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DummyCustomerRepo implements CustomerRepo {
  @Override
  public Optional<Customer> get(UUID uuid) {
    return Optional.of(new Customer());
  }

  @Override
  public Optional<List<Customer>> getAll(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public void create(Customer customer) {

  }

  @Override
  public void update(Customer customer) {

  }

  @Override
  public void delete(UUID uuid) {

  }
}
