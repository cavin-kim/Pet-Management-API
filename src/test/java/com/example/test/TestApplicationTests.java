package com.example.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = MyApplication.class)
@ActiveProfiles("test")
class TestApplicationTests {

	@Test
	void contextLoads() {
	}

}
