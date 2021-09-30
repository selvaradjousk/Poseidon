package com.nnk.springboot.UnitTests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.util.UserMapper;

@DisplayName("User Mapper - UNIT TESTS")
class UserMapperTest {

	User user = new User("Username", "Password", "Fullname", "Role");
	UserDTO userDTO = new UserDTO("Username", "Password", "Fullname", "Role");

	// *******************************************************************	
	
	@DisplayName("DTO to Entity - "
			+ "GIVEN DTO  "
			+ "WHEN Requested to DO"
			+ "THEN returns DO")
	@Test
	void testToUser() {
		UserMapper mapper = new UserMapper();
		User entity = mapper.toUser(userDTO);
		
		assertEquals(entity.getFullname(), user.getFullname());
		assertEquals(entity.getUsername(), user.getUsername());
		assertEquals(entity.getPassword(), user.getPassword());
		assertEquals(entity.getRole(), user.getRole());
	}
	
	
	
	// *******************************************************************	
	
	@DisplayName(" DTO to Entity null DTO Exception- "
			+ "GIVEN DTO null "
			+ "WHEN Requested to DO"
			+ "THEN returns Exception")
	@Test
    public void testToEntityNullException() {
		UserMapper mapper = new UserMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toUser(null).getUsername());
	}

	// *******************************************************************	
	
	@DisplayName("DO to DTO - "
			+ "GIVEN DO  "
			+ "WHEN Requested to DTO"
			+ "THEN returns DTO")
	@Test
	void testToUserDTO() {
		UserMapper mapper = new UserMapper();
		UserDTO dto = mapper.toUserDTO(user);
		
		assertEquals(dto.getFullname(), userDTO.getFullname());
		assertEquals(dto.getUsername(), userDTO.getUsername());
		assertEquals(dto.getPassword(), userDTO.getPassword());
		assertEquals(dto.getRole(), userDTO.getRole());
	}	
	
	// *******************************************************************	
	
	@DisplayName("DO to DTO null DO Exception- "
			+ "GIVEN DO null "
			+ "WHEN Requested to DTO"
			+ "THEN returns Exception")
	@Test
    public void testToDTONullException() {
	
		UserMapper mapper = new UserMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toUserDTO(null).getId());
	}
	
	
	// *******************************************************************	
}
