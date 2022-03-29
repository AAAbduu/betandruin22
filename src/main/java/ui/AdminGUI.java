package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminGUI extends Application {

    private Scene scene;
    Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminGUI.class.getResource("/admin-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 625, 375);
        stage.setTitle(ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("MainTitle"));
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
    }


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void show(){
        stage.show();
    }

    public void hide(){
        stage.hide();
    }


}
