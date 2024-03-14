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

public class LevelAdapter2 extends RecyclerView.Adapter<LevelAdapter2.ViewHolder> {

    Context context;
    ArrayList<LavelModelClass2> lavel_Model2;
    public LevelAdapter2(FragmentActivity activity, ArrayList<LavelModelClass2> lavelModel2) {

        this.context = activity;
        this.lavel_Model2 = lavelModel2;
    }

    @NonNull
    @Override
    public LevelAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leveladapter,parent,false);
        return new LevelAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter2.ViewHolder holder, int position) {

        LavelModelClass2 level = lavel_Model2.get(position);

        holder.text_MobileNo.setText(level.user_mobile);
        holder.text_UserName.setText(level.user_name);
    }

    @Override
    public int getItemCount() {
        return lavel_Model2.size();
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
