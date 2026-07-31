package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("/inventory/view/RootView.fxml"));
        Scene scene = new Scene(root);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf"), 12);
        
        String css = this.getClass().getResource("/inventory/view/MainView.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Estoque");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}