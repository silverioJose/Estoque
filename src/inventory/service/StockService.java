package inventory.service;

import inventory.dao.ProductDAO;
import inventory.dao.MovementDAO;
import inventory.model.Movement;
import inventory.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockService {
	
	private ProductDAO productDAO = new ProductDAO();
	private MovementDAO movementDAO = new MovementDAO();
	
	public void registrerEntry(Product product, int quantity, String notes) throws SQLException {
		Movement m = new Movement();
		m.setProduct(product);
		m.setQuantity(quantity);
		m.setType("ENTRY");
		m.setNotes(notes);
		movementDAO.insert(m);
		product.setCurrentStock(product.getCurrentStock()+quantity);
		productDAO.updateStock(product);
	}
	
	public void registrerExit(Product product, int quantity, String notes) throws SQLException {
		if(quantity>product.getCurrentStock()) {
			throw new IllegalArgumentException("Quantidade insuficiente");
		}
		Movement m = new Movement();
		m.setProduct(product);
		m.setQuantity(quantity);
		m.setType("EXIT");
		m.setNotes(notes);
		movementDAO.insert(m);
		product.setCurrentStock(product.getCurrentStock()-quantity);
		productDAO.updateStock(product);
	}
	
	public List<Product> getLowStock() throws SQLException {
		List<Product> all = productDAO.findAll();
		List<Product> result = new ArrayList<>();
		
		for (Product p : all) {
			if(p.getCurrentStock()<p.getMinimumStock()) {
				result.add(p);
			}
		}
		return result;
	}
	
	public List<Product> getNearMinimum() throws SQLException {
		List<Product> all = productDAO.findAll();
		List<Product> result = new ArrayList<>();
		
		for (Product p : all) {
			if(p.getCurrentStock()>=p.getMinimumStock() && p.getCurrentStock()<p.getMinimumStock()*1.2) {
				result.add(p);
			}
		}
		return result;
	}
}