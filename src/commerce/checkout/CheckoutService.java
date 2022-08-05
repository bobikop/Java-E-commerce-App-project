package checkout;


public interface CheckoutService {

    boolean checkout(Customer customer, Double totalAmount);
}
