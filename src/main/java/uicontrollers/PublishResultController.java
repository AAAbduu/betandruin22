package uicontrollers;

import businessLogic.BlFacade;
import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class PublishResultController implements Controller{
    public DatePicker datepicker;
    public TableView<Event> eventTableView;
    public TableColumn eventIdColumn;
    public TableColumn eventColumn;
    public TableView<Question> questionTableView;
    public TableColumn questionIdColumn;
    public TableColumn questionColumn;
    public TableView<Fee> feeTableView;
    public TableColumn feeIdColumn;
    public TableColumn descriptionFeeColumn;

    public Label statusLbl;
    public Button backBtn;
    public Button publishBtn;
    public TextField resultField;


    private BlFacade businessLogic;
    private MainGUI mainGUI;
    private ObservableList<Event> events;
    private ObservableList<Question> questions;
    private ObservableList<Fee> fees;

    private List<LocalDate> holidays = new ArrayList<>();

    public PublishResultController(BlFacade businessLogic) {
        this.businessLogic = businessLogic;
    }


    public void onBackBtn(ActionEvent actionEvent) {

        mainGUI.showAdminView();

    }



    private void setEvents(int year, int month) {
        Date date = Dates.toDate(year,month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }
    @FXML
    void initialize() {

        events = FXCollections.observableArrayList();
        questions = FXCollections.observableArrayList();
        fees = FXCollections.observableArrayList();
        this.publishBtn.setDisable(true);
        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());


        datepicker.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
            DatePickerSkin skin = (DatePickerSkin) datepicker.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {
                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
                    String month = ((Label) (labels.get(0))).getText();
                    String year =  ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);
                    setEventsPrePost(ym.getYear(), ym.getMonthValue());
                });
            });


        });

        datepicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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

        // Bind columns to Event attributes
        this.eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
        this.eventColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        this.questionIdColumn.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        this.questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));

        this.feeIdColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
        this.descriptionFeeColumn.setCellValueFactory(new PropertyValueFactory<>("result"));


    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void onDatePicker(ActionEvent actionEvent) {

        this.eventTableView.getItems().clear();
        this.feeTableView.getItems().clear();
        this.questionTableView.getItems().clear();
        this.publishBtn.setDisable(true);

        LocalDate date = datepicker.getValue();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date sDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());

        Vector<Event> events = businessLogic.getEvents(sDate);

        this.eventTableView.getItems().addAll(events);


    }

    public void selectEventClick(MouseEvent mouseEvent) {
        try {
            this.questionTableView.getItems().clear();
            this.publishBtn.setDisable(true);
            this.feeTableView.getItems().clear();
            Event event = this.eventTableView.getSelectionModel().getSelectedItem();
            Vector<Question> q = event.getQuestions();

            this.questionTableView.getItems().addAll(q);
        }catch(Exception e){

        }


    }

    public void selectQuestionClick(MouseEvent mouseEvent) {
        try{
            this.feeTableView.getItems().clear();
            this.publishBtn.setDisable(true);
            Question q = this.questionTableView.getSelectionModel().getSelectedItem();
            Vector<Fee> fees = q.getFees();

            this.feeTableView.getItems().addAll(fees);
        }catch(Exception e){

        }


    }

    public void onFeeSelected(MouseEvent mouseEvent) {

        Fee fee = this.feeTableView.getSelectionModel().getSelectedItem();
        if(fee!=null) {

            this.resultField.setDisable(false);

        }
    }


    public void onPublishBtn(ActionEvent actionEvent) {

        Event event = eventTableView.getSelectionModel().getSelectedItem();

        Question question = questionTableView.getSelectionModel().getSelectedItem();

        String res = this.resultField.getText();

        Result result = new Result(event,question,res);

        this.businessLogic.publishResult(result);

        statusLbl.setText("Result published!");




    }

    public void onWritingResult(KeyEvent keyEvent) {

        this.publishBtn.setDisable(false);

    }

    public void onStopWritingResult(KeyEvent keyEvent) {

        if(this.resultField.getLength()==0){
            this.publishBtn.setDisable(true);
        }

    }
}
