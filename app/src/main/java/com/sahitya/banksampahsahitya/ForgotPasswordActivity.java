package com.sahitya.banksampahsahitya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.presentation.membership.login.LoginActivity;
import com.sahitya.banksampahsahitya.rest.service.ForgotPasswordService;
import com.sahitya.banksampahsahitya.utils.ForgotPasswordUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();

    @BindView(R.id.btn_send_email_forgot_password)
    Button btnForgotPassword;
    @BindView(R.id.edit_text_email_forgot_password)
    EditText edtForgotPassword;
    @BindView(R.id.img_back_arrow)
    ImageView imgBack;

    private ForgotPasswordService mForgotPasswordService;
    private ProgressDialog loading;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        unbinder = ButterKnife.bind(this);

        mForgotPasswordService = ForgotPasswordUtils.getForgotPasswordService();

        btnForgotPassword.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        edtForgotPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0){
                    validasiField(true);
                }else{
                    validasiField(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send_email_forgot_password) {
            String email = edtForgotPassword.getText().toString().trim();
            loading = ProgressDialog.show(ForgotPasswordActivity.this, null, "Mengirim Email...", true, false);
            sendForgotPassword(email);
        }else if (view.getId() == R.id.img_back_arrow){
            onBackPressed();
        }
    }

    private void sendForgotPassword(String email) {
        mForgotPasswordService.saveForgotPasswordPost(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.dismiss();
                Log.d("Email Terkirim", response.body().toString());
                Toast.makeText(ForgotPasswordActivity.this, "Email Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Log.d(TAG,"Unable Post To API");
                Log.e(TAG, t.getMessage());
                Toast.makeText(ForgotPasswordActivity.this, "Koneksi Internet Terputus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean validasiField(boolean b){
        if (b){
            btnForgotPassword.setEnabled(true);
            btnForgotPassword.setBackgroundResource(R.drawable.bg_rounded_green);
        }else{
            btnForgotPassword.setEnabled(false);
            btnForgotPassword.setBackgroundResource(R.drawable.bg_rounded_green_false);
        }
        return b;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
