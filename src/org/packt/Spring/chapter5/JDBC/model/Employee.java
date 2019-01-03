package org.packt.Spring.chapter5.JDBC.model;

public class Employee {
	private int empId;
	private String name;
	
	public Employee(int empId, String name) {
		setEmpId(empId);
		setName(name);
		
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
