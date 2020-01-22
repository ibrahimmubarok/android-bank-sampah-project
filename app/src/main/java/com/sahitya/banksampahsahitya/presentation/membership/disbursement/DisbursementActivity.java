package com.sahitya.banksampahsahitya.presentation.membership.disbursement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DisbursementActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_disbursement)
    Toolbar toolbar;
    @BindView(R.id.tv_jumlah_pencairan_disbursement)
    TextView tvPencairan;
    @BindView(R.id.progress_bar_disbursement)
    ProgressBar progressBar;
    @BindView(R.id.layout_disbursement)
    LinearLayout linearLayout;

    private Unbinder unbinder;

    public static void start(Context context) {
        Intent starter = new Intent(context, DisbursementActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.label_submenu_profile);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
