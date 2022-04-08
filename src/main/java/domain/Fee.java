package domain;

import javax.persistence.*;

@Entity
public class Fee {

    @Id
    @GeneratedValue
    private Integer feeNumber;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Question question;

    private String result;
    private double fee;

    public String getResult() {
        return result;
    }

    public double getFee() {
        return fee;
    }

    public Fee(String eResult, double eFee) {
        this.fee=eFee;
        this.result = eResult;
    }



    @Override
    public String toString() {
        return "Fee [result=" + result + ", fee=" + fee + "]";
    }

}