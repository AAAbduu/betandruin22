package businessLogic;

import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Bet;
import domain.Event;
import domain.Question;
import domain.User;
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

    void setUser(User eUser);

	User getUser();

    void setBet(Bet bet);

	void updateUser(User user);

    void removeEvent(Event event);
}