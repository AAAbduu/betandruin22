package businessLogic;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Question;
import domain.User;
import exceptions.EventAlreadyExistException;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;


/**
 * Implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BlFacade")
public class BlFacadeImplementation implements BlFacade {

	DataAccess dbManager;
	ConfigXML config = ConfigXML.getInstance();
	private User currentUser;

	public BlFacadeImplementation()  {
		System.out.println("Creating BlFacadeImplementation instance");
		boolean initialize = config.getDataBaseOpenMode().equals("initialize");
		dbManager = new DataAccess(initialize);
		if (initialize)
			dbManager.initializeDB();
		dbManager.close();
	}

	public BlFacadeImplementation(DataAccess dam)  {
		System.out.println("Creating BlFacadeImplementation instance with DataAccess parameter");
		if (config.getDataBaseOpenMode().equals("initialize")) {
			dam.open(true);
			dam.initializeDB();
			dam.close();
		}
		dbManager = dam;
	}


	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 *
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */

	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		//The minimum bid must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);
		dbManager.close();
		return qry;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 *
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}


	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		dbManager.close();
	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD(){
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	/**
	 * Method checks if the user trying to sign-up is already registered in the system.
	 * @param eUser User to check if registered.
	 */
	public boolean checkIfRegistered(User eUser) {
		dbManager.open(false);
		boolean r= dbManager.checkIfRegistered(eUser);
		dbManager.close();
		return r;
	}
	/**
	 * Procedure that registers a new user into the system.
	 * @param user User to be registered.
	 */
	public void register(User user) {
		dbManager.open(false);
		dbManager.registerUser(user);
		dbManager.close();

	}

	/**
	 * Method in charge of returning the user object which contains information about the user trying to log-in.
	 * @param usname Username
	 * @param psswd Password
	 * @return User associated with the username and password if found, else null is returned.
	 */
	public User login(String usname, String psswd) {

		dbManager.open(false);
		User r = dbManager.login(usname,psswd);
		dbManager.close();
		return r;
	}


	/**
	 * Method which updates an existing question when a fee is added for that question.
	 * @param toAdd Question to be updated.
	 */
	@WebMethod
	public void updateQuestionFees(Question toAdd) {
		dbManager.open(false);
		dbManager.updateQuestion(toAdd);
		dbManager.close();

	}

	/**
	 * Method which returns the question associated with its question number in the DB.
	 * @param qn Question number.
	 * @return Question which is being found.
	 */
	@WebMethod
	public Question getQuestion(int qn) {
		dbManager.open(false);
		Question q = dbManager.findQuestion(qn);
		dbManager.close();

		return q;
	}

	/**
	 * Method in charge of adding in the DB a given Event.
	 * @param toAdd Event to add in the DB.
	 * @throws EventAlreadyExistException if Event already exists in the DB.
	 */
	@WebMethod
	public void addEvent(Event toAdd) throws EventAlreadyExistException {
		dbManager.open(false);
		dbManager.addEvent(toAdd);
		dbManager.close();
	}

	/**
	 * Method which sets the current user to the business logic, in order to communicate and obtains users information
	 * in the different views the applications has.
	 * @param eUser
	 */
	@Override
	@WebMethod
	public void setUser(User eUser) {
		this.currentUser = eUser;
	}

	/**
	 * Method which returns the current user logged-in.
	 * @return Current user logged-in.
	 */
	@Override
	@WebMethod
	public User getUser() {
		return currentUser;
	}

	/**
	 * Method which sets a bet, it updates the database with a new bet object type.
	 * @param bet Bet that is to be placed in the database.
	 */
	@Override
	@WebMethod
	public void setBet(Bet bet) {
		dbManager.open(false);
		dbManager.setBet(bet);
		dbManager.close();
	}

	/**
	 * Method which updates the information of the user which is received by parameter.
	 * @param user User which information needs to be updated.
	 */
	@Override
	public void updateUser(User user) {

		dbManager.open(false);
		dbManager.updateUser(user);
		dbManager.close();

	}

	/**
	 * Method which removes a given event and all the associated fees, questions and bets.
	 * @param event Event to be removed.
	 */
	@Override
	@WebMethod
	public void removeEvent(Event event) {
		dbManager.open(false);
		dbManager.removeEvent(event);
		dbManager.close();
	}


}