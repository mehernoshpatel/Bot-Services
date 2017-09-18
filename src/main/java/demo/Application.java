package demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application  {
	
	
    public static void main( String[] args ) throws IOException
    {
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
}
}

