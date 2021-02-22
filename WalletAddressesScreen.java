package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class WalletAddressesScreen extends AppCompatActivity implements  WalletRecyclerAdapter.onNoteListener{

    private String userName;
    private String password;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> userWalletAddresses;
    private RecyclerView recyclerView;
    private WalletRecyclerAdapter walletRecyclerAdapter;
    private ArrayList<String> helperOfWA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_addresses_screen);
        this.userName = this.getIntent().getStringExtra("Username");
        this.password = this.getIntent().getStringExtra("password");
        this.helperOfWA = this.getIntent().getStringArrayListExtra("walletAddresses");
        viewSettings();
        fillTheArray();
        walletRecyclerAdapter.notifyDataSetChanged();
    }

    private void viewSettings() {
        recyclerView = findViewById(R.id.recyclerview);
        userWalletAddresses = new ArrayList<>();
        walletRecyclerAdapter = new WalletRecyclerAdapter(userWalletAddresses, this);
        recyclerView.setAdapter(walletRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void fillTheArray(){
        for(String s : helperOfWA){
            userWalletAddresses.add(s);
        }
    }
    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void OnNoteClick(int position) {
        Intent intent = new Intent(this, SpecificWallet.class);
        intent.putExtra("address", userWalletAddresses.get(position));
        startActivity(intent);
    }
}