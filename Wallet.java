package com.example.myapplication;

public class Wallet {

    private String walletAddress;
    private String password;
    private String walletDirectory;
    private String walletName;


    public Wallet(String walletAddress, String password, String walletDirectory, String walletName) {
        this.walletAddress = walletAddress;
        this.password = password;
        this.walletDirectory = walletDirectory;
        this.walletName = walletName;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWalletDirectory() {
        return walletDirectory;
    }

    public void setWalletDirectory(String walletDirectory) {
        this.walletDirectory = walletDirectory;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
}
