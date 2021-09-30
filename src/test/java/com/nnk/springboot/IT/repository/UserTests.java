package com.nnk.springboot.IT.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;

//@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
class UserTests {

	@Autowired
	private UserRepository userRepository;

	
	@Test
	public void userTest() {
		User user = new User("Username", "Password", "Fullname", "Role");

		
		// Save
		user = userRepository.save(user);
		assertNotNull(user.getUsername());
		assertTrue(user.getUsername().equals("Username"));

		// Update
		user.setUsername("user Name Update");
		user = userRepository.save(user);
		assertTrue(user.getUsername().equals("user Name Update"));

		// Find
		List<User> listResult = userRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = user.getId();
		userRepository.delete(user);
		Optional<User> userList = userRepository.findById(id);
		assertFalse(userList.isPresent());
	}

}
