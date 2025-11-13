package pr1.helper.repository;

import pr1.helper.entity.MixedNumber;

import java.util.ArrayList;

public class MixedNumberList extends ArrayList<MixedNumber> {

    public double getMaxAmount() {
        return this.stream().mapToDouble(MixedNumber::getAmount).max().orElse(0);
    }

    public int getMaxAmountLength() {
        return String.valueOf((int) getMaxAmount()).length();
    }

    public int getMaxPrecision() {
        return this.stream().mapToInt(MixedNumber::getPrecision).max().orElse(0);
    }

}
