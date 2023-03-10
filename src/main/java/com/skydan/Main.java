package com.skydan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/")
    public GreetResponce greet(){
        GreetResponce responce = new GreetResponce(
                "Hello",
                List.of("Java", "JS"),
                new Person("Oleg", 36));
        return responce;
    }
    record Person(String name, int age){}
    record GreetResponce(String greet, List<String> favProgrammingLanguages, Person person){}
}
