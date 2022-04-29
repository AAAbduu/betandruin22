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
import domain.Question;
import domain.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
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
                    System.out.println("Timer is working");
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
                        today = year + "-" + "0" + month + "-" + day;            //TAKING a month length events happening in real life.
                    } else {
                        today = year + "-" + month + "-" + day;
                    }

                    String responseLiga = ((BlFacadeImplementation) businessLogic).getManager().makeRequest("/v2/competitions/2014/matches?status=FINISHED&dateFrom=" + today + "&dateTo=" + today);
                    //             String response = ((BlFacadeImplementation)businessLogic).getManager().makeRequest("/v2/competitions/2014/matches?status=FINISHED");


                    List <Competition.Match> laLigamatches = obtainResponseCompetition(responseLiga);

                    //today = year + "-" + "0" + month + "-" + "30";
                    //laLigamatches.add(new Competition.Match(1, today, "FINISHED", new Competition.Score("AWAY_TEAM"), new Competition.Team("Madrid"), new Competition.Team("Barcelona")));


                    for (Competition.Match m : laLigamatches) {
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
                            //q2 = ev1.addQuestion("¿Quién meterá el primer gol?");
                            //q3 = ev1.addQuestion("¿Quién ganará el partido?", 1);
                            //q4 = ev1.addQuestion("¿Cuántos goles se marcarán?", 2);
                            //q5 = ev1.addQuestion("¿Quién ganará el partido?", 1);
                            //q6 = ev1.addQuestion("¿Habrá goles en la primera parte?", 2);
                        } else if (Locale.getDefault().equals(new Locale("en"))) {
                            q = "Who will win the match?";
                            // q2 = ev1.addQuestion("Who will score first?", 2);
                            //q3 = ev1.addQuestion("Who will win the match?", 1);
                            //q4 = ev1.addQuestion("How many goals will be scored in the match?", 2);
                            //q5 = ev1.addQuestion("Who will win the match?", 1);
                            //q6 = ev1.addQuestion("Will there be goals in the first half?", 2);
                        } else {
                            q = "Zeinek irabaziko du partidua?";
                            // q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
                            //q3 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
                            //q4 = ev1.addQuestion("Zenbat gol sartuko dira?", 2);
                            //q5 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
                            //q6 = ev1.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
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

                }
            });
            schedulerLiga.getTimer().schedule(schedulerLiga.getTask(), new Date(),6000); //max can make 10 requests per minute to the rest api, so i have 60000 ms in a minute 60000/10 = 6000 ms.
        }catch (Exception e){

        }

        try {
            Scheduler schedulerChampions = new Scheduler(new Timer(), new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Timer for champions is working");
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
                        today = year + "-" + "0" + month + "-" + day;            //TAKING a month length events happening in real life.
                    } else {
                        today = year + "-" + month + "-" + day;
                    }
                    String responseChampions = ((BlFacadeImplementation) businessLogic).getManager().makeRequest("/v2/competitions/2001/matches?status=FINISHED&dateFrom=" + today + "&dateTo=" + today);


                    List<Competition.Match> championsMatches = obtainResponseCompetition(responseChampions);


                    for (Competition.Match m : championsMatches) {
                        //String winner = m.score.
                    }

                }
            });

            schedulerChampions.getTimer().schedule(schedulerChampions.getTask(), new Date(), 6000); //max can make 10 requests per minute to the rest api, so i have 60000 ms in a minute 60000/10 = 6000 ms.

        }catch (Exception o){

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
