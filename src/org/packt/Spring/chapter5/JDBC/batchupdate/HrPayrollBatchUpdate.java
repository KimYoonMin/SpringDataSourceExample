package org.packt.Spring.chapter5.JDBC.batchupdate;

import java.util.ArrayList;
import java.util.List;

import org.packt.Spring.chapter5.JDBC.dao.EmployeeDaoImpl;
import org.packt.Spring.chapter5.JDBC.model.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HrPayrollBatchUpdate {

	public static void main(String[] args) {
		ApplicationContext context = new
				ClassPathXmlApplicationContext("Spring.xml");
		EmployeeDaoImpl employeeDaoImpl = 
				(EmployeeDaoImpl) context.getBean("employeeDaoImpl");
		
		List<Employee> employeeList = new ArrayList<Employee>();
		Employee employee1 = new Employee(10001, "Ravi");
		Employee employee2 = new Employee(23330, "Kant");
		Employee employee3 = new Employee(12568, "Soni");
		employeeList.add(employee1);
		employeeList.add(employee2);
		employeeList.add(employee3);
		employeeDaoImpl.insertEmployee(employeeList);
		System.out.println(employeeDaoImpl.getEmployeeCount());
		System.out.println("프로시져 : "+employeeDaoImpl.getEmployee(10001).getName());

	}

}
