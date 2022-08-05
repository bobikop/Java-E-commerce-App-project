package discount;

import java.util.UUID;

public class AmountBasedDiscount extends Discount{

    private Double discountAmount;


    public AmountBasedDiscount(UUID id, String name, Double thresholdAmount, Double discountAmount) {
        super(id, name, thresholdAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public Double calculateCartAmountAfterDiscountApply(Double amount) {
        return null;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }
}
