package domain;

import javax.persistence.*;

@Entity
public class Bet {

    @Id
    @GeneratedValue
    private Integer betNumber;

    private User user;
    private double amountBet;
    private double calculatedAmount;
    private Fee fee;


    private Question question;
    private String description;

    //@OneToOne(cascade = CascadeType.ALL)
    private Event event;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bet(User user, double amountBet, double calculatedAmount, Fee fee, Question question, Event event) {
        this.user = user;
        this.amountBet = amountBet;
        this.calculatedAmount = calculatedAmount;
        this.fee = fee;
        this.question = question;
        this.event = event;
        this.description = event.getDescription();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmountBet() {
        return amountBet;
    }

    public void setAmountBet(double amountBet) {
        this.amountBet = amountBet;
    }

    public double getCalculatedAmount() {
        return calculatedAmount;
    }

    public void setCalculatedAmount(double calculatedAmount) {
        this.calculatedAmount = calculatedAmount;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }
}
