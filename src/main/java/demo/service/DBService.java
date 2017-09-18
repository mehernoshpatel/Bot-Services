package demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.model.DeloitteEmployee;
import demo.util.EmployeeRepository;

@Service
public class DBService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	

	public List<DeloitteEmployee> getAllEmployess() {
		List<DeloitteEmployee> retList = new ArrayList<DeloitteEmployee>();
		Iterator<DeloitteEmployee> it = employeeRepository.findAll().iterator();
		while(it.hasNext()){
			DeloitteEmployee emp = it.next();
			retList.add(emp);
		}
		return retList;
	}

	public DeloitteEmployee getEmployess(int id) {

		return employeeRepository.findOne(id);
		
	}

	public List<DeloitteEmployee> getAllEmployess(DeloitteEmployee body) {
		employeeRepository.save(body);
		List<DeloitteEmployee> retList = new ArrayList<DeloitteEmployee>();
		Iterator<DeloitteEmployee> it = employeeRepository.findAll().iterator();
		while(it.hasNext()){
			DeloitteEmployee emp = it.next();
			retList.add(emp);
		}
		return retList;
		
	}

	public List<DeloitteEmployee> updateEmployess(DeloitteEmployee addEmp, int id) {
		employeeRepository.save(addEmp);
		List<DeloitteEmployee> retList = new ArrayList<DeloitteEmployee>();
		Iterator<DeloitteEmployee> it = employeeRepository.findAll().iterator();
		while(it.hasNext()){
			DeloitteEmployee emp = it.next();
			retList.add(emp);
		}
		return retList;
	}

	public List<DeloitteEmployee> deleteEmployess(int id) {
		employeeRepository.delete(id);
		List<DeloitteEmployee> retList = new ArrayList<DeloitteEmployee>();
		Iterator<DeloitteEmployee> it = employeeRepository.findAll().iterator();
		while(it.hasNext()){
			DeloitteEmployee emp = it.next();
			retList.add(emp);
		}
		return retList;
	}

}
