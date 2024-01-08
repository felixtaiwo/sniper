package com.mintyn.codingtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = CodingTestApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class TestApplication {
	@Inject
	ObjectMapper mapper;
	@Inject
	protected MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

}
