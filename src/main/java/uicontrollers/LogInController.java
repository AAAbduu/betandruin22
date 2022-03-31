package uicontrollers;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.MainGUI;

import java.io.IOException;

public class LogInController implements Controller{

    @FXML
    private Label statusLbl;
    @FXML
    private Button closeBtn;
    @FXML
    private PasswordField psswdField;
    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameTxtField;
    @FXML
    private Label usernameLbl;
    @FXML
    private Pane loginPane;
    @FXML
    private Label psswdLbl;


    private BlFacade businessLogic;


    private MainGUI maingui;


    public LogInController(BlFacade businessLogic) {

        this.businessLogic = businessLogic;


    }


    public void onLoginBtn(ActionEvent actionEvent) throws IOException {
        String usr = usernameTxtField.getText();
        String psswd = psswdField.getText();

        if(usr.contentEquals("") || psswd.contentEquals("")){
            statusLbl.setText("None of the fields can be empty!");
        }else if(usr.contentEquals("admin") && psswd.contentEquals("admin")){
            statusLbl.setText("Succesfully logged-in!");

            //maingui.hide();
            maingui.showAdminView();



        }else{

            boolean r = businessLogic.login(usr,psswd);

            if(true){
                statusLbl.setText("Succesfully logged-in!");

                maingui.showUserView();
                    //loginGUI.showUser();
                    //show user view

            }else{
                statusLbl.setText("That combination of credentials is not correct!");
            }
            //call data manager in business logic and check if user exists, if exists then show user view. else show message.
            //wrong combination of credentials.

        }

    }




    public void onCloseBtn(ActionEvent actionEvent) {
        maingui.showMain();
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.maingui=mainGUI;
    }
}
