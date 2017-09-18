package demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import demo.client.GetBranchAddressClient;
import demo.model.Employee;

@org.springframework.stereotype.Service
public class Service {
	

	private List<Employee> emp = new ArrayList<Employee>(Arrays.asList(new Employee(1,"Kate","BTA"),
			new Employee(2,"Kurt","Consultant"),
			new Employee(3,"Leo","Manager")
			));
			
			
	@Autowired 
	GetBranchAddressClient getBranchAddressClient;

	@Value("${WELCOMEMESSAGE}")
	private String welcomeMessage;
	@Value("${ENV}")
	private String env;
	
	public String sendWelcomeMessge(){
		
		return welcomeMessage+env;
	}

	public String getBrachAddress(String body) throws JsonParseException, JsonMappingException, RestClientException, IOException {
		return getBranchAddressClient.getBrachAddress(body);
	}
	
	public String getAllBranchAddress(String body) throws JsonParseException, JsonMappingException, RestClientException, IOException {
		return getBranchAddressClient.getAllBranchAddress(body);
	}

	public List<Employee> getAllEmployess() {
		return emp;
	}

	public Employee getEmployess(int id) {

		Employee returnEmp = null;
		for(Employee employee: emp){
			if(employee.getId()==id){
				returnEmp = employee;
			}
		}
		return returnEmp;
	}

	public List<Employee> getAllEmployess(Employee body) {
		emp.add(body);
		return emp;
		
	}

	public List<Employee> updateEmployess(Employee addEmp, int id) {
		for(Employee employee: emp){
			if(employee.getId()==id){
				emp.remove(employee);
				emp.add(addEmp);
				break;
			}
		}
		return emp;
	}

	public List<Employee> deleteEmployess(int id) {
		for(Employee employee: emp){
			if(employee.getId()==id){
				emp.remove(employee);
				break;
			}
		}
		return emp;
	}

	
}
