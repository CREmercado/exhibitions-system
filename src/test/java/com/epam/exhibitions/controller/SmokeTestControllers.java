package com.epam.exhibitions.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class SmokeTestControllers {

    @Autowired
    private UserController userController;

    @Autowired
    private ExhibitionController exhibitionController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
        assertThat(exhibitionController).isNotNull();
    }
}
