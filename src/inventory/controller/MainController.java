package inventory.controller;

import java.sql.SQLException;
import java.util.List;

import inventory.dao.DepartmentDAO;
import inventory.dao.ProductDAO;
import inventory.model.Department;
import inventory.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {
	
	@FXML
	private HBox productCardsOrganizer;
	
	private final ProductDAO productDAO = new ProductDAO();
	private final DepartmentDAO departmentDAO = new DepartmentDAO();
	
	private Department selectedDepartment;
	
	@FXML
	public void initialize() {
		loadProducts();
	}
	
	private void loadProducts() {
		productCardsOrganizer.getChildren().clear();
		
		try {
			List<Product> products = productDAO.findAll();
			
			for (Product p : products) {
				VBox card = createProductCard(p);
				productCardsOrganizer.getChildren().add(card);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private VBox createProductCard(Product product) {
		VBox card = new VBox();
		card.getStyleClass().add("product__card");
		
		Label nameLabel = new Label(product.getName());
		Label quantityLabel = new Label(String.valueOf(product.getCurrentStock()));
		
		HBox infoBox = new HBox(nameLabel, quantityLabel);
		card.getChildren().add(infoBox);
		
		return card;
	}
}
