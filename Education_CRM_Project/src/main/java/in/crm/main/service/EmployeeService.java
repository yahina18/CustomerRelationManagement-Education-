package in.crm.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.crm.main.entities.Employee;
import in.crm.main.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;
	
	public void addEmployee(Employee emp)
	{
		repository.save(emp);
	}

	public Employee getEmployeeDetails(String employeeEmail)
	{
		return repository.findByEmail(employeeEmail);
	}
	
	public Page<Employee> getAllEmployeeDetailsByPagination(Pageable pageable)
	{
		return repository.findAll(pageable);
	}
	
	public void updateEmployeeDetails(Employee employee)
	{
		repository.save(employee);
	}
	
	public void deleteEmployeeDetails(String employeeEmail)
	{
		Employee employee = repository.findByEmail(employeeEmail);
		if(employee != null)
		{
			repository.delete(employee);
		}
		else
		{
			throw new RuntimeException("Employee not found with email : "+employeeEmail);
		}
	}
	
	
	public boolean loginEmp(String email, String password)
	{
	     Employee emp = repository.findByEmail(email);
	     if(emp != null)
	     {
	    	 if(emp.getPassword().equals(password))
	    	 {
	    		 return true;
	    	 }
	     }
	     return false;
	}
	
	
	
}
