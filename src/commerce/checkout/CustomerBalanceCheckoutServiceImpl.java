package checkout;

import balance.CustomerBalance;

import Customer;
import StaticConstants;
import balance.Balance;
import java.util.UUID;
import StaticConstant.CUSTOMER_BALANCE_LIST;
public class CustomerBalanceCheckoutServiceImpl implements CheckoutService{

    @Override
    public boolean checkout(Customer customer, Double totalAmount) {
        CustomerBalance customerBalance = findCustomerBalance(customer.getId());
        double finalBalance = customerBalance.getBalance() - totalAmount;
        if (finalBalance > 0){
            customerBalance.setBalance(finalBalance);
            return true;
        }
        return false;
    }
    private static CustomerBalance findCustomerBalance(UUID customerId){
        for(Balance customerBalance : StaticConstant.CUSTOMER_BALANCE_LIST){
            if(customerBalance.getCustomerId().toString().equals(customerId.toString())){
                return (CustomerBalance) customerBalance;
            }
        }

        CustomerBalance customerBalance = new CustomerBalance(customerId,0d);
        StaticConstant.CUSTOMER_BALANCE_LIST.add(customerBalance);

        return customerBalance;
    }
}
