package com.sahitya.banksampahsahitya.presentation.membership.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sahitya.banksampahsahitya.ForgotPasswordActivity;
import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.RegisterActivity;
import com.sahitya.banksampahsahitya.VerificationCodeActivity;
import com.sahitya.banksampahsahitya.model.LoginModel;
import com.sahitya.banksampahsahitya.model.VerifikasiModel;
import com.sahitya.banksampahsahitya.rest.service.LoginService;
import com.sahitya.banksampahsahitya.rest.service.VerificationService;
import com.sahitya.banksampahsahitya.ui.customdialog.CustomDialogOne;
import com.sahitya.banksampahsahitya.utils.LoginUtils;
import com.sahitya.banksampahsahitya.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahitya.banksampahsahitya.MainActivity.accountList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{

    private final String TAG = LoginActivity.class.getSimpleName();

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
    @BindView(R.id.text_email)
    TextInputEditText textInputEmail;
    @BindView(R.id.text_password)
    TextInputEditText textInputPassword;

    private Unbinder unbinder;

    private ProgressDialog loading;

    private LoginService mLoginService;
    private VerificationService mVerificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);

        mLoginService = LoginUtils.getLoginService();
        mVerificationService = VerificationUtils.getVerificationService();

        textInputEmail.setText("yantootnay@gmail.com");
        textInputPassword.setText("iniikhsan");

        textInputEmail.addTextChangedListener(this);
        textInputPassword.addTextChangedListener(this);

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    boolean validasiField(boolean b){
        if (b){
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.bg_rounded_green);
        }else{
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.bg_rounded_green_false);
        }
        return b;
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

    private void sendLoginPost(String email, String password){
        mLoginService.saveLoginPost(email, password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                loading.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getId() != 0){
                        if (response.body().getVerified() == 0) {
                            Log.d(TAG, response.body().toString());
                            Log.d(TAG, response.body().getKodeVerifikasi());
                            Log.d(TAG, "Sukses");

                            Intent intent = new Intent(LoginActivity.this, VerificationCodeActivity.class);
                            intent.putExtra(VerificationCodeActivity.KEY_VERIFICATION_CODE, response.body().getKodeVerifikasi());
                            intent.putExtra(VerificationCodeActivity.KEY_ID_VERIFICATION, response.body().getId());
                            startActivity(intent);
                            finish();
                        }else{
                            Log.d(TAG, response.body().toString());
                            Log.d(TAG, response.body().getKodeVerifikasi());
                            Log.d(TAG, "Sukses");

                            sendAccountVerify(response.body().getId(), response.body().getKodeVerifikasi());
                        }
                    }else{
                        showAlert(getResources().getString(R.string.coba_lagi_ya), getResources().getString(R.string.email_atau_password_yang_kamu_masukkan_belum_tepat));
                    }
                }else{
                    Log.d(TAG, "Gagal");
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d(TAG, "unable to submit post to API");
                Log.d(TAG, t.getMessage());
                showAlert(getResources().getString(R.string.connection_lost), getResources().getString(R.string.koneksi_dialog));
                loading.dismiss();
            }
        });
    }

    private void sendAccountVerify(int id, String kode){
        mVerificationService.saveVerifikasiPost(id, kode).enqueue(new Callback<VerifikasiModel>() {
            @Override
            public void onResponse(Call<VerifikasiModel> call, Response<VerifikasiModel> response) {
                loading.dismiss();
                if (response.isSuccessful()){
                    Log.d(TAG, response.body().toString());
                    Toast.makeText(LoginActivity.this, "Sukses!", Toast.LENGTH_SHORT).show();

                    String name = response.body().getName();
                    String email = response.body().getEmail();
                    String nim = response.body().getNim();
                    String fakultas = response.body().getFakultas();
                    String jurusan = response.body().getJurusan();
                    String hp = response.body().getHp();
                    String alamat = response.body().getAlamat();
                    String ttl = response.body().getTtl();
                    String foto = response.body().getFoto();

                    accountList.add(new VerifikasiModel(id, kode, name, email, nim, fakultas, jurusan, hp, alamat, ttl, foto));

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(MainActivity.ID_PROFILE, id);
                    intent.putExtra(MainActivity.NAMA_NASABAH, response.body().getName());
                    startActivity(intent);
                    finish();
                }else{
                    Log.d(TAG, "gagal");
                }
            }

            @Override
            public void onFailure(Call<VerifikasiModel> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                loading.dismiss();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String inputPassword = textInputPassword.getText().toString().trim();
        String inputEmail = textInputEmail.getText().toString().trim();

        if (!inputPassword.isEmpty() && !inputEmail.isEmpty()){
            validasiField(true);
        }else{
            validasiField(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void showAlert(String head, String body){
        CustomDialogOne mCustomDialogOne = new CustomDialogOne(head, body);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        mCustomDialogOne.show(mFragmentManager, CustomDialogOne.class.getSimpleName());
    }
}
