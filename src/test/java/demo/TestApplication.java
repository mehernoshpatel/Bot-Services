package demo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.controller.CommonController;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class TestApplication {

	@Autowired
	CommonController commonController;
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void sendWelcomeMessge() throws JsonProcessingException, IOException{
		
		assertTrue(commonController.sendWelcomeMessge().contains("Welcome!!Good Morning"));
	}
}
