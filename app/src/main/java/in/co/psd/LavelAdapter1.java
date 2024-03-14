package in.co.psd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LavelAdapter1 extends RecyclerView.Adapter<LavelAdapter1.ViewHolder> {

    Context context;
    ArrayList<LavelModelClass1> lavel_Model1;
    public LavelAdapter1(FragmentActivity activity, ArrayList<LavelModelClass1> lavelModel1) {

        this.context = activity;
        this.lavel_Model1 = lavelModel1;
    }

    @NonNull
    @Override
    public LavelAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leveladapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LavelAdapter1.ViewHolder holder, int position) {

        LavelModelClass1 level = lavel_Model1.get(position);

        holder.text_MobileNo.setText(level.user_mobile);
        holder.text_UserName.setText(level.user_name);

    }

    @Override
    public int getItemCount() {
        return lavel_Model1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_UserName,text_MobileNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_UserName = itemView.findViewById(R.id.text_UserName);
            text_MobileNo = itemView.findViewById(R.id.text_MobileNo);
        }
    }
}
