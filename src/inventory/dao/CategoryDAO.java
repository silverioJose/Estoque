package inventory.dao;

import db.Conn;
import inventory.model.Category;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CategoryDAO {
	
	public List<Category> findAll() throws SQLException{
		List<Category> list = new ArrayList<>();
		String sql = "SELECT * FROM category";
		
		try (Connection conn = Conn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			
			DepartmentDAO departmentDAO = new DepartmentDAO();
			
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDepartment(departmentDAO.findById(rs.getInt("department_id")));
				list.add(c);
			}
		}
		return list;
		}
		
	public void insert(Category category) throws SQLException{
		String sql = "INSERT INTO category (name, department_id) VALUES (?,?)";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, category.getName());
			pstmt.setInt(2, category.getDepartment().getId());
			pstmt.executeUpdate();
			
			System.out.println("Success on Category Insert");
		}
	}
	
	public Category findById(int id) throws SQLException {
		String sql = "SELECT * FROM category WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				DepartmentDAO departmentDAO = new DepartmentDAO();
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDepartment(departmentDAO.findById(rs.getInt("department_id")));
				return c;
			}
		}
		return null;
	}
	
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM category WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			}
	}
}
