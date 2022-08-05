package balance;
import  java.util.UUID;
public abstract class Balance { // whoever extend this class must override the method (this is for first concrete class)

    private UUID customerId;
    private Double balance;

    public Balance(UUID customerId, Double balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    public abstract Double addBalance(Double additionalBalance);// abstract class


    //encapsulation -answer - im my class I use sometime just getter (no setters) and then set the values with constructor

    public UUID getCustomerId() {
        return customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
