package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.fail;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;
    private final static int USER_ID = 123;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        mRossi = new AccountHolder("Mario", "Rossi", USER_ID);
        bankAccount = new StrictBankAccount(mRossi, INITIAL_AMOUNT);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(INITIAL_AMOUNT, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(USER_ID, 100);
        double expectedValue = bankAccount.getBalance() - (bankAccount.getTransactionsCount() * StrictBankAccount.TRANSACTION_FEE + StrictBankAccount.MANAGEMENT_FEE);
        bankAccount.chargeManagementFees(USER_ID);
        assertEquals(expectedValue, bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        int transactionCountBeforeTest = bankAccount.getTransactionsCount();
        try {
            bankAccount.withdraw(USER_ID, -10);
        }
        catch(IllegalArgumentException e){
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
        assertEquals(transactionCountBeforeTest, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        int transactionCountBeforeTest = bankAccount.getTransactionsCount();
        try{
            bankAccount.withdraw(USER_ID, bankAccount.getBalance() * 2);
        } catch(IllegalArgumentException e ){
            assertEquals("Insufficient balance", e.getMessage());
        }
        assertEquals(transactionCountBeforeTest, bankAccount.getTransactionsCount());
    }
}
