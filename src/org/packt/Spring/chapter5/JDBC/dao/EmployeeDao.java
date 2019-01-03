package org.packt.Spring.chapter5.JDBC.dao;

import org.packt.Spring.chapter5.JDBC.model.Employee;

public interface EmployeeDao {	
	int getEmployeeCount();
	int inserEmployee(Employee employee);
	int deleteEmployeeById(int empId);
	Employee getEmployeeById(int empId);
	
}
