package uicontrollers;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.MainGUI;


import java.io.IOException;

public class UserViewController implements Controller{



    private BlFacade bl;
    private MainGUI mainGUI;

    public UserViewController(BlFacade businessLogic) {
        this.bl = businessLogic;
    }

    public void onClickClose(ActionEvent actionEvent) {

         mainGUI.showLogin();

    }

    public void onClickBrwseQ(ActionEvent actionEvent) {

        mainGUI.showBrowseQ();

    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
