package uicontrollers;

import businessLogic.BlFacade;
import domain.Event;
import exceptions.EventAlreadyExistException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.MainGUI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AddEventController implements Controller{
    @FXML
    private DatePicker datepicker;
    @FXML
    private Label infoLbl;
    @FXML
    private TextField txtEvent;

    private BlFacade bl;
    private MainGUI mainGUI;

    public AddEventController (BlFacade businessLogic){
        this.bl = businessLogic;
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
            bl.addEvent(new Event(event,date ));
            infoLbl.setText("Event added correctly!");
        } catch (EventAlreadyExistException e) {
            infoLbl.setText("Event already exists!");
        }

    }

    public void onBackBtn(ActionEvent actionEvent) {
        mainGUI.showAdminView();
    }
}
