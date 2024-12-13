package com.raontec.carbookingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CarBookingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarBookingApiApplication.class, args);
    }

}
