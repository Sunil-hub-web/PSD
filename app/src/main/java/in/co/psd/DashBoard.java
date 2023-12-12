package in.co.psd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import in.co.psd.databinding.ActivityDashBoardBinding;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityDashBoardBinding binding;

    public static  ImageView image_Logo,image_back;
    public static  TextView welcome_text;
    private Boolean exit = false;

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

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.bottomNavigation.setSelectedItemId(R.id.personal);
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

        PersonalFragment test = (PersonalFragment) getSupportFragmentManager().findFragmentByTag("personalFragment");

        if (test != null && test.isVisible()) {

            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }, 4 * 1000);
            }
        } else {

            super.onBackPressed();

           // text_name.setText("Home Page");
           /* HomePageActivity.fragmentManager.beginTransaction()
                    .replace(R.id.framLayout,new Homepage(),"HomeFragment").addToBackStack(null).commit();*/

           // getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, new Homepage(), "HomeFragment").commit();

        }
    }
}