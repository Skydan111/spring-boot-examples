package com.skydan.customer;

import com.skydan.exception.DuplicateResourceException;
import com.skydan.exception.RequestValidationException;
import com.skydan.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }
    public Customer getCustomer(Integer customerId){
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        String email = customerRegistrationRequest.email();
        if(customerDao.existsCustomerWithEmail(email)) {
            throw new DuplicateResourceException("email already taken");
        }

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );

        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer customerId) {
        if(!customerDao.existsCustomerWithId(customerId)){
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(customerId));
        }
        customerDao.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = getCustomer(customerId);

        boolean changes = false;

        if(customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())){
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if(customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getEmail())){
            if(customerDao.existsCustomerWithEmail(customerUpdateRequest.email())){
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }

        if(customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(customer.getAge())){
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }
}

