package inventory.service;

import java.sql.SQLException;
import java.util.List;

import inventory.dao.DepartmentDAO;
import inventory.model.Department;

public class DepartmentService {
	
	private DepartmentDAO departmentDAO = new DepartmentDAO();
	
	public void addDepartment(Department department) throws SQLException {
		if (department.getName()==null || department.getName().isBlank()) {
			throw new IllegalArgumentException("Department name is obrigatory");
		}
		departmentDAO.insert(department);
	}
	
	public List<Department> listAll() throws SQLException {
		return departmentDAO.findAll();
	}
}