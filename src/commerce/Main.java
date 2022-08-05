import balance.Balance;
import balance.CustomerBalance;
import balance.GiftCardBalance;
import category.Category;
import discount.Discount;
import order.Order;
import order.OrderService;
import order.OrderServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
          DataGenerator.createCustomer();
          DataGenerator.createCategory();
          DataGenerator.createProduct();
          DataGenerator.createBalance();
          DataGenerator.createDiscount();
        Scanner scanner = new Scanner(System.in);// step 1

        System.out.println("Select Customer");
        for (int i = 0 ; i < StaticConstant.CUSTOMER_LIST.size(); i++){ // step 2
            System.out.println("Type " + i + " fot customer:" + StaticConstant.CUSTOMER_LIST .get(i).getUsername());
        }

        Customer customer = StaticConstant.CUSTOMER_LIST.get(scanner.nextInt());// step 3

        Cart cart = new Cart(customer);

        while(true){// step 4

            System.out.println("What you would like to do? Just type id for selection");

            for (int i = 0; i< prepareManuOptions().length;i++){ //step 6
                System.out.println(i + "-" + prepareManuOptions()[i]);

            }
            int menuSelection = scanner.nextInt();
            switch(menuSelection){
                case 0:
                    for (Category category : StaticConstant.CATEGORY_LIST){
                        System.out.println("Category code: " +category.generateCategoryCode() + " category name " + category.getName());
                    }
                    break;
                case 1: // list product // product name , product category name
                    try{
                        for (Product product : StaticConstant.PRODUCT_LIST){
                            System.out.println("Product name: " + product.getName() + " Product category " +  product.getCategoryName());
                        }

                    }catch (Exception e){
                        System.out.println("Product could not printed because category not found for product name" + e.getMessage().split(",")[1]);
                    }
                    break;
                case 2: // list discounts

                    for (Discount discount : StaticConstant.DISCOUNT_LIST){
                        System.out.println("Discount name, " + discount.getName() + " discound threshold amount: " + discount.getThresholdAmount());
                    }
                    break;
                case 3:// see balance
                    CustomerBalance cBalance = findCustomerBalance(customer.getId());
                    GiftCardBalance gBalance = findGiftCardBalance(customer.getId());
                    double totalBalance = cBalance.getBalance() + gBalance.getBalance();
                    System.out.println("Total balance: " + totalBalance);
                    System.out.println("Customer balance: " + cBalance.getBalance());
                    System.out.println("Gift Card Balance: " + gBalance.getBalance());

                    break;
                case 4:
                    CustomerBalance customerBalance = findCustomerBalance(customer.getId());
                    GiftCardBalance giftCardBalance = findGiftCardBalance(customer.getId());
                    System.out.println("Which account you would like to add?");
                    System.out.println("Type 1 for customer Balance: " + customerBalance.getBalance());
                    System.out.println("Type 1 for customer Balance: " + giftCardBalance.getBalance());
                    int balanceAccountSelection = scanner.nextInt();
                    System.out.println("How much you would like to add?");
                    double additionalAmount = scanner.nextInt();

                    switch(balanceAccountSelection){
                        case 1:
                            customerBalance.addBalance(additionalAmount);
                            System.out.println("New Customer Balance: " + customerBalance.getCustomerId());
                            break;
                            case 2:
                                giftCardBalance.addBalance(additionalAmount);
                                System.out.println("New Gift Card Balance" + giftCardBalance.getBalance());
                        break;
                    }

                    break;
                case 5:

                    Map<Product, Integer>  map = new HashMap<>();  // we created empty map. So when user is login in he have empty cart which is map (key is product name, and value is product quantity
                    cart.setProductMap(map); // put empty map here
                    while (true){ // asking user which product and quantity want to select and add to cart
                        System.out.println("Which product you want to add to your car. For exit product selection Type : exit");
                        for (Product product : StaticConstant.PRODUCT_LIST){
                            try {
                                System.out.println("id: " + product.getId() +
                                        "price: " + product.getPrice() +
                                        " product category " + product.getCategoryName() +
                                        "stock " + product.getRemainingStock() +
                                        " product delivery due: "+ product.getDeliveryDueDate());

                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                        }

                        String productId = scanner.next();// by using Id  we select the product

                        try {
                            Product product = findProductById(productId); // product is OBJECT from database
                            if (!putItemToCartIfStockAvailable(cart, product)){ // check if remain stack condition are good or not
                                System.out.println("Stock is insufficient. Please try again");
                                 continue;
                            }
                        } catch  (Exception e){
                            System.out.println(" Product does not exist, please try later");
                            continue;
                        }
                        System.out.println("Do you wan to add another product? Type Y for adding more, type N for exit");
                        String decision = scanner.next();
                        if (!decision.equals("Y")){ // maybe we can use equalsIgnoreCase here?
                            break;
                        }
                    }
                    System.out.println("seems there are discount options. Do you want to see and apply to your cart if it is applicable. For no discount type no");
                    for (Discount discount : StaticConstant.DISCOUNT_LIST) {
                        System.out.println("discount id " + discount.getId() + " discount name: " + discount.getName());
                    }
                    String discountId = scanner.next();
                    if (!discountId.equals("no")) {
                        try {
                            Discount discount = findDiscountById(discountId);
                            if (discount.decideDiscountIsApplicableToCart(cart)) {
                                cart.setDiscountId(discount.getId());
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    }

                    OrderService orderService = new OrderServiceImpl();
                    String result = orderService.placeOrder(Cart);
                    if (result.equals("Order has been placed successfully")) {
                        System.out.println("Order is successful");
                        updateProductStock(cart.getProductMap());
                        cart.setProductMap(new HashMap<>());
                        cart.setDiscountId(null);
                    } else {
                        System.out.println(result);
                    }
                    break;
                case 6://See cart
                    System.out.println("Your Cart");
                    if (!cart.getProductMap().keySet().isEmpty()) {
                        for (Product product : cart.getProductMap().keySet()) {
                            System.out.println("product name: " + product.getName() + " count: " + cart.getProductMap().get(product));
                        }
                    } else {
                        System.out.println("Your cart is empty");
                    }
                    break;
                case 7://See order details
                    printOrdersByCustomerId(customer.getId());
                    break;
                case 8://See your addresses
                    printAddressByCustomerId(customer);
                    break;
                case 9: //Close App
                    System.exit(1);
                    break;
            }

        }
    }
    private static Discount findDiscountById(String discountId) throws Exception {
        for (Discount discount : StaticConstant.DISCOUNT_LIST) {
            if (discount.getId().toString().equals(discountId)) {
                return discount;
            }
        }
        throw new Exception("Discount couldn't applied because couldn't found");
    }

    private static void updateProductStock(Map<Product, Integer> map) {
        for (Product product : map.keySet()) {
            product.setRemainingStock(product.getRemainingStock() - map.get(product));
        }
    }

    private static void printOrdersByCustomerId(UUID customerId) {
        for (Order order : StaticConstant.ORDER_LIST) {
            if (order.getCustomerId().toString().equals(customerId.toString())) {
                System.out.println("Order status: " + order.getOrderStatus() + " order amount " + order.getPaidAmount() + " order date " + order.getOrderDate());
            }
        }
    }

    private static void printAddressByCustomerId(Customer customer) {
        for (Address address : customer.getAddress()) {
            System.out.println(" Street Name: " + address.getStreetName() +
                    " Street Number: " + address.getStreetNumber() + "ZipCode:  "
                    + address.getZipCode() + " State: " + address.getState());
        }
    }


    private static boolean putItemToCartIfStockAvailable(Cart cart, Product product) {
        System.out.println("Please provide product count:");
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        // when we want to add something in the cart we have two scenarios: 1st we have product, and we add another product, 2nd we don't have any product in the cart, and then we add new product

        Integer cartCount = cart.getProductMap().get(product);// quantity from the cart

        if (cartCount != null && product.getRemainingStock() > cartCount + count){ // something is in the cart -

        } else if (product.getRemainingStock()>=count){ // if there are more than one
            cart.getProductMap().put(product,count);
            return true;
        }
            return  false;
        }

    private static Product findProductById(String productId) throws Exception {
        for(Product product : StaticConstant.PRODUCT_LIST){
            if (product.getId().toString().equals(productId)) {
                return product;
            }
        }
        throw new Exception("Product not found");
    }
    private static CustomerBalance findCustomerBalance(UUID customerId){
        for (Balance customerBalance : StaticConstant.CUSTOMER_BALANCE_LIST){
            if (customerBalance.getCustomerId().toString().equals(customerId.toString())){
                return (CustomerBalance) customerBalance;
            }
        }
        CustomerBalance customerBalance = new CustomerBalance(customerId, 0.00);
        StaticConstant.CUSTOMER_BALANCE_LIST.add(customerBalance);

        return  customerBalance;
    }
    private static GiftCardBalance findGiftCardBalance(UUID customerId){
        for (Balance giftCardBalance : StaticConstant.GIFT_CARD_BALANCE_LIST){
            if (giftCardBalance.getCustomerId().toString().equals(customerId.toString())){
                return (GiftCardBalance)  giftCardBalance;
            }
        }
        GiftCardBalance giftCardBalance = new GiftCardBalance(customerId,0.00);
        StaticConstant.GIFT_CARD_BALANCE_LIST.add(giftCardBalance);
        return  giftCardBalance;
    }
    private static String[] prepareManuOptions(){ //step 5
        return new String[] {"List Categories", "List Products", "List Discount", "See Balance", "Add Balance",
        "Place and order", "See cart", "See order details", "See order details", "See your address", "Close App" };
    }
}
