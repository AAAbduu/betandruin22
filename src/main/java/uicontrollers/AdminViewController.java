package uicontrollers;

import businessLogic.BlFacade;
import ui.MainGUI;

public class AdminViewController implements Controller{



    private BlFacade bl;
    private MainGUI mainGUI;


    public BlFacade getBl() {
        return bl;
    }

    public void setBl(BlFacade bl) {
        this.bl = bl;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
