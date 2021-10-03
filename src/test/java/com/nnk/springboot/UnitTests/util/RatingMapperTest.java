package com.nnk.springboot.UnitTests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.util.RatingMapper;

@DisplayName("Mapper ==> Rating - UNIT TESTS")
class RatingMapperTest {

	Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
	RatingDTO ratingDTO = new RatingDTO("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

	// *******************************************************************	
	
	@DisplayName("DTO to Entity - "
			+ "GIVEN DTO  "
			+ "WHEN Requested to DO"
			+ "THEN returns DO")	
	@Test
	void testToRating() {
		RatingMapper mapper = new RatingMapper();
		Rating entity = mapper.toRating(ratingDTO);
		
		assertEquals(entity.getMoodysRating(), rating.getMoodysRating());
		assertEquals(entity.getSandPRating(), rating.getSandPRating());
		assertEquals(entity.getFitchRating(), rating.getFitchRating());
	}
	
	
	// *******************************************************************	
	
	@DisplayName(" DTO to Entity null DTO Exception- "
			+ "GIVEN DTO null "
			+ "WHEN Requested to DO"
			+ "THEN returns Exception")
	@Test
    public void testToEntityNullException() {
		RatingMapper mapper = new RatingMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toRating(null).getMoodysRating());
	}

	// *******************************************************************	
	
	@DisplayName("DO to DTO - "
			+ "GIVEN DO  "
			+ "WHEN Requested to DTO"
			+ "THEN returns DTO")	
	@Test
	void testToRatingDTO() {
		RatingMapper mapper = new RatingMapper();
		RatingDTO dto = mapper.toRatingDTO(rating);
		
		assertEquals(dto.getMoodysRating(), ratingDTO.getMoodysRating());
		assertEquals(dto.getSandPRating(), ratingDTO.getSandPRating());
		assertEquals(dto.getFitchRating(), ratingDTO.getFitchRating());
	}
	
	// *******************************************************************	
	
	@DisplayName("DO to DTO null DO Exception- "
			+ "GIVEN DO null "
			+ "WHEN Requested to DTO"
			+ "THEN returns Exception")
	@Test
    public void testToDTONullException() {
	
		RatingMapper mapper = new RatingMapper();
	
    assertThrows(NullPointerException.class, ()
     		  -> mapper.toRatingDTO(null).getMoodysRating());
	}
	
	
	// *******************************************************************	
}
