package com.sahitya.banksampahsahitya.presentation.helpactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sahitya.banksampahsahitya.R;

public class KontakActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, KontakActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak);
    }
}
