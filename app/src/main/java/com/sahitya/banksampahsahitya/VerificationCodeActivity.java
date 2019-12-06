package com.sahitya.banksampahsahitya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sahitya.banksampahsahitya.model.VerifikasiModel;
import com.sahitya.banksampahsahitya.rest.service.VerificationService;
import com.sahitya.banksampahsahitya.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationCodeActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = VerificationCodeActivity.class.getSimpleName();
    public static final String KEY_VERIFICATION_CODE = "keyverificationcode";
    public static final String KEY_ID_VERIFICATION = "keyidverification";

    @BindView(R.id.btn_verification_code)
    Button btnVerificationCode;

    @BindView(R.id.edit_text_verification_code)
    TextInputLayout edtVerificationCode;

    private VerificationService mVerificationService;
    private ProgressDialog loading;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        unbinder = ButterKnife.bind(this);

        mVerificationService = VerificationUtils.getVerificationService();

        btnVerificationCode.setOnClickListener(this);
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

                    startActivity(intent);
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
        String verificationCode = edtVerificationCode.getEditText().getText().toString().trim();

        Bundle bundle = getIntent().getExtras();
        String confirmVerification = bundle.getString(KEY_VERIFICATION_CODE);
        int id = bundle.getInt(KEY_ID_VERIFICATION);

        if (!TextUtils.isEmpty(verificationCode)){
            if (verificationCode.equals(confirmVerification)){
                loading = ProgressDialog.show(this, null, "Memvalidasi Kode...", true, false);
                sendVerificationCode(id, verificationCode);
            }else{
                Toast.makeText(VerificationCodeActivity.this, "Kode Verifikasi Tidak Sama", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
