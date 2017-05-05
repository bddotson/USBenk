package android.wku.edu.usbenk;

import java.io.Serializable;

/**
 * Created by Ben on 4/26/2017.
 */

public class Customer implements Serializable{

    private int id;
    private String name;
    private String username;
    private String password;
    private String loginDate;
    private int selected;

    public Customer(int id, String name, String username, String password, String loginDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.loginDate = loginDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String toString() {
        return name;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getSelected() {
        return selected;
    }
}
