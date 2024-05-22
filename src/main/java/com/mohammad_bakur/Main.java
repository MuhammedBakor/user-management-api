package com.mohammad_bakur;

import com.mohammad_bakur.user.models.User;
import com.mohammad_bakur.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext
				= SpringApplication.run(Main.class, args);

//		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//
//		for (String be: applicationContext.getBeanDefinitionNames()){
//			System.out.println(be);
//		}


	}

	@Bean
	CommandLineRunner runner(UserRepository userRepo){
		return args -> {
			User alex = new User(
					1, "Alex", "alex@gmail.com",21
			);

			User jamila = new User(
					2, "Jamila", "jamila@gmail.com",19
			);
			List<User> users = List.of(alex, jamila);
			userRepo.saveAll(users);
		};
	}
}
