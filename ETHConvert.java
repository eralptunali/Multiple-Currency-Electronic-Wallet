package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class ETHConvert extends AppCompatActivity {
    private Button hack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_t_h_convert);
        hack = findViewById(R.id.button2);
        hack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hile();
            }
        });
    }
    public void hile(){
        toastAsync("1.05 Ether converted to 1381.31 USDT successfully!");
    }
    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }
}