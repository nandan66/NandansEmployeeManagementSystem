package com.fullstack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.exception.ResourceNotFoundException;
import com.fullstack.model.Employee;
import com.fullstack.repository.EmployeeRepository;

@RestController
@RequestMapping("EmployeeManagementApp")
@CrossOrigin(origins="http://localhost:3000")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	@GetMapping("EmployeesList")
	public List<Employee> getAllEmployees(){
		List<Employee>employeesList=null;
			 employeesList=employeeRepository.findAll();
	   return employeesList;
	}
	@PostMapping("/save")
	public Employee createEmployee(@RequestBody Employee employee) {
		System.out.println("in create Employee"+employee);
		return employeeRepository.save(employee);
		}
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
	Employee employee=employeeRepository.findById(id).
			orElseThrow(()->new ResourceNotFoundException("Employee with id "+id+" not found"));
	return new ResponseEntity<Employee>(employee,HttpStatus.OK);
		
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@PathVariable long id,@RequestBody Employee employee){
		Employee employeeDetails=employeeRepository.findById(id).
				orElseThrow(()->new ResourceNotFoundException("Employee with id "+id+" not found"));
		employeeDetails.setFirstName(employee.getFirstName());
		employeeDetails.setLastName(employee.getLastName());
		employeeDetails.setMailId(employee.getMailId());
		Employee updatedEmployee=employeeRepository.save(employeeDetails);
		return new ResponseEntity<Employee>(updatedEmployee,HttpStatus.OK);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployeeById(@PathVariable Long id){
		Employee employeeDetails=employeeRepository.findById(id).
		orElseThrow(()->new ResourceNotFoundException("Employee with id "+id+" not found"));
		employeeRepository.delete(employeeDetails);
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return new ResponseEntity<Map<String,Boolean>>(response,HttpStatus.OK);
		
		
	}
	
	
}
