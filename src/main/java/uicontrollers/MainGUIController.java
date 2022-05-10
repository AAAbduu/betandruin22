package uicontrollers;

import java.lang.reflect.Type;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import businessLogic.BlFacade;
import businessLogic.BlFacadeImplementation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import domain.Competition;
import domain.Event;
import domain.Question;
import domain.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import scheduler.Scheduler;
import ui.MainGUI;

public class MainGUIController implements Controller{
    @FXML
    private Label selectOptionLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    public MainGUIController(BlFacade businessLogic) {
        this.businessLogic = businessLogic;
    }

    @FXML
    void login(ActionEvent event) {
        mainGUI.showLogin();
    }

    @FXML
    void register(ActionEvent event) {
        mainGUI.showRegister();
    }


    @FXML
    void initialize() {

        try {
            Scheduler schedulerLiga = new Scheduler(new Timer(), new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Timer for Laliga is working and refreshing every minute");
                    Date date = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    String year = String.valueOf(calendar.get(Calendar.YEAR));
                    String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                    int monthInt = calendar.get(Calendar.MONTH) + 1;
                    if (monthInt == 12) monthInt = 1;
                    String month = String.valueOf(monthInt);
                    String today;
                    if (monthInt < 10) {
                        today = year + "-" + "0" + month + "-" + day;
                    } else {
                        today = year + "-" + month + "-" + day;
                    }if(Integer.valueOf(day) < 10){
                        today = year + "-" + "0" + month + "-" + "0"+day;
                    }

                    String responseLiga = ((BlFacadeImplementation) businessLogic).getManager().makeRequest("/v2/competitions/2014/matches?dateFrom=" + today + "&dateTo=" + today);


                    List <Competition.Match> laLigamatches = obtainResponseCompetition(responseLiga);

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY,0);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);
                    cal.set(Calendar.MILLISECOND,0);

                    Date d = cal.getTime();

                    Vector <Event> events = businessLogic.getEvents(d, true);
                    Event ev = null;
                    for (Competition.Match m : laLigamatches) {
                        for (Event e : events) {
                            if (e.getDescription().contentEquals(m.homeTeam.name + "-" + m.awayTeam.name)) {
                                ev = e;
                                break;
                            }
                        }
                        if (ev != null) {
                            if (ev.getStatus().get("isFinished") == false && m.status.contentEquals("FINISHED")) {
                                publishWinningResult(today, m);
                                publishHowManyGoalsResult(today, m);
                                ev.getStatus().replace("isFinished", true);
                                businessLogic.updateEvent(ev);
                            } else if (ev.getStatus().get("isFirstTimeFinished") == false && m.score.halfTime.get("awayTeam") !=null && m.score.halfTime.get("homeTeam") !=null){
                                publishFirstTimeGoalsResult(today, m);
                                ev.getStatus().replace("isFirstTimeFinished", true);
                                businessLogic.updateEvent(ev);
                            }
                        }
                    }
                }
            });
            schedulerLiga.getTimer().schedule(schedulerLiga.getTask(), new Date(),60500);
        }catch (Exception e){

        }

        try {
            Scheduler schedulerChampions = new Scheduler(new Timer(), new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Timer for champions is working and refreshing every minute");
                    Date date = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    String year = String.valueOf(calendar.get(Calendar.YEAR));
                    String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                    int monthInt = calendar.get(Calendar.MONTH) + 1;
                    if (monthInt == 12) monthInt = 1;
                    String month = String.valueOf(monthInt);
                    String today;
                    if (monthInt < 10) {
                        today = year + "-" + "0" + month + "-" + day;
                    } else {
                        today = year + "-" + month + "-" + day;
                    }if(Integer.valueOf(day) < 10){
                        today = year + "-" + "0" + month + "-" + "0"+day;
                    }
                    String responseChampions = ((BlFacadeImplementation) businessLogic).getManager().makeRequest("/v2/competitions/2001/matches?dateFrom=" + today + "&dateTo=" + today);


                    List<Competition.Match> championsMatches = obtainResponseCompetition(responseChampions);

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY,0);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);
                    cal.set(Calendar.MILLISECOND,0);

                    Date d = cal.getTime();

                    Vector <Event> events = businessLogic.getEvents(d, true);
                    Event ev = null;
                    for (Competition.Match m : championsMatches) {
                        for (Event e : events) {
                            if (e.getDescription().contentEquals(m.homeTeam.name + "-" + m.awayTeam.name)) {
                                ev = e;
                                break;
                            }
                        }
                        if (ev != null) {
                            if (ev.getStatus().get("isFinished") == false && m.status.contentEquals("FINISHED")) {
                                publishWinningResult(today, m);
                                publishHowManyGoalsResult(today, m);
                                ev.getStatus().replace("isFinished", true);
                                businessLogic.updateEvent(ev);
                            } else if (ev.getStatus().get("isFirstTimeFinished") == false && m.score.halfTime.get("awayTeam") !=null && m.score.halfTime.get("homeTeam") !=null){
                                publishFirstTimeGoalsResult(today, m);
                                ev.getStatus().replace("isFirstTimeFinished", true);
                                businessLogic.updateEvent(ev);
                            }
                        }
                    }
                }
            });

            schedulerChampions.getTimer().schedule(schedulerChampions.getTask(), new Date(), 60000);

        }catch (Exception o){

        }
    }
    private void publishHowManyGoalsResult(String today, Competition.Match m) {
        int goals = 0;
        goals = m.score.fullTime.get("homeTeam") + m.score.fullTime.get("awayTeam");
        String description = m.homeTeam.name + "-" + m.awayTeam.name;
        String q;
        if (Locale.getDefault().equals(new Locale("es"))) {
            q = "¿Cuántos goles se marcarán?";
        } else if (Locale.getDefault().equals(new Locale("en"))) {
            q = "How many goals will be scored in the match?";
        } else {
            q = "Zenbat gol sartuko dira?";

        }
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(today);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Question question = businessLogic.getSpecificQuestion(q, date1, description);

        if (question != null) {
            businessLogic.publishResult(new Result(question, String.valueOf(goals)));
        }
    }
    private void publishWinningResult(String today, Competition.Match m) {
        String winner = new String();
        if (m.score.winner.contentEquals("AWAY_TEAM")) {
            winner = m.awayTeam.name;
        } else if (m.score.winner.contentEquals("HOME_TEAM")) {
            winner = m.homeTeam.name;
        } else if (m.score.winner.contentEquals("DRAW")) {
            winner = "Draw";
        }
        String description = m.homeTeam.name + "-" + m.awayTeam.name;
        String q;
        if (Locale.getDefault().equals(new Locale("es"))) {
            q = "¿Quién ganará el partido?";
        } else if (Locale.getDefault().equals(new Locale("en"))) {
            q = "Who will win the match?";
        } else {
            q = "Zeinek irabaziko du partidua?";
        }
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(today);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Question question = businessLogic.getSpecificQuestion(q, date1, description);

        if (question != null) {
            businessLogic.publishResult(new Result(question, winner));
        }
    }
    private void publishFirstTimeGoalsResult(String today, Competition.Match m) {
        String result = "No";
       if(m.score.halfTime.get("awayTeam")+ m.score.halfTime.get("homeTeam") >0){
           result = "Yes";
       }
        String description = m.homeTeam.name + "-" + m.awayTeam.name;
        String q;
        if (Locale.getDefault().equals(new Locale("es"))) {
            q =  "¿Habrá goles en la primera parte?";
        } else if (Locale.getDefault().equals(new Locale("en"))) {
            q = "Will there be goals in the first half?";
        } else {
            q = "Golak sartuko dira lehenengo zatian?";
        }
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(today);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Question question = businessLogic.getSpecificQuestion(q, date1, description);

        if (question != null) {
            businessLogic.publishResult(new Result(question, result));
        }
    }
    private List<Competition.Match> obtainResponseCompetition(String responseChampions) {
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(responseChampions, JsonObject.class);
        Type matchListType = new TypeToken<ArrayList<Competition.Match>>() {
        }.getType();


        List<Competition.Match> matches = gson.fromJson((jsonObject.get("matches")), matchListType);

        return matches;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
