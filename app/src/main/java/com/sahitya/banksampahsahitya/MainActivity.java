package com.sahitya.banksampahsahitya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sahitya.banksampahsahitya.model.VerifikasiModel;
import com.sahitya.banksampahsahitya.presentation.membership.SettingsActivity;
import com.sahitya.banksampahsahitya.ui.InfoSampahFragment;
import com.sahitya.banksampahsahitya.ui.LaporanFragment;
import com.sahitya.banksampahsahitya.ui.PeringkatFragment;
import com.sahitya.banksampahsahitya.ui.ProfileFragment;
import com.sahitya.banksampahsahitya.ui.TabunganFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView navigationView;

    private Unbinder unbinder;

    public static int idUser;
    public static String namaNasabah;
    public static String ID_PROFILE = "id_profile";
    public static String NAMA_NASABAH = "nama_nasabah";
    public static ArrayList<VerifikasiModel> accountList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        idUser = bundle.getInt(ID_PROFILE);
        namaNasabah = bundle.getString(NAMA_NASABAH);

        loadFragment(new TabunganFragment());

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        loadFragment(new TabunganFragment());
                        return true;

                    case R.id.navigation_garbage_info :
                        loadFragment(new InfoSampahFragment());
                        return true;

                    case R.id.navigation_ranking :
                        loadFragment(new PeringkatFragment());
                        return true;

                    case R.id.navigation_report :
                        loadFragment(new LaporanFragment());
                        return true;

                    case R.id.navigation_profile :
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        finish();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void loadFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, fragment);
        fragmentTransaction.commit();
    }
}
