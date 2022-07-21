package com.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}

@RefreshScope
@RestController
class MessageRestController {

    @Value("${service.name}")
    private String name;

    @RequestMapping("/name")
    String getMessage() {
        return this.name;
    }
}