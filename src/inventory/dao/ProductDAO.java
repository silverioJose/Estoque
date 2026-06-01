package inventory.dao;

import db.Conn;
import inventory.model.Product;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ProductDAO {
	
	public List<Product> findAll() throws SQLException{
		List<Product> list = new ArrayList<>();
		String sql = "SELECT * FROM product";
		
		try (Connection conn = Conn.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			
			CategoryDAO categoryDAO = new CategoryDAO();
			UnitDAO unitDAO = new UnitDAO();
			
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setCurrentStock(rs.getInt("current_stock"));
				p.setMinimumStock(rs.getInt("minimum_stock"));
				p.setCategory(categoryDAO.findById(rs.getInt("category_id")));
				p.setStockUnit(unitDAO.findById(rs.getInt("stock_unit_id")));
				p.setContentUnit(unitDAO.findById(rs.getInt("content_unit_id")));
				p.setPackageContent(rs.getDouble("package_content"));
				p.setActive(rs.getInt("active") == 1);
				
				list.add(p);
			}
		}
		return list;
		}
		
	public void insert(Product product) throws SQLException{
		String sql = "INSERT INTO product (name, description, minimum_stock, category_id, stock_unit_id, package_content, content_unit_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getDescription());
			pstmt.setInt(3, product.getMinimumStock());
			pstmt.setInt(4, product.getCategory().getId());
			pstmt.setInt(5, product.getStockUnit().getId());
			pstmt.setDouble(6, product.getPackageContent());
			pstmt.setInt(7, product.getContentUnit().getId());
			pstmt.executeUpdate();
			
			System.out.println("Success on Product Insert");
		}
	}
	
	public Product findById(int id) throws SQLException {
		String sql = "SELECT * FROM product WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				CategoryDAO categoryDAO = new CategoryDAO();
				UnitDAO unitDAO = new UnitDAO();
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setCurrentStock(rs.getInt("current_stock"));
				p.setMinimumStock(rs.getInt("minimum_stock"));
				p.setCategory(categoryDAO.findById(rs.getInt("category_id")));
				p.setStockUnit(unitDAO.findById(rs.getInt("stock_unit_id")));
				p.setContentUnit(unitDAO.findById(rs.getInt("content_unit_id")));
				p.setPackageContent(rs.getDouble("package_content"));
				p.setActive(rs.getInt("active") == 1);
				return p;
			}
		}
		return null;
	}
	
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM product WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			}
	}
	
	public void updateStock(Product product) throws SQLException {
		String sql = "UPDATE product SET current_stock = ? WHERE id = ?";
		
		try (Connection conn = Conn.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, product.getCurrentStock());
			pstmt.setInt(2, product.getId());
			pstmt.executeUpdate();
		}
	}
}
