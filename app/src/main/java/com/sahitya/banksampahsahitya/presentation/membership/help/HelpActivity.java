package com.sahitya.banksampahsahitya.presentation.membership.help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.presentation.adapter.HelpAdapter;
import com.sahitya.banksampahsahitya.presentation.helpactivity.AppInfoActivity;
import com.sahitya.banksampahsahitya.presentation.helpactivity.FaqActivity;
import com.sahitya.banksampahsahitya.presentation.helpactivity.KontakActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HelpActivity extends AppCompatActivity{

    @BindView(R.id.toolbar_help)
    Toolbar toolbar;
    @BindView(R.id.rv_help)
    RecyclerView rvHelp;

    private Unbinder unbinder;

    public static void start(Context context) {
        Intent starter = new Intent(context, HelpActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        
        unbinder = ButterKnife.bind(this);
        
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_button_back_black);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        
        setListMenuProfile();
    }

    private void setListMenuProfile() {
        ArrayList<String> helpList = new ArrayList<>();

        helpList.add(getResources().getString(R.string.app_info));
        helpList.add(getResources().getString(R.string.f_a_q));
        helpList.add(getResources().getString(R.string.kontak_kami));

        HelpAdapter helpAdapter = new HelpAdapter(HelpActivity.this, helpList);

        rvHelp.setLayoutManager(new LinearLayoutManager(HelpActivity.this, LinearLayoutManager.VERTICAL, false));
        rvHelp.setAdapter(helpAdapter);

        helpAdapter.setOnHelpClickListener(new HelpAdapter.OnHelpClickListener() {
            @Override
            public void onHelpClicked(View view, int position) {
                switch (position){
                    case 0 :
                        AppInfoActivity.start(HelpActivity.this);
                        break;

                    case 1 :
                        FaqActivity.start(HelpActivity.this);
                        break;

                    case 2 :
                        KontakActivity.start(HelpActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
