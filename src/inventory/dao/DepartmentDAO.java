package inventory.dao;

import db.Conn;
import inventory.model.Department;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DepartmentDAO {
	
	public List<Department> findAll() throws SQLException{
		List<Department> list = new ArrayList<>();
		String sql = "SELECT * FROM department";
		
		try (Connection conn = Conn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt("id"));
				d.setName(rs.getString("name"));
				list.add(d);
			}
		}
		return list;
		}
		
	public void insert(Department department) throws SQLException{
		String sql = "INSERT INTO department (name) VALUES (?)";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, department.getName());
			pstmt.executeUpdate();
			
			System.out.println("Success on Departmente Insert");
			
		}
	}
	
	public Department findById(int id) throws SQLException {
		String sql = "SELECT * FROM department WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt("id"));
				d.setName(rs.getString("name"));
				return d;
			}
		}
		return null;
	}
	
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM department WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			}
	}
}
