package domain;

import javax.persistence.*;

@Entity
public class Fee {

    @Id
    @GeneratedValue
    private Integer feeNumber;
    private String result;
    private double fee;

    public Fee(String eResult, double eFee) {
        this.fee=eFee;
        this.result = eResult;
    }



    @Override
    public String toString() {
        return "Fee [result=" + result + ", fee=" + fee + "]";
    }

}