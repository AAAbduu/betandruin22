package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Result {
    @Id
    @GeneratedValue
    private Integer resultNumber;

    @OneToOne
    private Question question;
    private String result;


    public Result(Question question, String result) {
        this.question = question;
        this.result = result;
    }


    public Question getQuestion() {
        return question;
    }

    public String getResult() {
        return result;
    }


}
