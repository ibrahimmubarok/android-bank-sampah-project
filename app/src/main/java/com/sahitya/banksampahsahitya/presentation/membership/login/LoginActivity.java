package com.sahitya.banksampahsahitya.presentation.membership.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.sahitya.banksampahsahitya.ForgotPasswordActivity;
import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.RegisterActivity;
import com.sahitya.banksampahsahitya.VerificationCodeActivity;
import com.sahitya.banksampahsahitya.model.LoginModel;
import com.sahitya.banksampahsahitya.rest.service.LoginService;
import com.sahitya.banksampahsahitya.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = LoginActivity.class.getSimpleName();
    public static final String KEY_VERIFICATION_CODE = "keyverificationcode";
    public static final String KEY_ID_VERIFICATION = "keyidverification";

    @BindView(R.id.edit_text_email_login_user)
    TextInputLayout editTextEmail;
    @BindView(R.id.edit_text_password_login_user)
    TextInputLayout editTextPassword;
    @BindView(R.id.btn_masuk_login_user)
    Button btnLogin;
    @BindView(R.id.tv_sign_up_login_user)
    TextView tvSignUp;
    @BindView(R.id.tv_forgot_password_login_user)
    TextView tvForgotPassword;

    private Unbinder unbinder;

    private ProgressDialog loading;
    private LoginService mLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);

        mLoginService = LoginUtils.getLoginService();

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_masuk_login_user :
                String email = editTextEmail.getEditText().getText().toString().trim();
                String password = editTextPassword.getEditText().getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
                    sendLoginPost(email, password);
                }

                return;

            case R.id.tv_sign_up_login_user :
                startActivity(new Intent(this, RegisterActivity.class));
                return;

            case R.id.tv_forgot_password_login_user :
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                return;
        }
    }

    public void sendLoginPost(String email, String password){
        mLoginService.saveLoginPost(email, password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                loading.dismiss();
                String error = response.body().getError();
                if (response.isSuccessful()){
                    Log.d(TAG, response.body().toString());
                    Log.d(TAG, response.body().getKodeVerifikasi());
                    Log.d(TAG, "Sukses");

                    Intent intent = new Intent(getApplicationContext(), VerificationCodeActivity.class);
                    intent.putExtra(KEY_VERIFICATION_CODE, response.body().getKodeVerifikasi());
                    intent.putExtra(KEY_ID_VERIFICATION, response.body().getId());
                    startActivity(intent);
                }else{
                    Log.d(TAG, "Gagal");
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                loading.dismiss();
                Log.d(TAG, "unable to submit post to API");
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
