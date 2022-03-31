package uicontrollers;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import ui.MainGUI;

public class MainGUIController implements Controller{

    @FXML
    private Label selectOptionLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private MainGUI mainGUI;

    @FXML
    void login(ActionEvent event) {
        mainGUI.showLogin();
    }

    @FXML
    void register(ActionEvent event) {
        mainGUI.showRegister();
    }

    @FXML
    void adminView(ActionEvent event) {
        mainGUI.showAdminView();
    }

    @FXML
    void initialize() {


    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
