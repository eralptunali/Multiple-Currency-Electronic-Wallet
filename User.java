package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class User{

    private String userName;
    private String password;
    private List<String> walletAddresses = new ArrayList<String>();
    private int walletAmount;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getWalletAddresses() {
        return walletAddresses;
    }

    public void setWalletAddresses(List<String> walletAddresses) {
        this.walletAddresses = walletAddresses;
    }

    public void addWalletAdress(String address){
        this.walletAddresses.add(address);
    }

    public int getWalletAmount() {
        return this.walletAddresses.size();
    }

    public void setWalletAmount(int walletAmount) {
        this.walletAmount = walletAmount;
    }
}
