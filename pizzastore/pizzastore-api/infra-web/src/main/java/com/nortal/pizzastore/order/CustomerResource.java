package com.nortal.pizzastore.order;

import com.nortal.pizzastore.domain.order.Customer;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class CustomerResource {
  public String name;
  public String email;
  public String phone;
  public String address;

  CustomerResource(Customer customer) {
    name = customer.getName();
    email = customer.getEmail();
    phone = customer.getPhone();
    address = customer.getAddress();
  }
}
