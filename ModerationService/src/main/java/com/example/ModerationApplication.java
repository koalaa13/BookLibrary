package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class ModerationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModerationApplication.class, args);
    }
}
