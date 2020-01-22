package com.sahitya.banksampahsahitya.presentation.membership.changepassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.sahitya.banksampahsahitya.RegisterActivity;
import com.sahitya.banksampahsahitya.presentation.membership.editprofile.EditProfileActivity;

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
            getSupportActionBar().setTitle(R.string.ganti_password);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Apakah anda yakin data yang dimasukkan benar?");
                    builder.setTitle("Validasi Password");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Iya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    loading = ProgressDialog.show(ChangePasswordActivity.this, null, "Harap Tunggu...", true, false);

                                }
                            }
                    );

                    builder.setNegativeButton(
                            "Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }
                    );

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    showDialog("Password tidak sama", "Password error");
                }
            }else{
                showDialog("Harap masukkan password baru dan konfirmasi password", "Field Kosong");
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

    private void showDialog(String message, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
