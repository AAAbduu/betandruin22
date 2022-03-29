package uicontrollers;

import businessLogic.BlFacade;
import javafx.stage.Stage;
import ui.AdminGUI;

import java.io.IOException;

public class AdminViewController {

    private BlFacade bl;

    private AdminGUI admingui = new AdminGUI();

    public void start() throws IOException {
        admingui.start(new Stage());
    }



}
