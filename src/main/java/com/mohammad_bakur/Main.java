package com.mohammad_bakur;

import com.github.javafaker.Faker;
import com.mohammad_bakur.client.models.Client;
import com.mohammad_bakur.client.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Random;

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
	CommandLineRunner runner(ClientRepository userRepo){
		return args -> {
			Faker faker = new Faker();
			Random random = new Random();
			String firstname = faker.name().firstName();
			String lastname = faker.name().lastName();
			String fullname = firstname + " " +lastname;

			Client client = new Client(
					fullname,
					firstname.toLowerCase()+"."+lastname.toLowerCase()+"@moody.com",
					random.nextInt(16, 99)
			);


			userRepo.save(client);
		};
	}
}