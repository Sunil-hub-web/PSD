package in.co.psd;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.co.psd.databinding.ViewmyproductBinding;
import in.co.psd.databinding.ViewproductBinding;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ViewHolder> {

    ViewmyproductBinding binding;

    Context context;
    ArrayList<ProductModelClass> productModelClasses1;
    ViewDialog progressbar;
    SessionManager sessionManager;
    Activity activity;

    public MyProductAdapter(FragmentActivity activity, ArrayList<ProductModelClass> productModelClasses) {

        this.context = activity;
        this.activity = activity;
        this.productModelClasses1 = productModelClasses;
    }

    @NonNull
    @Override
    public MyProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ViewmyproductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductAdapter.ViewHolder holder, int position) {

        progressbar = new ViewDialog(activity);
        sessionManager = new SessionManager(context);

        ProductModelClass product = productModelClasses1.get(position);

        Double d_daliyincome = Double.valueOf(product.getProduct_owner());
        Double d_validateincome = Double.valueOf(product.getProduct_lockDays());
        Double total_revinue = d_daliyincome * d_validateincome;
        String str_totalrevinue = String.valueOf(total_revinue);

        holder.viewproductBinding.textPrice1.setText(product.getProduct_amt());
        holder.viewproductBinding.textDailyIncome1.setText("Rs "+product.getProduct_owner());
        holder.viewproductBinding.textValidityPeriod1.setText(product.getProduct_lockDays()+" / Days");
        holder.viewproductBinding.textPurchaseLimit1.setText(product.getProduct_title());
        holder.viewproductBinding.textTotalRevenue1.setText("Rs "+str_totalrevinue);

        String image = "https://projects.conjuror.in/investor/"+product.getProduct_banner();
        Picasso.with(context).load(image).into(holder.viewproductBinding.productImage);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewmyproductBinding viewproductBinding;
        public ViewHolder(ViewmyproductBinding itemView) {
            super(itemView.getRoot());
            this.viewproductBinding = itemView;
        }
    }
}
