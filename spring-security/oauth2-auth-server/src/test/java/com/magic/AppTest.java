package com.magic;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void passwordEncoder(){
        System.out.println(passwordEncoder.encode("123456"));
    }
}
