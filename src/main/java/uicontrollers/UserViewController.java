package uicontrollers;

import businessLogic.BlFacade;
import domain.Bet;
import domain.User;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ui.MainGUI;

import java.util.ArrayList;

public class UserViewController implements Controller{

    public TableView<Bet> currentBets;
    public TableColumn amounBet;
    public TableColumn eventDescription;
    private ObservableList <Bet> currentBetsTable;
    @FXML
    private Button addMoneyBtn;
    @FXML
    private Button placeBetBtn;
    @FXML
    private Label currentMoney;
    @FXML
    private Label statusLbl;
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


        this.addMoneyBtn.setDisable(true);

    }

    public void onClickClose(ActionEvent actionEvent) {
        this.bl.setUser(null);
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

        String moneyQ = this.quantityToAddField.getText();

        try {
            double money = Double.valueOf(moneyQ);
            if(money>0) {

                this.bl.getUser().setMoney(this.bl.getUser().getMoney() + money);

                this.currentMoney.setText(String.valueOf(this.bl.getUser().getMoney()));

                this.bl.updateUser(this.bl.getUser());

                this.quantityToAddField.setText(null);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void onPlaceBetBtn(ActionEvent actionEvent) {
        mainGUI.showBetView();
    }

    public void setUser() {
        lblCurrentUser.setText(bl.getUser().getUserName() + "!");

        currentBetsTable = FXCollections.observableArrayList();

        this.amounBet.setCellValueFactory(new PropertyValueFactory<>("calculatedAmount"));
        this.eventDescription.setCellValueFactory(new PropertyValueFactory<>("description"));


        currentBetsTable.clear();

        this.eventDescription.setCellFactory
                (
                        column ->
                        {
                            return new TableCell<Bet, String>() {
                                @Override
                                protected void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setText(item);
                                    try {
                                        this.setOnMouseEntered(e -> {
                                            TableCell<Bet, String> cell = (TableCell<Bet, String>) e.getPickResult().getIntersectedNode();
                                            try {
                                                setTooltip(new Tooltip("Event: " + super.getTableView().getItems().get(cell.getIndex()).getDescription()
                                                        + "\nQuestion: " + super.getTableView().getItems().get(cell.getIndex()).getQuestion().getQuestion()
                                                        + "\nPrediction: " + super.getTableView().getItems().get(cell.getIndex()).getFee().getResult()
                                                        + "\nFee: " + super.getTableView().getItems().get(cell.getIndex()).getFee().getFee()
                                                        + "\nBet:" + super.getTableView().getItems().get(cell.getIndex()).getAmountBet()));
                                            } catch (Exception o) {

                                            }
                                        });
                                    } catch (Exception e) {

                                    }

                                }
                            };
                        });

        /*this.bl.getUser().getBets().forEach(b -> {
            if(b.getAmountBet()!=0)
            currentBetsTable.add(b);
        });*/
        currentBetsTable.addAll(this.bl.getUser().getBets());
        currentBets.setItems(this.currentBetsTable);
        currentMoney.setText(String.valueOf(bl.getUser().getMoney()));
    }

    public void onFocusInQField(MouseEvent mouseEvent) {

        this.addMoneyBtn.setDisable(false);

    }

    public void onClickInMainPane(MouseEvent mouseEvent) {

        this.addMoneyBtn.setDisable(true);

    }


}
