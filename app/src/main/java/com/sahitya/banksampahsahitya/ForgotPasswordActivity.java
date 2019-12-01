package com.sahitya.banksampahsahitya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_send_email_forgot_password)
    Button btnForgotPassword;
    @BindView(R.id.edit_text_email_forgot_password)
    EditText edtForgotPassword;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        unbinder = ButterKnife.bind(this);

        btnForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
