package businessLogic;

import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.*;
import exceptions.EventAlreadyExistException;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BlFacade  {

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
	Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist;

	/**
	 * This method retrieves all the events of a given date
	 *
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);

	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);


	/**
	 * Method checks if the user trying to sign-up is already registered in the system.
	 * @param user User to check if registered.
	 */
	public boolean checkIfRegistered(User user);

	/**
	 * Procedure that register a new user into the system.
	 * @param user User to be registered.
	 */
	public void register(User user);


	/**
	 * Method which initiates transactions with the DB in order to log-in for registered users.
	 * @param usname
	 * @param psswd
	 * @return User logged-in
	 */
	public User login(String usname, String psswd);


	/**
	 * Method which updates an existing question when a fee is added for that question.
	 * @param toAdd Question to be updated.
	 */
	public void updateQuestionFees(Question toAdd);


	/**
	 * Method which returns the question associated with its question number in the DB.
	 * @param qn Question number.
	 * @return Question which is being found.
	 */
	public Question getQuestion(int qn);

	/**
	 * Method in charge of adding in the DB a given Event.
	 * @param toAdd Event to add in the DB.
	 * @throws EventAlreadyExistException if Event already exists in the DB.
	 */
	void addEvent(Event toAdd) throws EventAlreadyExistException;

	/**
	 * Method which sets the current user to the business logic, in order to communicate and obtains users information
	 * in the different views the applications has.
	 * @param eUser
	 */
    void setUser(User eUser);

	/**
	 * Method which returns the current user logged-in.
	 * @return Current user logged-in.
	 */
	User getUser();

	/**
	 * Method which sets a bet, it updates the database with a new bet object type.
	 * @param bet Bet that is to be placed in the database.
	 */
	void setBet(Bet bet);

	/**
	 * Method which updates the information of the user which is received by parameter.
	 * @param user User which information needs to be updated.
	 */
	void updateUserMoney(User user, double money);

	/**
	 * Method which removes a given event and all the associated fees, questions and bets.
	 * @param event Event to be removed.
	 */
    void removeEvent(Event event);

	/**
	 * Method in charge of removing a placed bet by a user, it will penalize 20% of the amount bet and will give back the 80% to the user.
	 * @param bet Bet to be removed.
	 */
    void removeBet(Bet bet);

	/**
	 * Method is in charge of publishing the results, updating the data base.
	 * @param result Result produced by an admin.
	 */
    void  publishResult(Result result);

	/**
	 * Method updates users money.
	 * @param user
	 */
	//void updateMoney(User user);

	void updateUser(User user);
}