package domain;

import javax.persistence.*;

@Entity
public class Bet {

    @Id
    @GeneratedValue
    private Integer betNumber;

    @ManyToOne
    private User user;
    private double amountBet;
    private double calculatedAmount;
    @OneToOne
    private Fee fee;

    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bet(User user, double amountBet, double calculatedAmount, Fee fee) {
        this.user = user;
        this.amountBet = amountBet;
        this.calculatedAmount = calculatedAmount;
        this.fee = fee;
        this.description = fee.getQuestion().getEvent().getDescription();
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

    public String getCompleteDescription(){
        String d = "Event: " + fee.getQuestion().getEvent().getDescription()
                + "\nQuestion: " + fee.getQuestion().getQuestion()
                + "\nPrediction: " + this.fee.getResult()
                + "\nFee: " + this.fee.getFee()
                + "\nBet:" + this.amountBet;

        return d;

    }

}
