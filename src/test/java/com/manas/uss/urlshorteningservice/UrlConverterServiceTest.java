package com.manas.uss.urlshorteningservice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.manas.uss.businessservices.URLConverterService;

/**
 * @author manasranjan
 * test class for UrlConverterService.java
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlConverterServiceTest {

	@Autowired
	URLConverterService urlConverterService;
	
	@Test
	public void contextLoads() {
		assertEquals("h",urlConverterService.shortenURL("", "https://www.glassdoor.co.in/index.htm"));
	}

}
