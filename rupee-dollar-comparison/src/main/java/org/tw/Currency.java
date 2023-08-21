package org.tw;

public class Currency {
    private final double value;
    private final String currencyName;

    private Currency(double value, String currencyType) {
        this.value = value;
        this.currencyName = currencyType;
    }

    public static Currency createRupee(double value) {
        return new Currency(value, "Rs");
    }

    public static Currency createDollar(double value) {
        return new Currency(value, "$");
    }

    public double getValue() {
        return value;
    }

    public String getCurrencyType(){
        return currencyName;
    }

}
