package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.util.ArrayList;

public class ImportWalletScreen extends AppCompatActivity {

    private TextView walletName;
    private TextView password;
    private TextView walletDirectory;
    private Button importButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userName;
    private String pw;
    private ArrayList<String> walletAddresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet_screen);
        this.userName = this.getIntent().getStringExtra("Username");
        this.pw = this.getIntent().getStringExtra("password");
        this.walletAddresses = this.getIntent().getStringArrayListExtra("walletAddresses");
        walletName = (TextView) findViewById(R.id.editTextTextPersonName11);
        password = (TextView) findViewById(R.id.editTextTextPersonName10);
        walletDirectory = (TextView) findViewById(R.id.editTextTextPersonName12);
        importButton = (Button) findViewById(R.id.button13);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    importWallet(password.getText().toString(), walletName.getText().toString(), walletDirectory.getText().toString());
                    toastAsync("Wallet Imported");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CipherException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void importWallet(String password, String walletName, String walletDirectory) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials(password, walletDirectory + "/" + walletName);
        String wA = credentials.getAddress();
        if (wA.length()>10){
            Wallet createdWallet = new Wallet(wA, password, walletDirectory, walletName);
            db.collection("Wallets").document(wA).set(createdWallet);
            User tempUser = new User(userName, pw);
            for(String a : walletAddresses){
                tempUser.addWalletAdress(a);
            }
            walletAddresses.add(wA);
            tempUser.addWalletAdress(wA);
            db.collection("Users").document(userName).set(tempUser);
        }
    }
    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }
}