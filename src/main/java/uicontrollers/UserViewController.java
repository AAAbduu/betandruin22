package uicontrollers;

import businessLogic.BlFacade;
import domain.Bet;
import domain.Movement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ui.MainGUI;

import java.util.Date;
import java.util.Optional;

public class UserViewController implements Controller{

    public TableView<Bet> currentBets;
    public TableColumn amounBet;
    public TableColumn eventDescription;
    public Button removeBet;
    public TableView<Movement> movementTable;
    public TableColumn amounMovement;
    public TableColumn movementDescription;
    private ObservableList <Bet> currentBetsTable;

    private ObservableList <Movement> movements;
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

        this.removeBet.setDisable(true);

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

                Movement movement = new Movement(money,"Added money","Money added on date: "+ new Date());

                this.bl.getUser().addMovement(movement);

                this.bl.getUser().setMoney(this.bl.getUser().getMoney() + money);

                this.currentMoney.setText(String.valueOf(this.bl.getUser().getMoney()));

                this.bl.updateUser(this.bl.getUser());

                this.quantityToAddField.setText(null);


                this.setUser();
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

        movements = FXCollections.observableArrayList();
        this.amounMovement.setCellValueFactory(new PropertyValueFactory<>("amount"));
        this.movementDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        this.movements.clear();





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
                                                setTextFill(Color.GREEN);
                                                setTooltip(new Tooltip(super.getTableView().getItems().get(cell.getIndex()).getCompleteDescription()));
                                            } catch (Exception o) {

                                            }
                                        });
                                    } catch (Exception e) {

                                    }

                                }
                            };
                        });
        this.movementDescription.setCellFactory
                (
                        column ->
                        {
                            return new TableCell<Movement, String>() {
                                @Override
                                protected void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setText(item);
                                    try {
                                        this.setOnMouseEntered(e -> {
                                            TableCell<Movement, String> cell = (TableCell<Movement, String>) e.getPickResult().getIntersectedNode();
                                            try {
                                                setTooltip(new Tooltip(super.getTableView().getItems().get(cell.getIndex()).getTooltip()));
                                            } catch (Exception o) {

                                            }
                                        });
                                    } catch (Exception e) {

                                    }

                                }
                            };
                        });



        this.amounMovement.setCellFactory
                (
                        row ->
                        {
                            return new TableCell<Movement, Double>() {
                                @Override
                                protected void updateItem(Double item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item != null) {
                                        Movement movement = super.getTableView().getItems().get(this.getIndex());
                                            if (movement.getDescription().contentEquals("Added money")) {
                                                setTextFill(Color.GREEN);
                                                setText(String.valueOf(item));
                                            } else if (movement.getDescription().contentEquals("Bet placed")) {
                                                setTextFill(Color.RED);
                                                setText(String.valueOf(item));
                                            } else if (movement.getDescription().contentEquals("Removed bet")) {
                                                setTextFill(Color.GREEN);
                                                setText(String.valueOf(item));

                                            }else if (movement.getDescription().contentEquals("Admin deleted the event")) {
                                                setTextFill(Color.GREEN);
                                                setText(String.valueOf(item));

                                            }
                                        }
                                    }
                                //}
                            };
                        });

        this.movements.addAll(this.bl.getUser().getMovements());
        this.movementTable.setItems(this.movements);

        currentBetsTable.addAll(this.bl.getUser().getBets());
        currentBets.setItems(this.currentBetsTable);
        currentMoney.setText(String.valueOf(bl.getUser().getMoney()));
    }

    public void onFocusInQField(MouseEvent mouseEvent) {
        this.removeBet.setDisable(true);
        this.addMoneyBtn.setDisable(false);

    }

    public void onClickInMainPane(MouseEvent mouseEvent) {

        this.removeBet.setDisable(true);
        this.addMoneyBtn.setDisable(true);

    }


    public void onRemoveBetBtn(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Removing bet");
        alert.setContentText("By removing the bet, you will get a penalisation of 20%.\n Are you sure you want to continue?");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional <ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            Bet bet = this.currentBets.getSelectionModel().getSelectedItem();

            Movement movement = new Movement(bet.getAmountBet()*0.8, "Removed bet", bet.getCompleteDescription());

            this.bl.getUser().addMovement(movement);

            this.bl.getUser().removeBet(bet);

            this.currentBets.getItems().remove(bet);

            this.bl.removeBet(bet);
            this.bl.updateUser(this.bl.getUser());

            this.currentMoney.setText(String.valueOf(this.bl.getUser().getMoney()));
            this.setUser();
        }
    }

    public void onMouseClickOnCurrentBets(MouseEvent mouseEvent) {

        try {
            Bet bet = this.currentBets.getSelectionModel().getSelectedItem();

            if(bet!=null) {
                this.removeBet.setDisable(false);
            }
        }catch(Exception e){

        }

    }
}
