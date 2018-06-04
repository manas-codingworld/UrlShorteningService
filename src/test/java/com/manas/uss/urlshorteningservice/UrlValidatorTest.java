package com.manas.uss.urlshorteningservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.manas.uss.utils.UrlValidator;

/**
 * @author manasranjan
 * test class for UrlValidator.java
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlValidatorTest {
	
	
	@Test
	public void validateURLTrueTest() {
		assertTrue(UrlValidator.validateURL("https://www.glassdoor.co.in/index.htm"));
		
	}
	
	@Test
	public void validateURLFalseTest() {
		assertFalse(UrlValidator.validateURL("https://www.glassdo"));
	}

}
