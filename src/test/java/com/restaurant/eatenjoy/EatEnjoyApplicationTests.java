package com.restaurant.eatenjoy;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CachingConfigurationSelector;

@Ignore
@SpringBootTest(properties = {"jasypt.encryptor.password=enejaoty"})
class EatEnjoyApplicationTests {

	@Test
	void contextLoads() {
	}

}
