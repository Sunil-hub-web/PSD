package in.co.psd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import in.co.psd.databinding.MyproductFragmentBinding;
import in.co.psd.databinding.MyteamFragmentBinding;
import in.co.psd.databinding.PersonalFragmentBinding;

public class MyTeamFragment extends Fragment {

    MyteamFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = MyteamFragmentBinding.inflate(getLayoutInflater(),container,false);

        DashBoard.image_Logo.setVisibility(View.GONE);
        DashBoard.welcome_text.setVisibility(View.VISIBLE);
        DashBoard.welcome_text.setText("My Team");

        binding.linteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.lininviateout.setVisibility(View.GONE);
                binding.linteamout.setVisibility(View.VISIBLE);
            }
        });

        binding.lininviateout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.lininviateout.setVisibility(View.VISIBLE);
                binding.linteamout.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }
}
