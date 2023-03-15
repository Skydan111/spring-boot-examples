package com.skydan;

import com.skydan.customer.Customer;
import com.skydan.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            Customer alex = new Customer(
                    "Alex",
                    "alex@mail.com",
                    28

            );
            Customer kate = new Customer(
                    "Kate",
                    "kate@mail.com",
                    21

            );

            List<Customer> customers = List.of(alex, kate);
            customerRepository.saveAll(customers);
        };
    }

}
