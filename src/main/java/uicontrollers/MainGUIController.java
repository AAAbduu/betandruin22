package uicontrollers;

import java.net.URL;
import java.util.*;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import scheduler.Scheduler;
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
    void initialize() {
        Scheduler schedulerLiga = new Scheduler(new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer is working");
            }
        });

        schedulerLiga.getTimer().schedule(schedulerLiga.getTask(), new Date(),6000); //max can make 10 requests per minute to the rest api, so i have 60000 ms in a minute 60000/10 = 6000 ms.

        Scheduler schedulerChampions = new Scheduler(new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer for champions is working");
            }
        });

        schedulerChampions.getTimer().schedule(schedulerChampions.getTask(), new Date(),6000); //max can make 10 requests per minute to the rest api, so i have 60000 ms in a minute 60000/10 = 6000 ms.


    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
