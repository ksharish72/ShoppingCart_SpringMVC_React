package shoppingcart.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Public {
	public static void printJsonString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(object);
			System.out.println("JSON = " + json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
