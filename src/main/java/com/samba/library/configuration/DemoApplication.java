package com.samba.library.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan("com.samba.library.book.model")
@EnableJpaRepositories("com.samba.library.book.persistence")
@SpringBootApplication(scanBasePackages = {
        "com.samba.library","com.samba.library.configuration"
})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
