package uicontrollers;

import businessLogic.BlFacade;
import domain.Event;
import exceptions.EventAlreadyExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

public class AddRemoveController implements Controller {

    public TableView<Event> eventTableView;
    public TableColumn idColumn;
    public TableColumn eventColumn;
    public Button removeEventBtn;
    public Pane addEventPane;
    public Button createEventBtn;
    public AnchorPane mainPane;
    @FXML
    private DatePicker datepicker;
    @FXML
    private Label infoLbl;
    @FXML
    private TextField txtEvent;

    private BlFacade businessLogic;
    private MainGUI mainGUI;
    private ObservableList <Event> events;

    private List<LocalDate> holidays = new ArrayList<>();

    public AddRemoveController (BlFacade businessLogic){
        this.businessLogic = businessLogic;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void onCreateEventBtn(ActionEvent actionEvent) {

        String event = txtEvent.getText();

        LocalDate localDate = datepicker.getValue();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());


        try {
            businessLogic.addEvent(new Event(event,date ));
            infoLbl.setText("Event added correctly!");
            this.eventTableView.getItems().clear();

            Vector <Event> events = businessLogic.getEvents(date);

            this.eventTableView.getItems().addAll(events);
        } catch (EventAlreadyExistException e) {
            infoLbl.setText("Event already exists!");
        }

    }

    public void onBackBtn(ActionEvent actionEvent) {
        mainGUI.showAdminView();
    }


    public void onRemoveEventBtn(ActionEvent actionEvent) {

        Event event = this.eventTableView.getSelectionModel().getSelectedItem();

        this.businessLogic.removeEvent(event);

        this.eventTableView.getItems().remove(event);


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
        this.removeEventBtn.setDisable(true);
        this.addEventPane.setVisible(true);


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
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
        this.eventColumn.setCellValueFactory(new PropertyValueFactory<>("description"));




    }





    public void onMouseClickMainPane(MouseEvent mouseEvent) {

        this.removeEventBtn.setDisable(true);


    }

    public void onPickingDate(ActionEvent actionEvent) {

        this.eventTableView.getItems().clear();

        LocalDate date = datepicker.getValue();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date sDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());

        Vector <Event> events = businessLogic.getEvents(sDate);

        this.eventTableView.getItems().addAll(events);

    }

    public void onMouseClickInTable(MouseEvent mouseEvent) {

        this.removeEventBtn.setDisable(false);

    }
}
