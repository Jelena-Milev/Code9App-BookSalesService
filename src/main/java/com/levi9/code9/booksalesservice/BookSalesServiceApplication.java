package com.levi9.code9.booksalesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication(scanBasePackageClasses = SecurityConfiguration.class)
@EnableEurekaClient
@EnableFeignClients
public class BookSalesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSalesServiceApplication.class, args);
	}

}
