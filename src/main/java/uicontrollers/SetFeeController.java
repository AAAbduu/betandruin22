package uicontrollers;

import businessLogic.BlFacade;
import domain.Event;
import domain.Fee;
import domain.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SetFeeController implements Controller{
    @FXML
    private Label statusLbl;
    @FXML
    private ComboBox<Question> comboQuestions;

    private ObservableList<Event> oListEvents;

    public SetFeeController(BlFacade bl) {
        this.businessLogic = bl;
    }

    private BlFacade businessLogic;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Event> comboEvents;

    @FXML
    private TextField txtResult;

    @FXML
    private TextField txtFee;


    private MainGUI mainGUI;


    @FXML
    void closeClick(ActionEvent event) {
        mainGUI.showAdminView();
        this.comboQuestions.setItems(null);
        this.txtResult.setText(null);
        this.comboEvents.setItems(null);
        this.datePicker.setValue(null);
        this.txtFee.setText(null);

    }

    private List<LocalDate> holidays = new ArrayList<>();

    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    private void setEvents(int year, int month) {

        Date date = Dates.toDate(year, month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

    @FXML
    void initialize() {


        // only show the text of the event in the combobox (without the id)
        Callback<ListView<Event>, ListCell<Event>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDescription());
            }
        };

        comboEvents.setCellFactory(factory);
        comboEvents.setButtonCell(factory.call(null));


        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());


        // get a reference to datepicker inner content
        // attach a listener to the  << and >> buttons
        // mark events for the (prev, current, next) month and year shown
        datePicker.setOnMouseClicked(e -> {
            DatePickerSkin skin = (DatePickerSkin) datePicker.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {
                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
                    String month = ((Label) (labels.get(0))).getText();
                    String year = ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);
                    setEventsPrePost(ym.getYear(), ym.getMonthValue());
                });
            });


        });

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (holidays.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            }
                        }
                    }
                };
            }
        });

        // when a date is selected...
        datePicker.setOnAction(actionEvent -> {
            comboEvents.getItems().clear();

            oListEvents = FXCollections.observableArrayList(new ArrayList<>());
            oListEvents.setAll(businessLogic.getEvents(Dates.convertToDate(datePicker.getValue())));

            comboEvents.setItems(oListEvents);

        });
        comboEvents.setOnAction(actionEvent -> {
                    Event event = comboEvents.getSelectionModel().getSelectedItem();
                    if (event != null) {
                        ObservableList<Question> observableList = FXCollections.observableList(event.getQuestions());
                        if (observableList != null) {
                            comboQuestions.setItems(observableList);
                        }
                    }
                }
        );

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void onSetFeeBtn(ActionEvent actionEvent) {
        Event event = comboEvents.getSelectionModel().getSelectedItem();
        Question q = comboQuestions.getSelectionModel().getSelectedItem();
        String inputResult = txtResult.getText();
        double inputFee;

        if(inputResult.length() > 0){
            inputFee = Double.valueOf(txtFee.getText());
            if(inputFee>0){

                Fee fee = new Fee(inputResult,inputFee);

                q.addFee(fee);

                businessLogic.updateQuestionFees(q);

                statusLbl.setText("Fee added correctly!");



            }else{
                statusLbl.setText("Fee cannot be negative!");
            }
        }else{
            statusLbl.setText("Result cannot be empty");
        }
    }
}

