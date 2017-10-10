package demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import demo.model.DeloitteEmployee;
import demo.model.Employee;
import demo.service.DBService;
import demo.service.Service;
import demo.util.GenericUtil;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CommonController {

	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	Service service;
	@Autowired
	DBService dbService;
	@Autowired
	GenericUtil genericUtil;
	@Value("${res}")
	private String res;
	/*@Autowired
	JdbcTemplate jdbcTemplate;*/


	@RequestMapping(value= "/welcome" ,method = RequestMethod.GET)
	public String sendWelcomeMessge() {
		return service.sendWelcomeMessge();
	}

	@RequestMapping(value= "/getAllEmployess" ,method = RequestMethod.GET )
	public List<Employee> getAllEmployess() throws Exception {
		return service.getAllEmployess();
	}

	@RequestMapping(value= "/getAllEmployess/{id}" ,method = RequestMethod.GET )
	public Employee putEmployess( @PathVariable("id") int id) {
		return service.getEmployess(id);
	}

	@RequestMapping(value= "/getAllEmployess" ,method = RequestMethod.POST )
	public List<Employee> addEmployess(@RequestBody String body) throws JsonParseException, JsonMappingException, IOException {
		Employee addEmp = new Employee(mapper.readTree(body).path("id").asInt(),mapper.readTree(body).path("name").asText(),mapper.readTree(body).path("designation").asText());
		return service.getAllEmployess(addEmp);
	}
	@RequestMapping(value= "/getAllEmployess/{id}" ,method = RequestMethod.PUT )
	public List<Employee> updateEmployess( @RequestBody String body,@PathVariable("id") int id) throws JsonProcessingException, IOException {
		Employee addEmp = new Employee(mapper.readTree(body).path("id").asInt(),mapper.readTree(body).path("name").asText(),mapper.readTree(body).path("designation").asText());
		return service.updateEmployess(addEmp,id);
	}

	@RequestMapping(value= "/getAllEmployess/{id}" ,method = RequestMethod.DELETE )
	public List<Employee> deleteEmployess( @PathVariable("id") int id) {
		return service.deleteEmployess(id);
	}

	/*
	 * IN MEMORY DB SERVICES STARTS
	 */


	@RequestMapping(value= "/getAllEmployessfromDB" ,method = RequestMethod.GET )
	public List<DeloitteEmployee> getAllEmployessfromDB() {
		return dbService.getAllEmployess();
	}

	@RequestMapping(value= "/getAllEmployessfromDB/{id}" ,method = RequestMethod.GET )
	public DeloitteEmployee putEmployessfromDB( @PathVariable("id") int id) {
		return dbService.getEmployess(id);
	}

	@RequestMapping(value= "/getAllEmployessfromDB" ,method = RequestMethod.POST )
	public List<DeloitteEmployee> addEmployessfromDB(@RequestBody String body) throws JsonParseException, JsonMappingException, IOException {
		DeloitteEmployee addEmp = new DeloitteEmployee(mapper.readTree(body).path("id").asInt(),mapper.readTree(body).path("name").asText(),mapper.readTree(body).path("designation").asText());
		return dbService.getAllEmployess(addEmp);
	}
	@RequestMapping(value= "/getAllEmployessfromDB/{id}" ,method = RequestMethod.PUT )
	public List<DeloitteEmployee> updateEmployessfromDB( @RequestBody String body,@PathVariable("id") int id) throws JsonProcessingException, IOException {
		DeloitteEmployee addEmp = new DeloitteEmployee(mapper.readTree(body).path("id").asInt(),mapper.readTree(body).path("name").asText(),mapper.readTree(body).path("designation").asText());
		return dbService.updateEmployess(addEmp,id);
	}

	@RequestMapping(value= "/getAllEmployessfromDB/{id}" ,method = RequestMethod.DELETE )
	public List<DeloitteEmployee> deleteEmployessfromDB( @PathVariable("id") int id) {
		return dbService.deleteEmployess(id);
	}

	/*
	 * IN MEMORY DB SERVICES ENDS
	 */




	@RequestMapping(value = "/getBrachAddress", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getBrachAddress(@RequestBody String body)
			throws JsonParseException, JsonMappingException,
			RestClientException, IOException {

		return new ResponseEntity<String>(mapper.writeValueAsString(service
				.getBrachAddress(body)), HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllBranchAddress", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getAllBranchAddress(@RequestBody String body) throws JsonParseException,
			JsonMappingException, RestClientException, IOException {
		//insertPersonDetails(body, httpServletRequest);
		return new ResponseEntity<String>(mapper.writeValueAsString(service
				.getAllBranchAddress(body)), HttpStatus.OK);
		/*return new ResponseEntity<String>(mapper.writeValueAsString(service
				.getAllBranchAddress(body)), HttpStatus.OK);*/
	}

	@RequestMapping(value = "/RESTServices/api/loadSearchResults", method = RequestMethod.POST, produces = "application/json")
	public JsonNode loadSearchResults(@RequestBody String body) throws JsonParseException,
			JsonMappingException, RestClientException, IOException {
		System.out.println("body "+body);

		JsonNode res1 = mapper.readTree(res);
		System.out.println(res1.toString());
		return res1;
		//return new ResponseEntity<String>(mapper.writeValueAsString(res1), HttpStatus.OK);
		//return service.getAllEmployess();
		//insertPersonDetails(body, httpServletRequest);
		//return new ResponseEntity<String>(mapper.writeValueAsString(res1.toString()), HttpStatus.OK);
		/*return new ResponseEntity<String>(mapper.writeValueAsString(service
				.getAllBranchAddress(body)), HttpStatus.OK);*/
	}


	@RequestMapping(value = "/insertPersonDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> insertPersonDetails(@RequestBody String body,
													  HttpServletRequest httpServletRequest) throws JsonParseException,
			JsonMappingException, RestClientException, IOException {

		/*String insertSql = "insert into [demoDataBase].[dbo].[Persons](LastName,FirstName,Address,City) VALUES('Peter','Dristopher','123 Cris Street','Chicago')";
		System.out.println(jdbcTemplate.update(insertSql));*/
		return null;
	}

	@RequestMapping(value = "/api/getChatMessages", method = RequestMethod.GET, produces = "text/html")
	public ResponseEntity<String> getChatMessages() throws JsonParseException, JsonMappingException, RestClientException, JsonProcessingException, IOException {
		ArrayNode n = mapper.createArrayNode();
		n.add(mapper.createObjectNode().put("text","Retail"));
		n.add(mapper.createObjectNode().put("text","Contractor"));
		n.add(mapper.createObjectNode().put("text","Restaraunt"));
		return new ResponseEntity<String>(n.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/getVehicles", method = RequestMethod.GET, produces = "text/html")
	public ResponseEntity<String> getVehicles() throws JsonParseException, JsonMappingException, RestClientException, JsonProcessingException, IOException {
		ArrayNode n = mapper.createArrayNode();
		n.add(mapper.createObjectNode().put("text","Private Passenger Auto"));
		n.add(mapper.createObjectNode().put("text","Motor Vehicles"));
		n.add(mapper.createObjectNode().put("text","Truck and Tractors"));
		return new ResponseEntity<String>(n.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/getCoverages", method = RequestMethod.GET, produces = "text/html")
	public ResponseEntity<String> getCoverages() throws JsonParseException, JsonMappingException, RestClientException, JsonProcessingException, IOException {
		ArrayNode n = mapper.createArrayNode();
		n.add(mapper.createObjectNode().put("text","Liability Coverage"));
		n.add(mapper.createObjectNode().put("text","Medical Expenses Coverage"));
		n.add(mapper.createObjectNode().put("text","Personal and Advertising Injury Coverage"));
		n.add(mapper.createObjectNode().put("text","Contractor's installation,Tools and Equipment Coverage"));
		n.add(mapper.createObjectNode().put("text","Employee Benefit Liability Coverage"));
		return new ResponseEntity<String>(n.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/sendEmail", method = RequestMethod.POST, produces = "text/html")
	public String sendEmail(@RequestParam("file") MultipartFile file, @RequestParam("body") String body) throws IOException, MessagingException {

		System.out.println("file "+file);
		System.out.println("body "+body);
		Map<String, String> map = new HashMap();

		JsonNode requestNode =  mapper.readTree(body).path("params").get(0);

		map.put("to", requestNode.path("toEmail").asText());
		map.put("cc", requestNode.path("ccEmail")!= null ? requestNode.path("ccEmail").asText():"");
		map.put("subject", requestNode.path("subject").asText());
		map.put("content", "text/html; charset=utf-8");
		Boolean isSucess;
		File convFile = new File(file.getOriginalFilename());
		try {


			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
			isSucess = genericUtil.sendMail(map, requestNode.path("body").asText(),convFile.getPath());
		}finally {
			convFile.delete();
		}

		ObjectNode finalNode = mapper.createObjectNode();
		if(isSucess){
			finalNode.put("result", "Email Sent Sucessfully");
		} else {
			finalNode.put("result","Email Sending Failed");
		}

		return finalNode.toString();
	}

}
