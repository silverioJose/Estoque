package inventory.controller;

import inventory.dao.DepartmentDAO;
import inventory.dao.ProductDAO;
import inventory.model.Department;
import inventory.model.Product;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.List;

public class HomeController {

    @FXML
    private HBox productCardsOrganizer;

    private final ProductDAO productDAO = new ProductDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();

    private Department selectedDepartment;
    private Timeline scrollTimeline;
    
    @FXML
    public void initialize() {
        loadProducts();
    }

    private void loadProducts() {
    	productCardsOrganizer.getChildren().clear();
    	
    	try {
    		Integer departmentId = (selectedDepartment != null) ? selectedDepartment.getId() : null;
    		List<Product> products = productDAO.findActive(departmentId);
    		
    		if (products.isEmpty()) {
    			String deptName = (selectedDepartment != null) ? selectedDepartment.getName() : "Nenhum departamento";
    			Label emptyLabel = new Label("Nenhum item ativo em " + deptName);
    			productCardsOrganizer.getChildren().add(emptyLabel);
    		} else {
    			for (Product p : products) {
    				VBox card = createProductCard(p);
                    productCardsOrganizer.getChildren().add(card);
    			}
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox();
        card.getStyleClass().add("product__card");
        card.setPrefWidth(180);
        card.setMinWidth(180);
        card.setMaxWidth(180);

        Label nameLabel = new Label(product.getName());
        Label quantityLabel = new Label(String.valueOf(product.getCurrentStock()));

        HBox infoBox = new HBox(nameLabel, quantityLabel);
        card.getChildren().add(infoBox);

        return card;
    }

    @FXML
    private void handleDepartmentClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String departmentName = clickedButton.getText();

        try {
            List<Department> departments = departmentDAO.findAll();

            for (Department d : departments) {
                if (d.getName().equals(departmentName)) {
                    selectedDepartment = d;
                    break;
                }
            }
            loadProducts();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private ScrollPane productsScrollPane;
    
    private static final double SCROLL_AMOUNT = 0.15; //fraction of the scroll per click
    private static final Duration SCROLL_DURATION = Duration.millis(300); //carousel animation duration
    
    @FXML
    private void handleScrollLeft(ActionEvent event) {
        double newValue = Math.max(0, productsScrollPane.getHvalue() - SCROLL_AMOUNT);
        animateScrollTo(newValue);
    }

    @FXML
    private void handleScrollRight(ActionEvent event) {
        double newValue = Math.min(1, productsScrollPane.getHvalue() + SCROLL_AMOUNT);
        animateScrollTo(newValue);
    }
    
    @FXML
    private void animateScrollTo(double targetValue) {
    	// stop any animation in progress before starting a new one, 
    	// preventing overlapping timelines from rapid repeated clicks
    	if (scrollTimeline != null) {
    		scrollTimeline.stop();
    	}
    	KeyValue keyValue = new KeyValue(productsScrollPane.hvalueProperty(), targetValue);
    	KeyFrame keyFrame = new KeyFrame(SCROLL_DURATION, keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }
}