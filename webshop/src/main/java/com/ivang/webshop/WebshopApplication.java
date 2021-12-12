package com.ivang.webshop;

import java.util.ArrayList;
import java.util.List;

import com.ivang.webshop.dto.AdminDTO;
import com.ivang.webshop.dto.BuyerDTO;
import com.ivang.webshop.entity.Admin;
import com.ivang.webshop.entity.Buyer;
import com.ivang.webshop.entity.Order;
import com.ivang.webshop.service.AdminService;
import com.ivang.webshop.service.BuyerService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run (AdminService adminService) {
		Admin admin1 = new Admin(null, "Taylor", "Ellis", "taylor", "12345", false);
		Admin admin2 = new Admin(null, "Josh", "Paul", "josh", "12345", false);
		Admin admin3 = new Admin(null, "Cody", "Cook", "cody", "12345", false);

		return args -> {
			adminService.save(new AdminDTO(admin1));
			adminService.save(new AdminDTO(admin2));
			adminService.save(new AdminDTO(admin3));
		};
	}

	@Bean
	CommandLineRunner runAgain (BuyerService buyerService) {
		List<Order> orders = new ArrayList<Order>();
		Buyer buyer1 = new Buyer(null, "John", "Cena", "john", "12345", false, "2125 Chestnut Street", orders);

		return args -> {
			buyerService.save(new BuyerDTO(buyer1));
		};
	}

}
