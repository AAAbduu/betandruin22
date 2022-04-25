package domain;

import javax.persistence.*;


@Entity
public class Fee {

    @Id
    @GeneratedValue
    private Integer feeNumber;

    @ManyToOne
    private Question question;

    private String result;
    private double fee;

    public String getResult() {
        return result;
    }

    public double getFee() {
        return fee;
    }

    public Fee(String eResult, double eFee, Question question) {
        this.fee=eFee;
        this.result = eResult;
        this.question = question;
    }



    @Override
    public String toString() {
        return "Fee [result=" + result + ", fee=" + fee + "]";
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}