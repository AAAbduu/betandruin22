package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Result {
    @Id
    @GeneratedValue
    private Integer resultNumber;

    private Event event;
    private Question question;
    private String result;


    public Result(Event event, Question question, String result) {
        this.event = event;
        this.question = question;
        this.result = result;
    }

    public Event getEvent() {
        return event;
    }


    public Question getQuestion() {
        return question;
    }

    public String getResult() {
        return result;
    }


}
