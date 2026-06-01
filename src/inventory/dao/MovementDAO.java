package inventory.dao;

import db.Conn;
import inventory.model.Movement;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovementDAO {
	
	public List<Movement> findAll() throws SQLException{
		List<Movement> list = new ArrayList<>();
		String sql = "SELECT * FROM movement";
		
		try (Connection conn = Conn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			
			ProductDAO productDAO = new ProductDAO();
			
			while (rs.next()) {
				Movement m = new Movement();
				m.setId(rs.getInt("id"));
				m.setProduct(productDAO.findById(rs.getInt("product_id")));
				m.setQuantity(rs.getInt("quantity"));
				m.setType(rs.getString("type"));
				String date = rs.getString("date");
				m.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				m.setNotes(rs.getString("notes"));
				list.add(m);
			}
		}
		return list;
		}
	
	public void insert(Movement movement) throws SQLException{
		String sql = "INSERT INTO movement (product_id, quantity, type, notes) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, movement.getProduct().getId());
			pstmt.setInt(2, movement.getQuantity());
			pstmt.setString(3, movement.getType());
			pstmt.setString(4, movement.getNotes());
			pstmt.executeUpdate();
			
			System.out.println("Success on Movement Insert");
		}
	}
	
	public Movement findById(int id) throws SQLException {
		String sql = "SELECT * FROM movement WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ProductDAO productDAO = new ProductDAO();
				Movement m = new Movement();
				m.setId(rs.getInt("id"));
				m.setProduct(productDAO.findById(rs.getInt("product_id")));
				m.setQuantity(rs.getInt("quantity"));
				m.setType(rs.getString("type"));
				String date = rs.getString("date");
				m.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				m.setNotes(rs.getString("notes"));
								
				return m;
			}
		}
		return null;
	}
	
	public List<Movement> findByProduct(int productId) throws SQLException {
		List<Movement> list = new ArrayList<>();
		String sql = "SELECT * FROM movement WHERE product_id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, productId);
			ResultSet rs = pstmt.executeQuery();
			ProductDAO productDAO = new ProductDAO();
			
			while (rs.next()) {
				Movement m = new Movement();
				m.setId(rs.getInt("id"));
				m.setProduct(productDAO.findById(rs.getInt("product_id")));
				m.setQuantity(rs.getInt("quantity"));
				m.setType(rs.getString("type"));
				String date = rs.getString("date");
				m.setDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				m.setNotes(rs.getString("notes"));
				list.add(m);
			}
		}
		return list;
	}
	
}