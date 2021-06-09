package com.restaurant.eatenjoy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableAutoConfiguration(exclude = {EatEnjoyApplicationTests.class})
class EatEnjoyApplicationTests {

	@Test
	void contextLoads() {
	}

}
