package org.tw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tw.Exceptions.InsufficientWalletBalanceException;
import org.tw.Exceptions.InvalidCurrencyTypeException;
import org.tw.Exceptions.MaximumWalletLimitReachedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WalletTest {
    private Wallet wallet;

    @BeforeEach
    void setUp(){
        wallet = new Wallet();
    }
    @Test
    void shouldReturnTrueWhenOneDollarIsComparedWithItsEquivalentRupees(){
        Currency seventyFourPointEightFiveRupees = Currency.createRupee(74.85);

        assertThat(wallet.oneDollarValueIsEquivalentTo(seventyFourPointEightFiveRupees), is(true));
    }

    @Test
    void shouldReturnFalseWhenOneDollarIsComparedWithItsNonEquivalentRupees(){
        Currency eightyRupees = Currency.createRupee(80);

        assertThat(wallet.oneDollarValueIsEquivalentTo(eightyRupees), is(false));
    }

    @Test
    void shouldNotThrowExceptionWhenTheMoneyIsAdded(){
        Currency fiveHundredRupees = Currency.createRupee(500);
        assertDoesNotThrow(() -> wallet.deposits(fiveHundredRupees));
    }

    @Test
    void shouldThrowExceptionWhenTheMoneyAddedIsInfinite(){
        Currency infiniteDollars = Currency.createDollar(Double.MAX_VALUE);
        assertThrows(MaximumWalletLimitReachedException.class, () -> wallet.deposits(infiniteDollars));
    }

    @Test
    void shouldReturnTheWalletBalanceInRupeesWhenThePreferredCurrencyIsRupees() throws Exception {
        Currency fiftyRupees = Currency.createRupee(50);
        Currency oneDollar = Currency.createDollar(1);

        wallet.deposits(fiftyRupees);
        wallet.deposits(oneDollar);

        assertThat(wallet.balanceIn("Rs"),is(124.85));
    }

    @Test
    void shouldReturnTheWalletBalanceInDollarsWhenThePreferredCurrencyIDollars() throws Exception {
        Currency seventyFourPointEightFiveRupees = Currency.createRupee(74.85);
        Currency oneDollar = Currency.createDollar(1);
        Currency oneHundredFortyNinePointSevenRupees = Currency.createRupee(149.7);

        wallet.deposits(seventyFourPointEightFiveRupees);
        wallet.deposits(oneDollar);
        wallet.deposits(oneHundredFortyNinePointSevenRupees);

        assertThat(wallet.balanceIn("$"),is(4.0));
    }

    @Test
    void shouldReturnZeroWhenTheWalletIsEmpty() throws Exception {
        assertThat(wallet.balanceIn("Rs"), is(0.0));
    }

    @Test
    void shouldThrowExceptionWhenThePreferredCurrencyIsInvalid(){
        assertThrows(InvalidCurrencyTypeException.class, () -> wallet.balanceIn("blah"));
    }

    @Test
    void shouldNotThrowExceptionWhenWithDrawingMoney() throws Exception{
        Currency seventyFourPointEightFiveRupees = Currency.createRupee(74.85);
        Currency oneDollar = Currency.createDollar(1);
        Currency oneHundredFortyNinePointSevenRupees = Currency.createRupee(149.7);

        wallet.deposits(seventyFourPointEightFiveRupees);
        wallet.deposits(oneDollar);
        wallet.deposits(oneHundredFortyNinePointSevenRupees);

        assertDoesNotThrow(()->wallet.withdraw(oneDollar));
    }

    @Test
    void shouldThrowExceptionWhenWithDrawingInsufficientMoney() throws Exception{
        Currency fiftyRupees = Currency.createRupee(50);
        Currency oneDollar = Currency.createDollar(1);

        wallet.deposits(fiftyRupees);

        assertThrows(InsufficientWalletBalanceException.class, ()->wallet.withdraw(oneDollar));
    }

}
