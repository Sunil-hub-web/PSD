package in.co.psd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.psd.databinding.TransactionlistBinding;
import in.co.psd.databinding.ViewmyproductBinding;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    TransactionlistBinding binding;
    ArrayList<TransactionModel> transactionListData;
    Context context;
    public TransactionAdapter(FragmentActivity activity, ArrayList<TransactionModel> transactionAll) {

        this.transactionListData = transactionAll;
        this.context = activity;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TransactionlistBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new TransactionAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {

        TransactionModel trans = transactionListData.get(position);
        holder.binding.textTransactionAmount.setText("Rs  "+trans.getTransaction_amount());
        holder.binding.textTransactionDate.setText(trans.getTransaction_date());
        holder.binding.textTransactionPurpose.setText(trans.getTransaction_purpose());
        holder.binding.textTransactionType.setText(trans.getTransaction_type());
        holder.binding.textTransactionUser.setText(trans.getTransaction_user());

        if (trans.getTransaction_stat().equals("0")){
            holder.binding.textTransactionStatues.setText("Pending");
        }else if(trans.getTransaction_stat().equals("1")){
            holder.binding.textTransactionStatues.setText("Approve");
        }else{
            holder.binding.textTransactionStatues.setText("Reject");
        }

    }

    @Override
    public int getItemCount() {
        return transactionListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TransactionlistBinding binding;
        public ViewHolder(@NonNull TransactionlistBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
