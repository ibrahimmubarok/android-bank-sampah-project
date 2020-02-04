package com.sahitya.banksampahsahitya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.model.RegistersModel;
import com.sahitya.banksampahsahitya.rest.service.RegisterApiService;
import com.sahitya.banksampahsahitya.tools.DatePickerFragment;
import com.sahitya.banksampahsahitya.tools.SingleChoiceDialogFragment;
import com.sahitya.banksampahsahitya.utils.RegisterApiUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahitya.banksampahsahitya.tools.SingleChoiceDialogFragment.FAKULTAS_TAG;
import static com.sahitya.banksampahsahitya.tools.SingleChoiceDialogFragment.JURUSAN_TAG;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, SingleChoiceDialogFragment.SingleChoiceListener {

    private final String TAG = RegisterActivity.class.getSimpleName();
    private final static String DATE_PICKER_TAG = "DatePicker";

    @BindView(R.id.edit_text_name_register)
    EditText editTextName;
    @BindView(R.id.edit_text_nim_register)
    EditText editTextNim;
    @BindView(R.id.edit_text_fakultas_register)
    EditText edtFakultas;
    @BindView(R.id.edit_text_jurusan_register)
    EditText edtJurusan;
    @BindView(R.id.edit_text_no_hp_register)
    EditText editTextNoHp;
    @BindView(R.id.edit_text_alamat_register)
    EditText editTextAlamat;
    @BindView(R.id.edit_text_ttl_register)
    EditText editTextTtl;
    @BindView(R.id.edit_text_email_register)
    EditText editTextEmail;
    @BindView(R.id.edit_text_password_register)
    EditText editTextPassword;
    @BindView(R.id.edit_text_konfirmasi_password_register)
    EditText editTextKonfirmasiPassword;
    @BindView(R.id.btn_daftar_register)
    Button btnDaftar;
    @BindView(R.id.toolbar_register)
    Toolbar toolbar;

    private Unbinder unbinder;
    private RegisterApiService mRegisterApiService;
    private ProgressDialog loading;

    private String[] listFakultas;
    private String[] listJurusan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        unbinder = ButterKnife.bind(this);

        mRegisterApiService = RegisterApiUtils.getRegisterApiService();

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_button_back_white);
            getSupportActionBar().setTitle(R.string.daftar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listFakultas = getResources().getStringArray(R.array.fakultas);

        edtFakultas.setOnClickListener(this);
        edtJurusan.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);
        editTextTtl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_daftar_register){
            String name = editTextName.getText().toString().trim();
            String nim = editTextNim.getText().toString().trim();
            String noHp = editTextNoHp.getText().toString().trim();
            String alamat = editTextAlamat.getText().toString().trim();
            String ttl = editTextTtl.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String konfirmasiPassword = editTextKonfirmasiPassword.getText().toString().trim();
            String fakultas = edtFakultas.getText().toString().trim();
            String jurusan = edtJurusan.getText().toString().trim();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(nim) && !fakultas.equals("Pilih Fakultas") && !jurusan.equals("Pilih Jurusan") && !TextUtils.isEmpty(noHp) && !TextUtils.isEmpty(alamat) && !TextUtils.isEmpty(ttl) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(konfirmasiPassword)){
                if (password.equals(konfirmasiPassword)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Apakah anda yakin data yang dimasukkan benar?");
                    builder.setTitle("Register");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Iya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    loading = ProgressDialog.show(RegisterActivity.this, null, "Harap Tunggu...", true, false);
                                    sendRegisterPost(name, email, password, konfirmasiPassword, nim, fakultas, jurusan, noHp, alamat, ttl);
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
            }else if (fakultas.equals("Pilih Fakultas") || jurusan.equals("Pilih Jurusan")){
                showDialog("Fakultas atau listJurusan harus dipilih", "Field Kosong");
            }else{
                showDialog("Field Tidak Boleh Ada Yang Kosong", "Field Kosong");
            }
        }else if (view.getId() == R.id.edit_text_ttl_register){
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
        }else if (view.getId() == R.id.edit_text_fakultas_register){
            DialogFragment fakultasDialog = new SingleChoiceDialogFragment(listFakultas);
            fakultasDialog.setCancelable(true);
            fakultasDialog.show(getSupportFragmentManager(), FAKULTAS_TAG);
        }else if (view.getId() == R.id.edit_text_jurusan_register){
            String jurusan = edtFakultas.getText().toString().trim();
            if (!TextUtils.isEmpty(jurusan)){
                DialogFragment jurusanDialog = new SingleChoiceDialogFragment(listJurusan);
                jurusanDialog.setCancelable(true);
                jurusanDialog.show(getSupportFragmentManager(), JURUSAN_TAG);
            }else{
                Toast.makeText(this, "Pilih fakultas terlebih dahulu", Toast.LENGTH_SHORT).show();
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

    private void sendRegisterPost(String name, String email, String password, String konfirmasiPassword, String nim, String fakultas, String jurusan, String noHp, String alamat, String ttl ) {
        mRegisterApiService.saveRegisterPost(name, email, password, konfirmasiPassword, nim, fakultas, jurusan, noHp, alamat, ttl).enqueue(new Callback<RegistersModel>() {
            @Override
            public void onResponse(Call<RegistersModel> call, Response<RegistersModel> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    Log.d(TAG, response.body().toString());
                    startActivity(new Intent(getApplicationContext(), RegisterSuccesfullyActivity.class));
                    finish();
                    Log.d(TAG, "Sukses");
                }else{
                    if (response.code() == 500){
                        showDialog("Email sudah terdaftar harap ganti email yang lain", "Register Gagal");
                        loading.dismiss();
                    }else {
                        showDialog("Anda tidak berhasil mendaftar", "Register Gagal");
                        loading.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistersModel> call, Throwable t) {
                loading.dismiss();
                Log.d(TAG, "Unable to submit post to API");
                Log.i(TAG, t.toString());
                showDialog("Periksa kembali koneksi internet anda", "Tidak Ada Koneksi");
            }
        });
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        editTextTtl.setText(dateFormat.format(calendar.getTime()));
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

    @Override
    public void onPositiveFakultasClicked(String[] list, int position) {
        edtFakultas.setText(list[position]);
        edtJurusan.setText("");

        switch (position){
            case 0 :
                listJurusan = getResources().getStringArray(R.array.jurusan_fitk);
                break;

            case 1 :
                listJurusan = getResources().getStringArray(R.array.jurusan_adab_humaniora);
                break;

            case 2 :
                listJurusan = getResources().getStringArray(R.array.jurusan_ushuluddin);
                break;

            case 3 :
                listJurusan = getResources().getStringArray(R.array.jurusan_syariah_hukum);
                break;

            case 4 :
                listJurusan = getResources().getStringArray(R.array.jurusan_dakwah_komunikasi);
                break;

            case 5 :
                listJurusan = getResources().getStringArray(R.array.jurusan_dirasat);
                break;

            case 6 :
                listJurusan = getResources().getStringArray(R.array.jurusan_psikologi);
                break;

            case 7 :
                listJurusan = getResources().getStringArray(R.array.jurusan_ekonomi);
                break;

            case 8 :
                listJurusan = getResources().getStringArray(R.array.jurusan_sains_teknologi);
                break;

            case 9 :
                listJurusan = getResources().getStringArray(R.array.jurusan_ilmu_kesehatan);
                break;

            case 10 :
                listJurusan = getResources().getStringArray(R.array.jurusan_isip);
                break;

            case 11 :
                listJurusan = getResources().getStringArray(R.array.jurusan_kedokteran);
                break;
        }
    }

    @Override
    public void onPositiveJurusanClicked(String[] list, int position) {
        edtJurusan.setText(list[position]);
    }
}