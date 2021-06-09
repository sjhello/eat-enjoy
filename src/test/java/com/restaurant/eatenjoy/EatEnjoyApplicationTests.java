package com.restaurant.eatenjoy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"jasypt.encryptor.password=enejaoty"})
@EnableAutoConfiguration(exclude = RedisAutoConfiguration.class)
class EatEnjoyApplicationTests {

	@Test
	void contextLoads() {
	}

}
