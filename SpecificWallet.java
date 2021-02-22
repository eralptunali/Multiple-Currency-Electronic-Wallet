package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class SpecificWallet extends AppCompatActivity {

    private Web3j web3j =  Web3j.build(new HttpService("https://ropsten.infura.io/v3/37132f5960df4aca855ab542d74d9f91"));
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String address;
    private TextView wAddress;
    private String password;
    private String walletDir;
    private String walletName;
    private TextView ethCur;
    private Button sendEther;
    private Button convertEther;
    private TextView linkCur;
    private Button sendLink;
    private Button convertLink;
    private TextView tetherCur;
    private Button sendTether;
    private Button convertTether;
    private TextView eqvDollar;
    private TextView eqvTL;
    private TextView eqvEuro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_wallet);
        this.address = this.getIntent().getStringExtra("address");
        getPassword(address);
        getWalletName(address);
        getWalletDir(address);
        wAddress = (TextView) findViewById(R.id.textView9);
        wAddress.setText(address);
        ethCur = (TextView) findViewById(R.id.textView4);
        try {
            ethCur.setText("ETH: " + getEtherBalance().toString());
        } catch (IOException e) {
            toastAsync(e.getMessage());
        } catch (CipherException e) {
            toastAsync(e.getMessage());
        } catch (ExecutionException e) {
            toastAsync(e.getMessage());
        } catch (InterruptedException e) {
            toastAsync(e.getMessage());
        }
        eqvDollar = (TextView) findViewById(R.id.textView8);
        eqvEuro = (TextView) findViewById(R.id.textView11);
        eqvTL = (TextView) findViewById(R.id.textView10);
        try {
            BigDecimal dollarCure = new BigDecimal("605.7");
            BigDecimal result = dollarCure.multiply(getEtherBalance());
            eqvDollar.setText("DOLLAR: " + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            BigDecimal TLCure = new BigDecimal("4624.53");
            BigDecimal result = TLCure.multiply(getEtherBalance());
            eqvTL.setText("TL: " + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            BigDecimal EUROCure = new BigDecimal("497.2");
            BigDecimal result = EUROCure.multiply(getEtherBalance());
            eqvEuro.setText("EURO: " + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendEther = (Button) findViewById(R.id.send_eth);
        sendEther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendEtherScreen();
            }
        });
        sendTether = (Button) findViewById(R.id.sendh_usdt);
        sendTether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendTetherScreen();
            }
        });
        sendLink = (Button) findViewById(R.id.send_link);
        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendLinkScreen();
            }
        });
        convertEther = (Button) findViewById(R.id.convert_eth);
        convertEther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConvertEtherScreen();
            }
        });
        convertTether = (Button) findViewById(R.id.convert_usdt);
        convertTether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConvertTetherScreen();
            }
        });
        convertLink = (Button) findViewById(R.id.convert_link);
        convertLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConvertLINKScreen();
            }
        });

    }
    public void openSendEtherScreen(){
        Intent intent = new Intent(this, ETHTransfer.class);
        intent.putExtra("address", address);
        startActivity(intent);
    }
    public void openSendTetherScreen(){
        Intent intent = new Intent(this, USDTTransfer.class);
        intent.putExtra("address", address);
        startActivity(intent);
    }
    public void openSendLinkScreen(){
        Intent intent = new Intent(this, LINKTransfer.class);
        intent.putExtra("address", address);
        startActivity(intent);
    }
    public void openConvertEtherScreen(){
        Intent intent = new Intent(this, ETHConvert.class);
        intent.putExtra("address", address);
        startActivity(intent);
    }
    public void openConvertTetherScreen(){
        Intent intent = new Intent(this, USDTConvert.class);
        intent.putExtra("address", address);
        startActivity(intent);
    }
    public void openConvertLINKScreen(){
        Intent intent = new Intent(this, LINKConvert.class);
        intent.putExtra("address", address);
        startActivity(intent);
    }
    public BigDecimal getEtherBalance() throws IOException, CipherException, ExecutionException, InterruptedException {
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address.toString(), DefaultBlockParameterName.LATEST).sendAsync().get();
        return Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
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
    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }
}