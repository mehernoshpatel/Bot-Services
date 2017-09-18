package demo.util;

import org.springframework.data.repository.CrudRepository;

import demo.model.DeloitteEmployee;

public interface EmployeeRepository extends CrudRepository<DeloitteEmployee, Integer>{

}
