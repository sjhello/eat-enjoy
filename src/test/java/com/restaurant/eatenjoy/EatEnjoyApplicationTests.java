package com.restaurant.eatenjoy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CachingConfigurationSelector;

@SpringBootTest(properties = {"jasypt.encryptor.password=enejaoty"})
@EnableAutoConfiguration(exclude = CachingConfigurationSelector.class)
class EatEnjoyApplicationTests {

	@Test
	void contextLoads() {
	}

}
