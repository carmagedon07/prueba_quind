package com.order_service.orderServiceAplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

<<<<<<< HEAD
@SpringBootTest
=======
@SpringBootTest(properties = {
        "spring.sql.init.mode=never",
        "spring.r2dbc.initialization-mode=never"
})
>>>>>>> develop_checkout_2
class OrderServiceAplicationApplicationTests {

	@Test
	void contextLoads() {
	}

}
