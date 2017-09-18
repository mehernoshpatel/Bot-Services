package demo.client;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;




import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.util.GenericUtil;

@Component
@EnableCaching
public class GetBranchAddressClient extends BaseClient {
	ObjectMapper mapper = new ObjectMapper();
	static final Logger logger = LogManager.getLogger(GetBranchAddressClient.class);
	
	
	@Autowired
	GenericUtil genericUtil;
	public String getBrachAddress(String body) throws JsonParseException, JsonMappingException, RestClientException, IOException {
		HttpEntity<String> entity = new HttpEntity<String>(body);
		return genericUtil.thirdPartyCall(mapper.readTree(body).path("thirdPartURL").asText(), entity);
	
	
	}
	
	@Cacheable( value="demoCache",key= "#root.target.GET_ALL_BRANCH")
	public String getAllBranchAddress(String body) throws JsonParseException, JsonMappingException, RestClientException, IOException {
		
		HttpEntity<String> entity = new HttpEntity<String>(body);
		logger.info("NOW IT WILL BE CALLING AN EXTERNAL SERVICE");
		return genericUtil.thirdPartyCall(mapper.readTree(body).path("thirdPartURL").asText(), entity);
	
	
	}
	
	

}
