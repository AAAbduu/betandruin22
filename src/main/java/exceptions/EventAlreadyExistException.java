package exceptions;

public class EventAlreadyExistException extends Exception {
    /**
     * This Exception is triggered if administrator tries to add
     *  an already existing event in the same date.
     */
    public EventAlreadyExistException() {
        super();
    }

}