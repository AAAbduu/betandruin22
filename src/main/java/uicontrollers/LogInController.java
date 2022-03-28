package uicontrollers;

import businessLogic.BlFacade;
import ui.MainGUI;

public class LogInController implements Controller{

    private BlFacade businessLogic;

    private MainGUI maingui;


    public LogInController(BlFacade businessLogic) {

        this.businessLogic = businessLogic;

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.maingui = mainGUI;
    }
}
