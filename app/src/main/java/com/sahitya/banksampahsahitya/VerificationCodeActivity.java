package com.sahitya.banksampahsahitya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sahitya.banksampahsahitya.model.VerifikasiModel;
import com.sahitya.banksampahsahitya.presentation.membership.login.LoginActivity;
import com.sahitya.banksampahsahitya.rest.service.VerificationService;
import com.sahitya.banksampahsahitya.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationCodeActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = VerificationCodeActivity.class.getSimpleName();
    public static final String KEY_VERIFICATION_CODE = "keyverificationcode";
    public static final String KEY_ID_VERIFICATION = "keyidverification";

    @BindView(R.id.btn_verification_code)
    Button btnVerificationCode;
    @BindView(R.id.edit_text_verification_code)
    TextInputLayout edtVerificationCode;
    @BindView(R.id.text_kode_verifikasi)
    TextInputEditText textInputKodeVerifikasi;
    @BindView(R.id.img_back_arrow_verification)
    ImageView imgBack;

    private VerificationService mVerificationService;
    private ProgressDialog loading;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        unbinder = ButterKnife.bind(this);

        mVerificationService = VerificationUtils.getVerificationService();

        textInputKodeVerifikasi.addTextChangedListener(this);
        btnVerificationCode.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void sendVerificationCode(int id, String verificationCode) {
        mVerificationService.saveVerifikasiPost(id, verificationCode).enqueue(new Callback<VerifikasiModel>() {
            @Override
            public void onResponse(Call<VerifikasiModel> call, Response<VerifikasiModel> response) {
                loading.dismiss();
                if (response.isSuccessful()){
                    Log.d(TAG, response.body().toString());
                    Toast.makeText(VerificationCodeActivity.this, "Sukses!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(MainActivity.ID_PROFILE, id);
                    startActivity(intent);
                    finish();
                }else{
                    Log.d(TAG, "gagal");
                }
            }

            @Override
            public void onFailure(Call<VerifikasiModel> call, Throwable t) {
                loading.dismiss();
                Log.d(TAG,"Unable Post To API");
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_verification_code) {
            String verificationCode = edtVerificationCode.getEditText().getText().toString().trim();

            Bundle bundle = getIntent().getExtras();
            String confirmVerification = bundle.getString(KEY_VERIFICATION_CODE);
            int id = bundle.getInt(KEY_ID_VERIFICATION);

            if (!TextUtils.isEmpty(verificationCode)) {
                if (verificationCode.equals(confirmVerification)) {
                    loading = ProgressDialog.show(this, null, "Memvalidasi Kode...", true, false);
                    sendVerificationCode(id, verificationCode);
                } else {
                    Toast.makeText(VerificationCodeActivity.this, "Kode Verifikasi Tidak Sama", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (view.getId() == R.id.img_back_arrow_verification){
            startActivity(new Intent(VerificationCodeActivity.this, LoginActivity.class));
            finish();
        }
    }

    boolean validasiField(boolean b){
        if (b){
            btnVerificationCode.setEnabled(true);
            btnVerificationCode.setBackgroundResource(R.drawable.bg_rounded_green);
        }else{
            btnVerificationCode.setEnabled(false);
            btnVerificationCode.setBackgroundResource(R.drawable.bg_rounded_green_false);
        }
        return b;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() > 0){
            validasiField(true);
        }else{
            validasiField(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
