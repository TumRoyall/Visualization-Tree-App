package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load file FXML
        Parent root = FXMLLoader.load(getClass().getResource("fxml/menu.fxml"));
        // Thiết lập giao diện chính
        Scene scene1 = new Scene(root);
        // Hiển thị giao diện chính
        stage.setScene(scene1);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
