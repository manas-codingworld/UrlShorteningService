package com.manas.uss.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
/**
 * 
 * @author manasranjan
 * utility class for encoding and decoding id/short url
 */
public class IDConverter {

	private IDConverter() {	}

	//initializing the data structures while class loading
	static{
		initializeCharToIndexTable();
		initializeIndexToCharTable();
	}
	
	private static HashMap<Character, Integer> charToIndexTable;
	private static List<Character> indexToCharTable;

	/**
	 * storing character and corresponding integer in the map
	 */
	private static void initializeCharToIndexTable() {
		charToIndexTable = new HashMap<>();
		// 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
		for (int i = 0; i < 26; ++i) {
			char c = 'a';
			c += i;
			charToIndexTable.put(c, i);
		}
		for (int i = 26; i < 52; ++i) {
			char c = 'A';
			c += (i-26);
			charToIndexTable.put(c, i);
		}
		for (int i = 52; i < 62; ++i) {
			char c = '0';
			c += (i - 52);
			charToIndexTable.put(c, i);
		}
	}

	/**
	 * storing character in the list
	 */
	private static void initializeIndexToCharTable() {
		// 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
		indexToCharTable = new ArrayList<>();
		for (int i = 0; i < 26; ++i) {
			char c = 'a';
			c += i;
			indexToCharTable.add(c);
		}
		for (int i = 26; i < 52; ++i) {
			char c = 'A';
			c += (i-26);
			indexToCharTable.add(c);
		}
		for (int i = 52; i < 62; ++i) {
			char c = '0';
			c += (i - 52);
			indexToCharTable.add(c);
		}
	}

	/**
	 * 
	 * @param uniqueID in the path param in the endpoint
	 * @return getting the id from unique id
	 */
	public static Long getDictionaryKeyFromUniqueID(String uniqueID) {
		List<Character> base62Number = new ArrayList<>();
		for (int i = 0; i < uniqueID.length(); ++i) {
			base62Number.add(uniqueID.charAt(i));
		}
		Long dictionaryKey = convertBase62ToBase10ID(base62Number);
		return dictionaryKey;
	}

	/**
	 * 
	 * @param ids - list of characters
	 * @return id as key in the redis hash
	 */
	private static Long convertBase62ToBase10ID(List<Character> ids) {
		long id = 0L;
		int exp = ids.size() - 1;
		for (int i = 0; i < ids.size(); ++i, --exp) {
			int base10 = charToIndexTable.get(ids.get(i));
			id += (base10 * Math.pow(62.0, exp));
		}
		return id;
	}

	/**
	 * @param id is the incremented ID from the redis server
	 * @return encoded value using id
	 */
	public static String createUniqueID(Long id) {
		List<Integer> base62ID = convertBase10ToBase62ID(id);
		StringBuilder uniqueURLID = new StringBuilder();
		for (int digit: base62ID) {
			uniqueURLID.append(indexToCharTable.get(digit));
		}
		return uniqueURLID.toString();
	}
	
	/**
	 * @param id is the incremented ID from the redis server
	 * @return list of integers decoded from the id
	 */
	private static List<Integer> convertBase10ToBase62ID(Long id) {
		List<Integer> digits = new LinkedList<>();
		while(id > 0) {
			int remainder = (int)(id % 62);
			((LinkedList<Integer>) digits).addFirst(remainder);
			id /= 62;
		}
		return digits;
	}
}