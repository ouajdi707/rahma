package com.esprit.spring.ftthback;

import com.esprit.spring.ftthback.models.Role;
import com.esprit.spring.ftthback.models.User;
import com.esprit.spring.ftthback.repository.RoleRepository;
import com.esprit.spring.ftthback.security.WebSecurityConfig;
import com.esprit.spring.ftthback.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class FtthBackApplication {

	@Autowired
	UserRepository userRepository;
	@Autowired
    RoleRepository roleRepository;
	@Autowired
    WebSecurityConfig webSecurityConfig;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(FtthBackApplication.class, args);
	}



	@Bean
	CommandLineRunner start() {


		return args -> {
			System.out.println("run");
			List<Role> roles = (List<Role>) roleRepository.findAll();
			if (roles.isEmpty()) {
				Role role = new Role();
				role.setName("ROLE_USER");
				roleRepository.save(role);
				Role role2 = new Role();
				role2.setName("ROLE_ADMIN");
				roleRepository.save(role2);
				Role roleManager = new Role();
				roleManager.setName("ROLE_RESPONSABLE");
				roleRepository.save(roleManager);

			}
			if (!userRepository.existsByEmail("rahma@gmail.com")) {
				String bcrypt = webSecurityConfig.passwordEncoder().encode("12345678");
				List<Role> rolesAdmin = new ArrayList<>();
				Role r = roleRepository.findByName("ROLE_ADMIN")
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				rolesAdmin.add(r);
				User user = new User();
				user.setRoles(rolesAdmin);
				user.setUsername("admin");
				user.setPassword(bcrypt);
				user.setEmail("rahma@gmail.com");
				user.setRefnv("aaa");
				user.setGrade("aaa");
				user.setStatus("aaaa");
				user.setNumtel("55555555");
				userRepository.save(user);
			}
		};
	}




}
