package uicontrollers;

import businessLogic.BlFacade;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.MainGUI;

public class UserViewController implements Controller{

    @FXML
    private TextField quantityToAddField;
    @FXML
    private Label welcomeLbl;

    private BlFacade bl;
    private MainGUI mainGUI;


    @FXML
    private Label lblCurrentUser;



    public UserViewController(BlFacade userBl) {
        this.bl = userBl;
    }

    @FXML
    public void initialize(){
        System.out.println("Testing");
        if(this.bl.getUser()!=null)
        welcomeLbl.setText(welcomeLbl.getText()+" "+this.bl.getUser().getName());
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

    public void onAddMoneyBtn(ActionEvent actionEvent) {
    }

    public void onPlaceBetBtn(ActionEvent actionEvent) {
        mainGUI.showBetView();
    }

    public void setUser() {
        lblCurrentUser.setText(bl.getUser().getUserName());
    }
}
