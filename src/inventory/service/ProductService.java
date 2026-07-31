package inventory.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import inventory.dao.ProductDAO;
import inventory.model.Product;

public class ProductService {
	
	private ProductDAO productDAO = new ProductDAO();
	
	public void addProduct(Product product) throws SQLException {
		if (product.getName()==null || product.getName().isBlank()) {
			throw new IllegalArgumentException("Product name is obrigatory");
		}
		if (product.getMinimumStock()<0) {
			throw new IllegalArgumentException("Minimum stock cannot be negative");
		}
		productDAO.insert(product);
	}
	
	public List<Product> listAll() throws SQLException {
		return productDAO.findAll();
	}
	
	
	public List<Product> listByDepartment(int departmentId) throws SQLException {
		List<Product> all = productDAO.findAll();
		List<Product> result = new ArrayList<>();
		
		for (Product p : all) {
			if(p.getDepartment().getId()==departmentId) {
				result.add(p);
			}
		}
		return result;
	}
	
	public void deactivate(int id) throws SQLException {
		productDAO.updateActive(id, false);
	}
	
	public void activate(int id) throws SQLException {
		productDAO.updateActive(id, true);
	}
}