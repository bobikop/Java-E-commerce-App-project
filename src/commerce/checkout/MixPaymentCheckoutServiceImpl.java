package checkout;
import Customer;
import StaticConstant;
import balance.Balance;
import balance.CustomerBalance;
import balance.GiftCardBalance;

import java.util.UUID;

public class MixPaymentCheckoutServiceImpl implements CheckoutService{
    @Override
    public boolean checkout(Customer customer, Double totalAmount) {
        try {
            GiftCardBalance giftCardBalance = findGiftCardBalance(customer.getId());

            // 300 giftcard balance
            // 450 customer balance
            // 600 cart

            // 300 - 600 = -300

            // 300 + 450 - 600 = 150

            final double giftBalance = giftCardBalance.getBalance() - totalAmount;
            if (giftBalance > 0 ){
                giftCardBalance.setBalance(giftBalance);
            }else {
                CustomerBalance customerBalance = findCustomerBalance(customer.getId());
                final double mixBalance = giftCardBalance.getBalance() + customerBalance.getBalance() - totalAmount;
                if (mixBalance > 0){
                    giftCardBalance.setBalance(0d);
                    customerBalance.setBalance(mixBalance);

                    return true;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }
    private static GiftCardBalance findGiftCardBalance(UUID customerId){
        for(Balance giftCarBalance : StaticConstant.GIFT_CARD_BALANCE_LIST){
            if(giftCarBalance.getCustomerId().toString().equals(customerId.toString())){
                return  (GiftCardBalance) giftCarBalance;
            }
        }

        GiftCardBalance giftCarBalance = new GiftCardBalance(customerId,0d);
        StaticConstant.GIFT_CARD_BALANCE_LIST.add(giftCarBalance);

        return giftCarBalance;
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