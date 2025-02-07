package dataAccess;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sun.xml.ws.util.StringUtils;
import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.EventAlreadyExistException;
import exceptions.QuestionAlreadyExist;
import httpManagement.Manager;

/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess  {

	protected EntityManager  db;
	protected EntityManagerFactory emf;

	protected Manager manager = new Manager();

	public EntityManager getDb() {
		return db;
	}

	public void setDb(EntityManager db) {
		this.db = db;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	ConfigXML config = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode)  {
		System.out.println("Creating DataAccess instance => isDatabaseLocal: " +
				config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());
		open(initializeMode);
	}

	public DataAccess()  {
		this(false);
	}


	/**
	 * This method initializes the database with some trial events and questions.
	 * It is invoked by the business logic when the option "initialize" is used
	 * in the tag dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB(){

		db.getTransaction().begin();

		try {

			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			String year = String.valueOf(calendar.get(Calendar.YEAR));
			String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			int monthInt = calendar.get(Calendar.MONTH)+1;
			if(monthInt ==12) monthInt=1;
			String month = String.valueOf(monthInt);
			String today;
			if(monthInt<10) {
				today = year + "-" + "0" + month + "-" + day;			//TAKING a month length events happening in real life.
			}else{
				today = year + "-" + month + "-" + day;
			}if(Integer.valueOf(day) < 10) {
				today = year + "-" + "0" + month + "-" + "0" + day;
			}
			monthInt++;
			if(monthInt ==12) monthInt=1;
			else if(day.contentEquals("31")) day = "30";
			month = String.valueOf(monthInt);
			String nextMonthtoday;
			if(monthInt<10) {
				nextMonthtoday = year + "-" + "0" + month + "-" + day;
			}else{
				nextMonthtoday = year + "-" + month + "-" + day;
			}if(Integer.valueOf(day) < 10){
				nextMonthtoday = year + "-" + "0" + month + "-" + "0"+day;
			}
			String responseLiga = manager.makeRequest("/v2/competitions/2014/matches?dateFrom="+today+"&dateTo="+nextMonthtoday);
			String responseChampions = manager.makeRequest("/v2/competitions/2001/matches?dateFrom="+today+"&dateTo="+nextMonthtoday);
//			String response = manager.makeRequest("/v2/competitions/2014/matches?dateFrom=2022-03-24&dateTo=2022-04-15");
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			JsonElement je = JsonParser.parseString(response);
//			String prettyJsonString = gson.toJson(je);
//			System.out.println(prettyJsonString);

			Gson gson = new Gson();

			JsonObject jsonObject;

			jsonObject = gson.fromJson(responseLiga, JsonObject.class);
			Type matchListType = new TypeToken<ArrayList<Competition.Match>>(){}.getType();

			List <Competition.Match> laLigamatches = gson.fromJson((jsonObject.get("matches")), matchListType);

			jsonObject = gson.fromJson(responseChampions, JsonObject.class);

			List <Competition.Match> championsMatches = gson.fromJson((jsonObject.get("matches")), matchListType);

			List <Competition.Match> allMatches = new ArrayList<>();

			allMatches.addAll(championsMatches);
			allMatches.addAll(laLigamatches);

			for(Competition.Match m : allMatches){
				if(m.status.contentEquals("SCHEDULED") && (m.homeTeam.name!=null)&&(m.awayTeam.name!=null)){
					String dateofEvent = m.utcDate;
					String [] split = dateofEvent.split("T");
					String [] spliDate = split[0].split("-");
					int yearE = Integer.valueOf(spliDate[0]);
					int monthE = Integer.valueOf(spliDate[1])-1;
					int dayE = Integer.valueOf(spliDate[2]);
					String eventDescription = m.homeTeam.name + "-" + m.awayTeam.name;
					Event ev1 = new Event( eventDescription, UtilDate.newDate(yearE, monthE, dayE));

					Question q1;
					Question q2;
					Question q3;
					Question q4;
					Question q5;
					Question q6;

					if (Locale.getDefault().equals(new Locale("es"))) {
						q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
						q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
						//q3 = ev1.addQuestion("¿Quién ganará el partido?", 1);
						q4 = ev1.addQuestion("¿Cuántos goles se marcarán?", 2);
						//q5 = ev1.addQuestion("¿Quién ganará el partido?", 1);
						q6 = ev1.addQuestion("¿Habrá goles en la primera parte?", 2);
					}
					else if (Locale.getDefault().equals(new Locale("en"))) {
						q1 = ev1.addQuestion("Who will win the match?", 1);
						q2 = ev1.addQuestion("Who will score first?", 2);
						//q3 = ev1.addQuestion("Who will win the match?", 1);
						q4 = ev1.addQuestion("How many goals will be scored in the match?", 2);
						//q5 = ev1.addQuestion("Who will win the match?", 1);
						q6 = ev1.addQuestion("Will there be goals in the first half?", 2);
					}
					else {
						q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
						q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
						//q3 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
						q4 = ev1.addQuestion("Zenbat gol sartuko dira?", 2);
						//q5 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
						q6 = ev1.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
					}

					//db.persist(q1);
					//db.persist(q2);
					//db.persist(q3);
					//db.persist(q4);
					//db.persist(q5);
					//db.persist(q6);

					db.persist(ev1);


				}
			}





			db.persist(new User("a", "a", "a", "a", "a", new Date(), false));

			db.persist(new User("admin", "admin", "admin", "admin", "a", new Date(), true));

			db.getTransaction().commit();
			System.out.println("The database has been initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 *
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum)
			throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event = " + event + " question = " +
				question + " minimum bet = " + betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.doesQuestionExist(question)) throw new QuestionAlreadyExist(
				ResourceBundle.getBundle("Etiquetas").getString("ErrorQuestionAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		//db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added
		// in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;
	}

	/**
	 * This method retrieves from the database the events of a given date
	 *
	 * @param date in which events are retrieved
	 * @return collection of events
	 */

	public Vector<Event> getEvents(Date date, boolean admin) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query;
		if(!admin) {

			query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.status.get('isFinished') = false",
					Event.class);
		}else{
			 query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",
					Event.class);
		}
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev "
				+ "WHERE ev.eventDate BETWEEN ?1 and ?2 AND ev.status.get('isFinished') = false", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}


	public void open(boolean initializeMode){

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " +
				config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());

		String fileName = config.getDataBaseFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (config.isDataAccessLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", config.getDataBaseUser());
			properties.put("javax.persistence.jdbc.password", config.getDataBasePassword());

			emf = Persistence.createEntityManagerFactory("objectdb://" + config.getDataAccessNode() +
					":"+config.getDataAccessPort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion => event = " + event +
				" question = " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.doesQuestionExist(question);
	}

	public void close(){
		db.close();
		System.out.println("DataBase is closed");
	}

	/**
	 * Method checks if the user trying to sign-up is already registered in the system.
	 * @param eUser User to check if registered.
	 */
	public boolean checkIfRegistered(User eUser) {

		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.userName=?1 AND u.email=?2", User.class);
		query.setParameter(1, eUser.getUserName());
		query.setParameter(2, eUser.getEmail());
		try {
			query.getSingleResult();
		}catch(NoResultException e) {
			return false;
		}
		return true;
	}

	/**
	 * Procedure that register a new user into the system.
	 * @param eUser User to be registered.
	 */
	public void registerUser(User eUser) {

		db.getTransaction().begin();
		db.persist(eUser);
		db.getTransaction().commit();


	}
	/**
	 * Method which initiates transactions with the DB in order to log-in for registered users.
	 * @param usname
	 * @param psswd
	 * @return boolean which indicates if credentials are correct.
	 */
	public User login(String usname, String psswd) {

		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.userName=?1 AND u.password=?2", User.class);
		query.setParameter(1, usname);
		query.setParameter(2, psswd);

		try {
			User u = query.getSingleResult();
			System.out.println("Succesfully logged-in!");
			return u;
		}catch(NoResultException e) {

			System.out.println("Credendtials are incorrect!");
			return null;

		}

	}
	/**
	 * Method which updates an existing question when a fee is added for that question.
	 * @param toAdd Question to be updated.
	 */
	public void updateQuestion(Question toAdd) {
		db.getTransaction().begin();
		db.merge(toAdd);
		db.flush();
		db.getTransaction().commit();

	}

	/**
	 * Method in charge of adding in the DB a given Event.
	 * @param toAdd Event to add in the DB.
	 * @throws EventAlreadyExistException if Event already exists in the DB.
	 */
	public void addEvent(Event toAdd) throws EventAlreadyExistException {

		if(!this.isEventInDb(toAdd.getDescription(), toAdd.getEventDate())) {
			db.getTransaction().begin();
			db.persist(toAdd);
			db.getTransaction().commit();
		}

	}


	/**
	 * Method which returns the question associated with its question number in the DB.
	 * @param qn Question number.
	 * @return Question which is being found.
	 */
	public Question findQuestion(int qn) {
		return db.find(Question.class, qn);

	}

	/**
	 * Method checks if there is an already exisiting event in the DB.
	 * @param event String which contains the description of the event.
	 * @param date Date in which the Event takes place
	 * @return false indicating there is no such Event in the database.
	 * @throws EventAlreadyExistException If the Event already exists.
	 */
	private boolean isEventInDb(String event, Date date) throws EventAlreadyExistException {

		TypedQuery<Event> query = db.createQuery("SELECT e FROM Event e WHERE e.description = ?1 AND e.eventDate = ?2", Event.class);
		query.setParameter(1, event);
		query.setParameter(2, date);

		try {
			query.getSingleResult();
			throw new EventAlreadyExistException();
		}catch(NoResultException e) {
			return false;
		}
	}

	/**
	 * Method in charge of updating the database when a user sets a bet.
	 * @param bet Bet to be included in the database.
	 */
	public void setBet(Bet bet) {

		db.getTransaction().begin();
		db.persist(bet);
		db.getTransaction().commit();

	}

	/**
	 * Method in charge of updating the users information whenever it is necessary.
	 * @param user User to be updated in the database.
	 */
	public void updateUserMoney(User user, double money) {

		db.getTransaction().begin();
		TypedQuery<User> query = db.createQuery("UPDATE User e set e.money = ?1 WHERE e = ?2", User.class);
		query.setParameter(1, money);
		query.setParameter(2, user);
		query.executeUpdate();
		db.getTransaction().commit();

	}


	/**
	 * Method which removes a given event, it also removes all the associated fees, questions and bets associated with the event.
	 * @param event Event to be removed from the database.
	 */
    public void removeEvent(Event event) {

		db.getTransaction().begin();
		Object detached = db.merge(event);
		TypedQuery<Bet> query = db.createQuery("SELECT e FROM Bet e WHERE e.fee.question.event = ?1 AND e.user is not null ", Bet.class);
		query.setParameter(1,event);


		List <Bet> bets = query.getResultList();

		for(Bet b: bets){
			db.remove(b);
			//u.removeBet(b);
			b.getUser().setMoney(b.getUser().getMoney() + b.getAmountBet());
			Movement movement = new Movement(b.getAmountBet(), "Admin deleted the event", b.getCompleteDescription() + "\nReason: Admin deleted the event.");
			b.getUser().addMovement(movement);
			b.getUser().removeBet(b);
			b.setUser(null);

		}
		db.remove(detached);

		db.getTransaction().commit();

    }

	/**
	 * Method which removes the given bet from the database.
	 * @param bet Bet to be removed from the database.
	 */
    public void removeBet(Bet bet) {

		db.getTransaction().begin();
		Object detached = db.merge(bet);
		db.remove(detached);
		db.getTransaction().commit();

    }

	/**
	 * Method is in charge of publishing the results, updating the data base.
	 * @param result Result produced by an admin.
	 */
	/**
	 * Method is in charge of publishing the results, updating the data base.
	 * @param result Result produced by an admin.
	 */
	public void publishResult(Result result) {
		db.getTransaction().begin();
		TypedQuery<Bet> query = db.createQuery("SELECT distinct e FROM Bet e WHERE e.fee.question = ?1 AND e.user is not null", Bet.class);
		query.setParameter(1,result.getQuestion());
		List <Bet> bets = query.getResultList();
		for(Bet b : bets){
				if (b.getFee().getResult().contentEquals(result.getResult())) {
					b.getUser().setMoney(b.getUser().getMoney() + b.getCalculatedAmount());
					Movement movement = new Movement(b.getCalculatedAmount(), "Bet won", b.getCompleteDescription());
					b.getUser().addMovement(movement);
				} else {
					Movement movement = new Movement(b.getAmountBet(), "Bet lost", b.getCompleteDescription());
					b.getUser().addMovement(movement);
				}
				b.getUser().removeBet(b);
				b.setUser(null);
		}

		db.getTransaction().commit();
	}

	/**
	 * Method that returns all the existing bets in the database.
	 * @return List containing all the bets in the database.
	 */
	public List<Bet> getBets(){
		TypedQuery<Bet> query = db.createQuery("SELECT distinct e FROM Bet e", Bet.class);
		List <Bet> bets = query.getResultList();
		return bets;
	}

	/**
	 * Method to update a user.
	 * @param user User to be updated.
	 */
	public void updateUser(User user) {
		db.getTransaction().begin();
		db.merge(user);
		db.getTransaction().commit();
	}

	/**
	 * Method to get an specific question from an specific event and date.
	 * @param question Question to find
	 * @param eventDate date from the event
	 * @param description description of the question that is being trying to get
	 * @return Question that is being looked for.
	 */
	public Question getSpecificQuestion(String question, Date eventDate, String description){

		TypedQuery<Question> query = db.createQuery("SELECT distinct q FROM Question q where q.question = ?1 and q.event.eventDate = ?2 and q.event.description = ?3", Question.class);
		query.setParameter(1,question);
		query.setParameter(2,eventDate);
		query.setParameter(3,description);
	try {
		Question q = query.getSingleResult();
		if(q!=null){
			return q;
		}
		}catch (Exception e){

		}
		return null;
	}

	public static void main(String[] args) {
		// EventNumber 7 Atlético - Athletic 17 - May 2022
		// who will win the match?
		//  QuestionNumber 1

		// betNumber 31 amountBet 4.0 calculatedAmount 8.0

		//DataAccess dt = new DataAccess(false);
//		Question question = dt.findQuestion(3);
//		Result result = new Result(question, "Athletic");
//		dt.publishResult(result);
//		dt.close();

		//dt.initializeDB();

	}

	/**
	 * Method to update an exisiting event.
	 * @param ev Event to be updated.
	 */
	public void updateEvent(Event ev) {

		db.getTransaction().begin();
		db.merge(ev);
		db.getTransaction().commit();

	}
	/*public static void main(String[] args) {
		Event event = new Event("Barcelona-Madrid",new Date());

		Question question = new Question("Who is winning?", 2.0F, event);

		Fee fee = new Fee("Bacelona",4.0);

		DataAccess dt = new DataAccess(false);

		TypedQuery<User> queryUser = dt.getDb().createQuery("SELECT e FROM User e " , User.class);

		User user = queryUser.getSingleResult();

		Bet bet = new Bet(user, 100,400, fee,question,event);

		user.addBet(bet);
		dt.getDb().getTransaction().begin();
		dt.getDb().persist(event);
		dt.getDb().persist(question);
		dt.getDb().persist(fee);
		dt.getDb().persist(user);
		dt.getDb().persist(bet);

		dt.getDb().getTransaction().commit();

		dt.removeEvent(event);


	}*/
}