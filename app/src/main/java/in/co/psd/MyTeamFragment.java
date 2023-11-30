package in.co.psd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

        binding.textInvite.setBackgroundResource(R.drawable.backgroundcolor);
        binding.textAssets.setBackgroundResource(R.drawable.layoutback);

        binding.linteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.lininviateout.setVisibility(View.GONE);
                binding.linteamout.setVisibility(View.VISIBLE);
            }
        });
        binding.lininvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.lininviateout.setVisibility(View.VISIBLE);
                binding.linteamout.setVisibility(View.GONE);
            }
        });
        binding.textInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // binding.textAccount.setBackgroundResource(R.drawable.backgroundcolor);
                binding.textInvite.setBackgroundResource(R.drawable.backgroundcolor);
                binding.textAssets.setBackgroundResource(R.drawable.layoutback);

                binding.textInvite.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textAssets.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            }
        });
        binding.textAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textInvite.setBackgroundResource(R.drawable.layoutback);
                binding.textAssets.setBackgroundResource(R.drawable.backgroundcolor);

                binding.textAssets.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textInvite.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            }
        });

        binding.textLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textLevel1.setBackgroundResource(R.drawable.backgroundcolor);
                binding.textLevel2.setBackgroundResource(R.drawable.layoutback);
                binding.textLevel3.setBackgroundResource(R.drawable.layoutback);

                binding.textLevel1.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textLevel2.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textLevel3.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

            }
        });

        binding.textLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textLevel1.setBackgroundResource(R.drawable.layoutback);
                binding.textLevel2.setBackgroundResource(R.drawable.backgroundcolor);
                binding.textLevel3.setBackgroundResource(R.drawable.layoutback);

                binding.textLevel1.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textLevel2.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                binding.textLevel3.setTextColor(ContextCompat.getColor(getContext(),R.color.black));

            }
        });

        binding.textLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textLevel1.setBackgroundResource(R.drawable.layoutback);
                binding.textLevel2.setBackgroundResource(R.drawable.layoutback);
                binding.textLevel3.setBackgroundResource(R.drawable.backgroundcolor);

                binding.textLevel1.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textLevel2.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                binding.textLevel3.setTextColor(ContextCompat.getColor(getContext(),R.color.white));

            }
        });

        return binding.getRoot();
    }
}
