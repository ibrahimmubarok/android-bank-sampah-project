package com.sahitya.banksampahsahitya.presentation.membership.changepassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.presentation.membership.SettingsActivity;
import com.sahitya.banksampahsahitya.ui.customdialog.CustomDialogOne;
import com.sahitya.banksampahsahitya.ui.customdialog.CustomDialogTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar_change_password)
    Toolbar toolbar;
    @BindView(R.id.edit_text_new_change_password)
    EditText edtNewPassword;
    @BindView(R.id.edit_text_konfirmasi_change_password)
    EditText edtKonfirmasiPassword;
    @BindView(R.id.btn_save_change_password)
    Button btnSave;

    private ProgressDialog loading;

    private Unbinder unbinder;

    public static void start(Context context) {
        Intent starter = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_button_back_black);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save_change_password){
            String password = edtNewPassword.getText().toString().trim();
            String confirmPassword = edtKonfirmasiPassword.getText().toString().trim();

            if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){
                if (password.equals(confirmPassword)){
                    showAlert("Apakah kamu yakin ingin mengganti password?", SettingsActivity.class);
                }else{
                    showAlertDataInvalid("Password error", "Konfirmasi password harus sama dengan password baru");
                }
            }else{
                showAlertDataInvalid("Field kosong", "Harap masukkan password baru dan konfirmasi password");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlert(String head, Class<?> cls){
        CustomDialogTwo mCustomDialogTwo = new CustomDialogTwo(ChangePasswordActivity.this, head, cls);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        mCustomDialogTwo.show(mFragmentManager, cls.getSimpleName());
    }

    private void showAlertDataInvalid(String head, String body){
        CustomDialogOne mCustomDialogOne = new CustomDialogOne(head, body);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        mCustomDialogOne.show(mFragmentManager, ChangePasswordActivity.class.getSimpleName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChangePasswordActivity.this, SettingsActivity.class));
        finish();
    }
}
