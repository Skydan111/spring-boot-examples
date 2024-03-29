package com.skydan.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{

    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(
                1,
                "Alex",
                "alex@mail.com",
                28

        );
        customers.add(alex);
        Customer kate = new Customer(
                2,
                "Kate",
                "kate@mail.com",
                21

        );
        customers.add(kate);
    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
       return customers.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerWithId(Integer customerId) {
        return customers.stream()
                .anyMatch(customer -> customer.getId().equals(customerId));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        customers.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .ifPresent(customers::remove);
    }
}
