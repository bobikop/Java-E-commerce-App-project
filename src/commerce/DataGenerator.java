import balance.Balance;
import balance.CustomerBalance;
import category.Category;
import category.Electronic;
import discount.AmountBasedDiscount;
import discount.Discount;
import discount.RateBasedDiscount;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataGenerator {

public static void createCustomer(){

    Address address1Customer1 = new Address("7925", "Jones Branch Dr", "Suite 3300", "22102","VA");
    Address address2Customer1 = new Address("15415", "Beach Water Court", "Suite 33t0", "22025","VA");
    Address address3Customer3 = new Address("1585", "Greenhouse Dr", "House", "22302","VA");


    List<Address> customer1AddressList = new ArrayList<>();
    customer1AddressList.add(address1Customer1);
    customer1AddressList.add(address2Customer1);

    Customer customer1 = new Customer (UUID.randomUUID(), "ozzy", "ozzy@cydeo.com",  customer1AddressList);
    Customer customer2 = new Customer(UUID.randomUUID(), "mike", "mike@gmail.com");

    StaticConstant.CUSTOMER_LIST.add(customer1);
    StaticConstant.CUSTOMER_LIST.add(customer1);

     }


     public static void createCategory(){

         Category category1 = new Electronic(UUID.randomUUID(),"Electronic");
         Category category2 = new Electronic(UUID.randomUUID(),"Furniture");
         Category category3 = new Electronic(UUID.randomUUID(),"Skin Care");

         StaticConstant.CATEGORY_LIST.add(category1);
         StaticConstant.CATEGORY_LIST.add(category2);
         StaticConstant.CATEGORY_LIST.add(category2);

     }

           public static void createProduct(){

    Product product1 = new Product(UUID.randomUUID(), "PS5", 230.72, 7, 7,StaticConstant.CATEGORY_LIST.get(0).getId());

               Product product2 = new Product(UUID.randomUUID(), "XBOX", 230.72, 6, 6,StaticConstant.CATEGORY_LIST.get(0).getId());
               Product product3 = new Product(UUID.randomUUID(), "Chair", 30.78, 166, 166,StaticConstant.CATEGORY_LIST.get(0).getId());
               Product product4 = new Product(UUID.randomUUID(), "milk", 23.72, 8, 8,UUID.randomUUID());

               StaticConstant.PRODUCT_LIST.add(product1);
               StaticConstant.PRODUCT_LIST.add(product2);
               StaticConstant.PRODUCT_LIST.add(product3);
               StaticConstant.PRODUCT_LIST.add(product4);
          }




          public static void createBalance(){

           Balance customerBalance = new CustomerBalance(StaticConstant.CUSTOMER_LIST.get(0).getId(), 450.00 );
           Balance giftCardBalance = new CustomerBalance(StaticConstant.CUSTOMER_LIST.get(1).getId(), 500.00 );


              StaticConstant.CUSTOMER_BALANCE_LIST.add(customerBalance);
              StaticConstant.GIFT_CARD_BALANCE_LIST.add(giftCardBalance);
          }




          public static void createDiscount(){
              Discount amountBasedDiscount = new AmountBasedDiscount(UUID.randomUUID(),"Spend $250 , get $50 back!", 250.00, 50.00);
              Discount rateBasedDiscount = new RateBasedDiscount(UUID.randomUUID(),"Spend $500 , get 15% off!", 500.00, 75.00);

              StaticConstant.DISCOUNT_LIST.add(amountBasedDiscount);
              StaticConstant.DISCOUNT_LIST.add(rateBasedDiscount);

          }
















}
