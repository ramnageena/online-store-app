package com.online_store;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@OpenAPIDefinition(info = @Info(title = "Online Store Product Management API",
		description = "A comprehensive API for managing products in the online store. Features include creating new products, updating existing ones, retrieving product details, and deleting products. This service ensures seamless integration for product management operations.",
		contact = @Contact(
				name = "Ram Tiwari",
				email = " ramtiwari8716@gmail.com"
		)
)
)
public class OnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApplication.class, args);
		log.info("Online-Store APP Started !!!!");
	}

}
