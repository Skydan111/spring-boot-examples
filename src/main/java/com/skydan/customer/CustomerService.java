package com.skydan.customer;

import com.skydan.exception.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }
    public Customer getCustomer(Integer customerId){
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFound(
                        "customer with id [%s] not found".formatted(customerId)
                ));
    }
}
