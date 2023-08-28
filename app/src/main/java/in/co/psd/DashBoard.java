package in.co.psd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import in.co.psd.databinding.ActivityDashBoardBinding;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityDashBoardBinding binding;

    public static  ImageView image_Logo,image_back;
    public static  TextView welcome_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dash_board);

        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image_Logo = findViewById(R.id.image_Logo);
        image_back = findViewById(R.id.image_back);
        welcome_text = findViewById(R.id.welcome_text);

        image_Logo.setVisibility(View.VISIBLE);
        welcome_text.setVisibility(View.GONE);
        welcome_text.setText("");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PersonalFragment personalFragment = new PersonalFragment();
        ft.replace(R.id.framLayout, personalFragment,"personalFragment");
        ft.addToBackStack(null);
        ft.commit();

        binding.bottomNavigation.setSelectedItemId(R.id.home);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()) {

                    case R.id.product:

                        selectedFragment = new ProductFragment();

                        //loc.setVisibility(View.GONE);
                        //logo.setVisibility(View.GONE);
                        // search.setVisibility(View.GONE);
                      //  text_name.setTextSize(18);
                      //  text_name.setText("My Order Details");
                        //text_address.setVisibility(View.GONE);

                        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, selectedFragment).addToBackStack(null).commit();

                        break;

                    case R.id.personal:

                        selectedFragment = new PersonalFragment();

                        //loc.setVisibility(View.GONE);
                        //logo.setVisibility(View.GONE);
                        // search.setVisibility(View.GONE);
                        //  text_name.setTextSize(18);
                        //  text_name.setText("My Order Details");
                        //text_address.setVisibility(View.GONE);

                        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, selectedFragment).addToBackStack(null).commit();

                        break;

                    case R.id.team:

                        selectedFragment = new MyTeamFragment();

                        //loc.setVisibility(View.GONE);
                        //logo.setVisibility(View.GONE);
                        // search.setVisibility(View.GONE);
                        //  text_name.setTextSize(18);
                        //  text_name.setText("My Order Details");
                        //text_address.setVisibility(View.GONE);

                        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, selectedFragment).addToBackStack(null).commit();

                        break;





                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,selectedFragment).commit();

                return true;
            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}