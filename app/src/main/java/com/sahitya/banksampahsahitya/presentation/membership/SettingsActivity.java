package com.sahitya.banksampahsahitya.presentation.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.presentation.adapter.SettingAdapter;
import com.sahitya.banksampahsahitya.presentation.helpactivity.AppInfoActivity;
import com.sahitya.banksampahsahitya.presentation.helpactivity.KontakActivity;
import com.sahitya.banksampahsahitya.presentation.membership.changepassword.ChangePasswordActivity;
import com.sahitya.banksampahsahitya.presentation.membership.login.LoginActivity;
import com.sahitya.banksampahsahitya.ui.customdialog.CustomDialogTwo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.rv_pengaturan)
    RecyclerView rvPengaturan;
    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Unbinder unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_button_back_black);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        setListSetting();

    }

    private void setListSetting(){
        ArrayList<String> settingList = new ArrayList<>();

        settingList.add(getResources().getString(R.string.app_info));
        settingList.add(getResources().getString(R.string.kontak_kami));
        settingList.add(getResources().getString(R.string.ganti_password));
        settingList.add(getResources().getString(R.string.label_logout));

        SettingAdapter settingAdapter = new SettingAdapter(SettingsActivity.this, settingList);

        rvPengaturan.setLayoutManager(new LinearLayoutManager(SettingsActivity.this, LinearLayoutManager.VERTICAL, false));
        rvPengaturan.setAdapter(settingAdapter);

        settingAdapter.setOnSettingClickListener(new SettingAdapter.OnSettingClickListener() {
            @Override
            public void onSettingClicked(View view, int position) {
                switch (position){
                    case 0 :
                        AppInfoActivity.start(SettingsActivity.this);
                        break;

                    case 1 :
                        KontakActivity.start(SettingsActivity.this);
                        break;

                    case 2 :
                        ChangePasswordActivity.start(SettingsActivity.this);
                        finish();
                        break;

                    case 3:
                        showAlert("Apakah kamu yakin ingin keluar?", LoginActivity.class);
                        break;
                }
            }
        });
    }

    private void showAlert(String head, Class<?> cls){
        CustomDialogTwo mCustomDialogTwo = new CustomDialogTwo(SettingsActivity.this, head, cls);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        mCustomDialogTwo.show(mFragmentManager, cls.getSimpleName());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.ID_PROFILE, MainActivity.idUser);
        intent.putExtra(MainActivity.NAMA_NASABAH, MainActivity.namaNasabah);
        startActivity(intent);
        finish();
    }
}
