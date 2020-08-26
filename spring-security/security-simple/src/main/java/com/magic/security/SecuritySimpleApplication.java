package com.magic.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 * @author magic
 */
@SpringBootApplication
public class SecuritySimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuritySimpleApplication.class, args);
    }

}
