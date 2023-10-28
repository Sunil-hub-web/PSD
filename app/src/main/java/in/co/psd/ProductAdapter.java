package in.co.psd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.co.psd.databinding.ViewproductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductModelClass> productModelClasses1;
    ViewproductBinding binding;

    public ProductAdapter(FragmentActivity activity, ArrayList<ProductModelClass> productModelClasses) {

        this.context = activity;
        this.productModelClasses1 = productModelClasses;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ViewproductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        ProductModelClass product = productModelClasses1.get(position);
        holder.viewproductBinding.textPrice1.setText("");
        holder.viewproductBinding.textDailyIncome1.setText("");
        holder.viewproductBinding.textValidityPeriod1.setText("");
        holder.viewproductBinding.textPurchaseLimit1.setText("");
        holder.viewproductBinding.textTotalRevenue1.setText("");



        //Picasso.with(context).load().into(holder.viewproductBinding.productImage);

    }

    @Override
    public int getItemCount() {
        return productModelClasses1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewproductBinding viewproductBinding;
        public ViewHolder(ViewproductBinding itemView) {
            super(itemView.getRoot());
            this.viewproductBinding = itemView;
        }
    }
}
