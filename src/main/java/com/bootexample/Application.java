package com.bootexample;

import com.bootexample.entity.User;
import com.bootexample.model.Module;
import com.bootexample.repository.UserRepository;
import com.bootexample.util.Specifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);

        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository) {
		return (args) -> {
            User superUser = new User();
            superUser.setId(1);
            superUser.setName("user1");
            superUser.setEmail("email1@email.com");
            superUser.setPassword("pass1");
            superUser.setModules(Arrays.asList(Module.AUTH.name(), Module.SETTINGS.name()));

            userRepository.save(superUser);

            User user2 = new User();
            user2.setName("user2");
            user2.setEmail("email2@email.com");
            user2.setPassword("pass2");
            user2.setModules(Arrays.asList(Module.AUTH.name()));

            userRepository.save(user2);

            User user3 = new User();
            user3.setName("user3");
            user3.setEmail("email3@email.com");
            user3.setPassword("pass3");
            user3.setModules(Arrays.asList(Module.AUTH.name()));

            userRepository.save(user3);

			log.info("---------- USERS -------------");

			log.info("Users found with findAll():");
			log.info("-------------------------------");

            userRepository.findAll().forEach(u -> log.info(u.toString()));

            log.info("");

			log.info("Users found with findAll(+email):");
			log.info("-------------------------------");

			userRepository.findAll(Specifications.findAllUsers("+email")).forEach(u -> log.info(u.toString()));

			log.info("");

			log.info("Users found with findAll(-name):");
			log.info("-------------------------------");
			userRepository.findAll(Specifications.findAllUsers("-name")).forEach(u -> log.info(u.toString()));

			log.info("-------------------------------");
		};
	}
}