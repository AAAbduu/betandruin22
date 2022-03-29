package uicontrollers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.UserGUI;

import java.io.IOException;

public class UserViewController {


    public AnchorPane mainPane;
    public Button closeBtn;
    public Button brwseQBtn;
    public Label welcomeLbl;

    private UserGUI usergui = new UserGUI();

    public void onClickClose(ActionEvent actionEvent) {
    }

    public void onClickBrwseQ(ActionEvent actionEvent) {
    }

    public void start() throws IOException {
        usergui.start(new Stage());
    }

}
