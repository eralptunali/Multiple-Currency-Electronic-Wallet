package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WalletRecyclerAdapter extends RecyclerView.Adapter<WalletRecyclerAdapter.MyViewHolder> {

    private ArrayList<String> walletAddresses;
    private onNoteListener mOnNoteListener;
    public WalletRecyclerAdapter(ArrayList<String> walletAddresses, onNoteListener onNoteListener) {
        this.walletAddresses = walletAddresses;
        this.mOnNoteListener= onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_single_item, parent, false);
        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.wA.setText(walletAddresses.get(position));
    }

    @Override
    public int getItemCount() {
        return walletAddresses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView wA;
        onNoteListener onNoteListener;
        public MyViewHolder(@NonNull View itemView, onNoteListener onNoteListener){
            super(itemView);
            wA = itemView.findViewById(R.id.textView11);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }
    public interface onNoteListener{
        void OnNoteClick(int position);
    }
}
