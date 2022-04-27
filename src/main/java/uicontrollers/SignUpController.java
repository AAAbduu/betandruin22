package uicontrollers;

import businessLogic.BlFacade;
import domain.User;
import exceptions.UserIsUnderageException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import ui.MainGUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SignUpController implements Controller {
    @FXML
    private PasswordField confirmPsswdField;
    @FXML
    private Label statusLbl;
    @FXML
    private TextField usernameTxtField;
    @FXML
    private PasswordField psswdField;
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField emailTxtField;
    @FXML
    private DatePicker dateOfBPicker;
    @FXML
    private CheckBox acceptCondChkB;
    @FXML
    private CheckBox declareAgeChkB;
    @FXML
    private Pane loginPane;




    private BlFacade businessLogic;
    private MainGUI maingui;

    public SignUpController(BlFacade businessLogic) {
        this.businessLogic = businessLogic;

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.maingui = mainGUI;
    }


    public void onBackBtn(ActionEvent actionEvent) {
        this.maingui.showMain();

    }

    public void onSignupBtn(ActionEvent actionEvent) {

        User user;
        if(usernameTxtField.getText().contentEquals("") ||
                psswdField.getText().contentEquals("")  ||
                nameTxtField.getText().contentEquals("")||
                lastnameField.getText().contentEquals("")||
                emailTxtField.getText().contentEquals("")||
                dateOfBPicker.getValue() == null){
            statusLbl.setText("None of the fields can be empty!");
            return;
        }else if(!(acceptCondChkB.isSelected() && declareAgeChkB.isSelected())) {
            statusLbl.setText("You have to check both checkboxes in order to proceed!");
            return;

        }else if(!psswdField.getText().contentEquals(confirmPsswdField.getText())){
            statusLbl.setText("Careful: Passwords are different!");
            return;
        }
        try {
            user = parseUser();
            if(businessLogic.checkIfRegistered(user)){
                statusLbl.setText("User is already registered!");
                return;
            }else{
                businessLogic.register(user);
                statusLbl.setText("Registered succesfully, you may log-in now!");
                return;
            }
        } catch (ParseException e) {
            System.out.println("Parsing exception.");
        } catch (UserIsUnderageException e) {
            statusLbl.setText("You cannot register if you are underage!");
        }
    }


    private User parseUser() throws ParseException, UserIsUnderageException {
        String name = nameTxtField.getText();
        String lastname = lastnameField.getText();
        String email =  emailTxtField.getText();
        String userName = usernameTxtField.getText();
        String password = psswdField.getText();
        LocalDate uDate = dateOfBPicker.getValue();

        Date today = new Date();


        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date date = Date.from(uDate.atStartOfDay(defaultZoneId).toInstant());

        checkAgeBeforeRegister(date, today);

        return new User(userName,password,name,lastname,email,date, false);

    }

    private void checkAgeBeforeRegister(Date uDate, Date today) throws UserIsUnderageException {
        long diff = today.getTime() - uDate.getTime();

        TimeUnit time = TimeUnit.DAYS;

        long difference = time.convert(diff,TimeUnit.MILLISECONDS);

        if(difference < 6570) {
            statusLbl.setText("You must be at least 18 years old!");
            throw new UserIsUnderageException();
        }
    }



}



