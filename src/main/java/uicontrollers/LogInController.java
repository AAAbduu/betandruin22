package uicontrollers;

import businessLogic.BlFacade;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.MainGUI;

import java.io.IOException;

public class LogInController implements Controller{

    @FXML
    private Label statusLbl;
    @FXML
    private PasswordField psswdField;
    @FXML
    private TextField usernameTxtField;



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

        }else{

            User r = businessLogic.login(usr,psswd);

            if(r!=null){
                statusLbl.setText("Succesfully logged-in!");

                this.businessLogic.setUser(r);

                if(r.isAdmin()){
                    maingui.showAdminView();
                }else {

                    maingui.showUserView();
                }



            }else{
                statusLbl.setText("That combination of credentials is not correct!");
            }

            statusLbl.setText(null);

            usernameTxtField.setText(null);

            psswdField.setText(null);

        }

    }




    public void onCloseBtn(ActionEvent actionEvent) {
        maingui.showMain();
        this.businessLogic.setUser(null);
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.maingui=mainGUI;
    }
}
