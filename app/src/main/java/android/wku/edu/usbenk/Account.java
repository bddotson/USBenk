package android.wku.edu.usbenk;

import java.io.Serializable;

/**
 * Created by Ben on 4/26/2017.
 */

public class Account implements Serializable{

    private int id;
    private int customerID;
    private int balance;
    private String accountName;
    private String date;

    public Account(int id, int customerID, int balance, String accountName, String date) {
        this.id = id;
        this.customerID = customerID;
        this.balance = balance;
        this.accountName = accountName;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        String toString = String.format("%-38s %40s", accountName, "$"+balance);
        return toString;
    }
}