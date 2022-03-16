package domain;

import javax.persistence.Entity;

@Entity
public class Fee {
	
	@Override
	public String toString() {
		return "Fee [result=" + result + ", fee=" + fee + "]";
	}

	private String result;
	private double fee;
	
	public Fee(String eResult, double eFee) {
		this.fee=eFee;
		this.result = eResult;
	}

}
