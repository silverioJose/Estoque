package inventory.dao;

import db.Conn;
import inventory.model.Unit;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UnitDAO {
	
	public List<Unit> findAll() throws SQLException{
		List<Unit> list = new ArrayList<>();
		String sql = "SELECT * FROM unit";
		
		try (Connection conn = Conn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				Unit u = new Unit();
				u.setId(rs.getInt("id"));
				u.setAbbreviation(rs.getString("abbreviation"));
				u.setDescription(rs.getString("description"));
				list.add(u);
			}
		}
		return list;
		}
		
	public void insert(Unit unit) throws SQLException{
		String sql = "INSERT INTO unit (abbreviation, description) VALUES (?,?)";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, unit.getAbbreviation());
			pstmt.setString(2, unit.getDescription());
			pstmt.executeUpdate();
			
			System.out.println("Success on Unit Insert");
		}
	}
	
	public Unit findById(int id) throws SQLException {
		String sql = "SELECT * FROM unit WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Unit u = new Unit();
				u.setId(rs.getInt("id"));
				u.setAbbreviation(rs.getString("abbreviation"));
				u.setDescription(rs.getString("description"));
				return u;
			}
		}
		return null;
	}
	
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM unit WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			}
	}
}
