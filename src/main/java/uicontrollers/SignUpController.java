package uicontrollers;

import businessLogic.BlFacade;
import ui.MainGUI;

public class SignUpController implements Controller {

    private BlFacade businessLogic;
    private MainGUI maingui;

    public SignUpController(BlFacade businessLogic) {
        this.businessLogic = businessLogic;

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.maingui = mainGUI;
    }
}



