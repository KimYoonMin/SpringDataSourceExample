package org.packt.Spring.chapter5.JDBC.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.packt.Spring.chapter5.JDBC.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;





@Repository
public class EmployeeDaoImpl implements EmployeeDao{
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall jdbcCall;
	
	@Autowired	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcCall = new SimpleJdbcCall(this.dataSource).withProcedureName("getEmployee");
	}

	public void insertEmployee(final List<Employee> employees) {
		jdbcTemplate.batchUpdate("insert into employee(id, name) values(?, ?)",
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Employee employee = employees.get(i);
						ps.setLong(1, employee.getEmpId());
						ps.setString(2, employee.getName());
						
					}
					
					@Override
					public int getBatchSize() {
						return employees.size();
					}
				}); 
	}
	
	public int getEmployeeCount() {
		String sql = "select count(*) from employee";
		return jdbcTemplate.queryForInt(sql);
	}
	

	@Override
	public int inserEmployee(Employee employee) {
		String insertQuery = "insert into employee (Empid, Name) values(?, ?)";
		Object[] params = new Object[] {employee.getEmpId(), employee.getName()};
		int[] types = new int[] {Types.INTEGER, Types.VARCHAR};
		return jdbcTemplate.update(insertQuery, params, types);
	}

	@Override
	public int deleteEmployeeById(int empId) {
		String delQuery = "delete from employee where EmpId = ?";
		return jdbcTemplate.update(delQuery, new Object[] {empId});
	}

	@Override
	public Employee getEmployeeById(int empId) {
		String query = "select * from Employee where EmpId = ?";
		//재사용을 위해
		//RowMapper anonymous class를 사용해, 분리된 RowMapper를 생성
		Employee employee = jdbcTemplate.queryForObject(query, new Object[] {empId},
				new RowMapper<Employee>() {

					@Override
					public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
						Employee employee = new Employee(rs.getInt("EmpId"),
								rs.getString("Name"));
						return employee;
					}
				});
		return employee;
	}
	
	public Employee getEmployee(Integer id) {
		System.out.println("1");
		SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
		System.out.println("2");
		Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
		System.out.println("3");
		Employee employee = new Employee(id, (String) simpleJdbcCallResult.get("name"));
		System.out.println("4");
		return employee;
	}

}
