package db.impl;

import db.CustomerRepo;
import model.Customer;

import java.util.List;
import java.util.UUID;

public class DummyCustomerRepo implements CustomerRepo {
  @Override
  public Customer get(UUID uuid) {
    return Customer.builder()
                   .age(18)
                   .email("default@email.com")
                   .name("default " + uuid)
                   .build();
  }

  @Override
  public List<Customer> getAll() {
    return null;
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
