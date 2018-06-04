package com.manas.uss.urlshorteningservice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.manas.uss.utils.IDConverter;

/**
 * @author manasranjan
 * test class for IDConverter.java
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IDConverterTest {
	

	@Test
	public void createUniqueIDTest() {
		assertEquals("h",IDConverter.createUniqueID(Long.valueOf(7)));
		
	}
	
	@Test
	public void getDictionaryKeyFromUniqueIDTest() {
		assertEquals(Long.valueOf(8),IDConverter.getDictionaryKeyFromUniqueID("i"));
	}

}
