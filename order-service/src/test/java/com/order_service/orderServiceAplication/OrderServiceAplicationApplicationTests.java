package com.order_service.orderServiceAplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.sql.init.mode=never",
        "spring.r2dbc.initialization-mode=never"
})
class OrderServiceAplicationApplicationTests {

	@Test
	void contextLoads() {
	}

}
