package inventory.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RootController {

    @FXML
    private BorderPane rootPane;

    @FXML
    public void initialize() {
        showHome();
    }

    @FXML
    private void showHome() {
        loadView("/inventory/view/HomeView.fxml");
    }

    @FXML
    private void showMovements() {
        // ainda não temos MovementsView.fxml — vamos criar depois
        // loadView("/inventory/view/MovementsView.fxml");
    }

    @FXML
    private void showList() {
        // ainda não temos ListView.fxml — vamos criar depois
        // loadView("/inventory/view/ListView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            rootPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}