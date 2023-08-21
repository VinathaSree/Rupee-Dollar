package org.tw;
import org.tw.Exceptions.InsufficientWalletBalanceException;
import org.tw.Exceptions.InvalidCurrencyTypeException;
import org.tw.Exceptions.MaximumWalletLimitReachedException;
public class Wallet {
    private static final double rupeeValueOfOneDollar = 74.85;
    private Currency walletBalance;

    public Wallet() {
        this.walletBalance = Currency.createRupee(0);
    }

    public boolean oneDollarValueIsEquivalentTo(Currency rupees) {
        return rupees.getValue() == rupeeValueOfOneDollar;
    }

    private Currency convertToEquivalentRupees(Currency currencyToBeConverted){
        return Currency.createRupee(currencyToBeConverted.getValue() * rupeeValueOfOneDollar);
    }

    private boolean areCurrencyTypesEqual(Currency currencyToBeChecked){
        return walletBalance.getCurrencyType().equals(currencyToBeChecked.getCurrencyType());
    }

    public void deposits(Currency currencyToBeDeposited) throws MaximumWalletLimitReachedException {
        if (!areCurrencyTypesEqual(currencyToBeDeposited)) {
            currencyToBeDeposited = convertToEquivalentRupees(currencyToBeDeposited);
        }
        walletBalance = Currency.createRupee(walletBalance.getValue() + currencyToBeDeposited.getValue());
        if (Double.isInfinite(walletBalance.getValue())) {
            throw new MaximumWalletLimitReachedException();
        }
    }

    public double balanceIn(String preferredCurrency) throws InvalidCurrencyTypeException {
        if (preferredCurrency.equals("Rs")) {
            return walletBalance.getValue();
        } else if (preferredCurrency.equals("$")) {
            return walletBalance.getValue()/rupeeValueOfOneDollar;
        }
        throw new InvalidCurrencyTypeException();
    }

    public void withdraw(Currency currencyToBeWithDrawn) throws InsufficientWalletBalanceException {
        if(!areCurrencyTypesEqual(currencyToBeWithDrawn)){
            currencyToBeWithDrawn = convertToEquivalentRupees(currencyToBeWithDrawn);
        }
        if (currencyToBeWithDrawn.getValue() > walletBalance.getValue()){
            throw  new InsufficientWalletBalanceException();
        }
    }

}
