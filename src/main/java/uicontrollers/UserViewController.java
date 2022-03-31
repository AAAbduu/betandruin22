package uicontrollers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.MainGUI;
import ui.UserGUI;

import java.io.IOException;

public class UserViewController implements Controller{


    public AnchorPane mainPane;
    public Button closeBtn;
    public Button brwseQBtn;
    public Label welcomeLbl;

    private MainGUI mainGUI;

    public void onClickClose(ActionEvent actionEvent) {
    }

    public void onClickBrwseQ(ActionEvent actionEvent) {
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
