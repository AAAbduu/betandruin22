package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Movement {

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    @Id
    @GeneratedValue
    private Integer movementNumber;

    private double amount;

    private String description;

    private String tooltip;

    public Movement(double amount, String description, String tooltip) {
        this.amount = amount;
        this.description = description;
        this.tooltip = tooltip;
    }
}
