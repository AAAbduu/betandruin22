package exceptions;

public class UserIsUnderageException extends Exception{
	
	/**
	 * This Exception triggers if a non-registered user tries to register
	 * being underage.
	 */
	
	public UserIsUnderageException(){
		super();
	}

}
