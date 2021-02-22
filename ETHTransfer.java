package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;


public class ETHTransfer extends AppCompatActivity {

    private Web3j web3j =  Web3j.build(new HttpService("https://ropsten.infura.io/v3/37132f5960df4aca855ab542d74d9f91"));
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userAddr;
    private String password;
    private String walletDir;
    private String walletName;
    private Button send;
    private TextView receiver;
    private TextView value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_t_h_transfer);
        this.userAddr = this.getIntent().getStringExtra("address");
        getPassword(userAddr);
        getWalletDir(userAddr);
        getWalletName(userAddr);
        receiver = (TextView) findViewById(R.id.editTextTextPersonName) ;
        value = (TextView)findViewById(R.id.editTextTextPersonName2) ;
        send = (Button) findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recA = receiver.getText().toString();
                BigDecimal amount = new BigDecimal(value.getText().toString());
                sendTransaction(recA,amount);
                toastAsync(userAddr);
            }
        });
    }

    private void getPassword(String walletAddress) {
        db.collection("Wallets").document(walletAddress).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        password = (String) document.get("password");
                    }
                }
            }
        });
    }
    private void getWalletDir(String walletAddress){
        db.collection("Wallets").document(walletAddress).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        walletDir = (String) document.get("walletDirectory");
                    }
                }
            }
        });
    }
    private void getWalletName(String walletAddress){
        db.collection("Wallets").document(walletAddress).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        walletName = (String) document.get("walletName");
                    }
                }
            }
        });
    }
    public void sendTransaction(String receiverAdress, BigDecimal amount){
        try{
            Credentials credentials = WalletUtils.loadCredentials(password, walletDir + "/" + walletName);
            TransactionReceipt receipt = Transfer.sendFunds(web3j, credentials, receiverAdress, amount, Convert.Unit.ETHER).sendAsync().get();
            toastAsync("Transaction complete: " +receipt.getTransactionHash());
        }
        catch (Exception e){
            toastAsync(e.getMessage());
        }
    }

    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }
}