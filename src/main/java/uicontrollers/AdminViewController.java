package uicontrollers;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import ui.MainGUI;

public class AdminViewController implements Controller{



    private BlFacade bl;
    private MainGUI mainGUI;

    public AdminViewController(BlFacade businessLogic) {
        this.bl = businessLogic;
    }


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

    public void onCreateEBtn(ActionEvent actionEvent) {
        mainGUI.showCreateEventView();
    }

    public void onSetFeeBtn(ActionEvent actionEvent) {
        mainGUI.showCreateFee();
    }

    public void onBrowseQBtn(ActionEvent actionEvent) {
        mainGUI.showBrowseQ();
    }

    public void onCreateQBtn(ActionEvent actionEvent) {
        mainGUI.showCreateQ();
    }

    public void onCloseBtn(ActionEvent actionEvent) {
        mainGUI.showMain();
    }

    public void onPublishResultBtn(ActionEvent actionEvent) {
        mainGUI.showPublishResult();
    }
}
