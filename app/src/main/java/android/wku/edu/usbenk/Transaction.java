package android.wku.edu.usbenk;

import java.io.Serializable;

/**
 * Created by Ben on 4/26/2017.
 */

public class Transaction implements Serializable{

    private int id;
    private int senderID;
    private int receiverID;
    private int amount;
    private String date;

    public Transaction(int id, int senderID, int receiverID, int amount, String date) {
        this.id = id;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }





}
