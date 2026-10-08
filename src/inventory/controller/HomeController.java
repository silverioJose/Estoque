package inventory.controller;

import inventory.dao.DepartmentDAO;
import inventory.dao.ProductDAO;
import inventory.dao.UnitDAO;
import inventory.model.Department;
import inventory.model.Product;
import inventory.model.Unit;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController {

    @FXML
    private HBox productCardsOrganizer;
    
    @FXML
    private HBox lowStockCardsOrganizer;

    private final ProductDAO productDAO = new ProductDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final UnitDAO unitDAO = new UnitDAO();

    private Department selectedDepartment;
    private Button selectedDepartmentButton;

    // ===== Modal de cadastro rápido =====
    @FXML
    private StackPane modalOverlay;

    @FXML
    private TextField fieldProductName;

    @FXML
    private TextField fieldProductQuantity;

    @FXML
    private TextField fieldMinimumStock;

    @FXML
    private ComboBox<Department> comboDepartment;

    @FXML
    private ComboBox<Unit> comboUnit;

    @FXML
    public void initialize() {
        loadProducts();
        loadLowStockProducts();
    }

    private void loadProducts() {
    	Integer departmentId = (selectedDepartment != null) ? selectedDepartment.getId() : null;
    	
    	try {
    		List<Product> products = productDAO.findActive(departmentId);
    		String emptyMessage = "Nenhum item ativo em " + deptNameOrDefault();
    		populateCardsOrganizer(productCardsOrganizer, productsScrollPane, products, emptyMessage);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    private void loadLowStockProducts() {
        Integer departmentId = (selectedDepartment != null) ? selectedDepartment.getId() : null;
        try {
            List<Product> products = productDAO.findLowStock(departmentId);
            String emptyMessage = "Nenhum item em baixo estoque em " + deptNameOrDefault();
            populateCardsOrganizer(lowStockCardsOrganizer, lowStockScrollPane, products, emptyMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Returns the selected department's name, or a default label when no filter is active
    private String deptNameOrDefault() {
        return (selectedDepartment != null) ? selectedDepartment.getName() : "Nenhum departamento";
    }
    
	 // Clears the given organizer and fills it with product cards,
	 // or shows an empty-state label when the list has no items
	 private void populateCardsOrganizer(HBox organizer, ScrollPane parentScrollPane, List<Product> products, String emptyMessage) {
	     organizer.getChildren().clear();
	
	     if (products.isEmpty()) {
	         Label emptyLabel = new Label(emptyMessage);
	         organizer.getChildren().add(emptyLabel);
	     } else {
	         for (Product p : products) {
	             VBox card = createProductCard(p, parentScrollPane);
	             organizer.getChildren().add(card);
	         }
	     }
	 }

    // Number of cards meant to be visible at once inside the carousel viewport;
    // card width is derived from this so cards scale with the window instead of staying fixed
    private static final int VISIBLE_CARDS = 4;
    private static final double CARD_SPACING = 12;
    private static final double MIN_CARD_WIDTH = 120;
    private static final double CARD_HEIGHT_RATIO = 1.2; // card height = width * this ratio

    private VBox createProductCard(Product product, ScrollPane parentScrollPane) {
        VBox card = new VBox();
        card.getStyleClass().add("product__card");

        // card width = (scrollpane viewport width - space taken by gaps) / cards visible at once,
        // recalculated automatically whenever the scrollpane is resized
        card.prefWidthProperty().bind(
            parentScrollPane.widthProperty()
                .subtract(CARD_SPACING * (VISIBLE_CARDS - 1))
                .divide(VISIBLE_CARDS)
        );
        card.setMinWidth(MIN_CARD_WIDTH);

        // keeps card height proportional to its (now dynamic) width, so both axes scale together
        card.prefHeightProperty().bind(card.prefWidthProperty().multiply(CARD_HEIGHT_RATIO));

        Label nameLabel = new Label(product.getName());
        
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        
        Label quantityLabel = new Label(String.valueOf(product.getCurrentStock()));
        Label unityLabel = new Label(String.valueOf(product.getStockUnit()));

        HBox infoBox = new HBox(nameLabel, region, quantityLabel, unityLabel);
        infoBox.setSpacing(5);
        //infoBox.getStyleClass().add("");
        card.getChildren().add(infoBox);

        return card;
    }

    @FXML
    private void handleDepartmentClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String departmentName = clickedButton.getText();
        
        if (selectedDepartmentButton != null) {
            selectedDepartmentButton.getStyleClass().remove("department__button-active");
        }
	        selectedDepartmentButton = clickedButton;
	        selectedDepartmentButton.getStyleClass().add("department__button-active");

        try {
            List<Department> departments = departmentDAO.findAll();

            for (Department d : departments) {
                if (d.getName().equals(departmentName)) {
                    selectedDepartment = d;
                    break;
                }
            }
            loadProducts();
            loadLowStockProducts();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private ScrollPane productsScrollPane;
    
    @FXML
    private ScrollPane lowStockScrollPane;
    
    private final Map<ScrollPane, Timeline> scrollTimelines = new HashMap<>();
    
    private static final Duration SCROLL_DURATION = Duration.millis(300); //carousel animation duration
    
    @FXML
    private void handleProductsScrollLeft(ActionEvent event) {
        scrollBy(productsScrollPane, productCardsOrganizer, -1);
    }

    @FXML
    private void handleProductsScrollRight(ActionEvent event) {
        scrollBy(productsScrollPane, productCardsOrganizer, 1);
    }

    @FXML
    private void handleLowStockScrollLeft(ActionEvent event) {
        scrollBy(lowStockScrollPane, lowStockCardsOrganizer, -1);
    }

    @FXML
    private void handleLowStockScrollRight(ActionEvent event) {
        scrollBy(lowStockScrollPane, lowStockCardsOrganizer, 1);
    }
    
    // Card width used to compute scroll distance per click.
    // Kept in sync with the dynamic card width via currentCardWidth().
    private double currentCardWidth(HBox organizer) {
        if (!organizer.getChildren().isEmpty() && organizer.getChildren().get(0) instanceof VBox) {
            VBox firstCard = (VBox) organizer.getChildren().get(0);
            return firstCard.getWidth() + CARD_SPACING;
        }
        return MIN_CARD_WIDTH + CARD_SPACING;
    }
    
    private void scrollBy(ScrollPane scrollPane, HBox organizer, int direction) {
    	double contentWidth = organizer.getWidth();
    	double viewportWidth = scrollPane.getViewportBounds().getWidth();
    	double scrollableWidth = contentWidth - viewportWidth;
    	
    	// avoids division by zero when content fits entirely within the viewport
    	if(scrollableWidth  <= 0) return;
    	
    	double fraction = currentCardWidth(organizer) / scrollableWidth;
    	double newValue = Math.max(0,  Math.min(1, scrollPane.getHvalue() + (fraction * direction)));
    	animateScrollTo(scrollPane, newValue);
    }
    
    private void animateScrollTo(ScrollPane scrollPane, double targetValue) {
    	// stop any animation in progress before starting a new one, 
    	// preventing overlapping timelines from rapid repeated clicks
    	Timeline existing = scrollTimelines.get(scrollPane);
    	if (existing != null) {
    		existing.stop();
    	}
    	KeyValue keyValue = new KeyValue(scrollPane.hvalueProperty(), targetValue);
    	KeyFrame keyFrame = new KeyFrame(SCROLL_DURATION, keyValue);
        Timeline timeline = new Timeline(keyFrame);
        scrollTimelines.put(scrollPane, timeline);
        timeline.play();
    }

    // ===================== MODAL: CADASTRO RÁPIDO =====================

    @FXML
    private void handleShowAddModal(ActionEvent event) {
        try {
            comboDepartment.getItems().setAll(departmentDAO.findAll());
            comboUnit.getItems().setAll(unitDAO.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        fieldProductName.clear();
        fieldProductQuantity.clear();
        fieldMinimumStock.clear();
        comboDepartment.getSelectionModel().clearSelection();
        comboUnit.getSelectionModel().clearSelection();

        modalOverlay.setVisible(true);
        modalOverlay.setManaged(true);
    }

    @FXML
    private void handleCancelAdd(ActionEvent event) {
        closeModal();
    }

    @FXML
    private void handleSaveProduct(ActionEvent event) {
        String name = fieldProductName.getText();
        String quantityText = fieldProductQuantity.getText();
        String minimumStockText = fieldMinimumStock.getText();
        Department department = comboDepartment.getSelectionModel().getSelectedItem();
        Unit unit = comboUnit.getSelectionModel().getSelectedItem();

        if (name == null || name.isBlank() || department == null || unit == null) {
        	System.out.println("Campos não preenchidos");
            
            return;
        }

        int quantity;
        int minimumStock;
        try {
            quantity = Integer.parseInt(quantityText.trim());
            minimumStock = Integer.parseInt(minimumStockText.trim());
        } catch (NumberFormatException e) {
            
            return;
        }

        Product product = new Product();
        product.setName(name);
        product.setDescription("");
        product.setMinimumStock(minimumStock);
        product.setDepartment(department);
        product.setStockUnit(unit);
        product.setContentUnit(unit);
        product.setPackageContent(1);

        try {
            productDAO.insert(product);

            // insert() não define o estoque inicial; buscamos o produto recém-criado
            // pelo nome para então gravar a quantidade informada no modal
            List<Product> matches = productDAO.findActive(department.getId());
            for (Product p : matches) {
                if (p.getName().equals(name)) {
                    p.setCurrentStock(quantity);
                    productDAO.updateStock(p);
                    break;
                }
            }

            closeModal();
            loadProducts();
            loadLowStockProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeModal() {
        modalOverlay.setVisible(false);
        modalOverlay.setManaged(false);
    }
}