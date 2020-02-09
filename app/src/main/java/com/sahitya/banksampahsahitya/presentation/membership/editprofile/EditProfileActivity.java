package com.sahitya.banksampahsahitya.presentation.membership.editprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.tools.DatePickerFragment;
import com.sahitya.banksampahsahitya.tools.SingleChoiceDialogFragment;
import com.sahitya.banksampahsahitya.ui.customdialog.CustomDialogTwo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.sahitya.banksampahsahitya.MainActivity.accountList;
import static com.sahitya.banksampahsahitya.tools.SingleChoiceDialogFragment.FAKULTAS_TAG;
import static com.sahitya.banksampahsahitya.tools.SingleChoiceDialogFragment.JURUSAN_TAG;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, SingleChoiceDialogFragment.SingleChoiceListener {

    private final static String DATE_PICKER_TAG = "DatePicker";

    @BindView(R.id.toolbar_edit_profile)
    Toolbar toolbar;
    @BindView(R.id.edit_text_name_edit_profile)
    EditText edtName;
    @BindView(R.id.edit_text_nim_edit_profile)
    EditText edtNim;
    @BindView(R.id.edit_text_no_hp_edit_profile)
    EditText edtNoHp;
    @BindView(R.id.edit_text_alamat_edit_profile)
    EditText edtAlamat;
    @BindView(R.id.edit_text_ttl_edit_profile)
    EditText edtTtl;
    @BindView(R.id.edit_text_email_edit_profile)
    EditText edtEmail;
    @BindView(R.id.btn_simpan_edit_profile)
    Button btnSimpan;
    @BindView(R.id.edit_text_fakultas_edit_profile)
    EditText edtFakultas;
    @BindView(R.id.edit_text_jurusan_edit_profile)
    EditText edtJurusan;
    @BindView(R.id.img_edit_profile)
    CircleImageView imgProfile;
    @BindView(R.id.tv_ubah_foto_profile)
    TextView tvUbah;

    private Unbinder unbinder;

    private String[] listFakultas;
    private String[] listJurusan;

    public static void start(Context context) {
        Intent starter = new Intent(context, EditProfileActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_button_back_black);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        listFakultas = getResources().getStringArray(R.array.fakultas);

        if (!accountList.isEmpty()){
            edtName.setText(accountList.get(0).getName());
            edtNim.setText(accountList.get(0).getNim());
            edtNoHp.setText(accountList.get(0).getHp());
            edtAlamat.setText(accountList.get(0).getAlamat());
            edtTtl.setText(accountList.get(0).getTtl());
            edtEmail.setText(accountList.get(0).getEmail());
            edtFakultas.setText(accountList.get(0).getFakultas());
            edtJurusan.setText(accountList.get(0).getJurusan());
            Glide.with(this)
                    .load(accountList.get(0).getFoto())
                    .into(imgProfile);
        }

        Log.d("isiii", edtFakultas.getText().toString().trim());

        if (!edtFakultas.getText().toString().isEmpty()){
            for (int i=0; i<listFakultas.length; i++){
                if (listFakultas[i].equals(edtFakultas.getText().toString().trim())){
                    Log.d("checckk", listFakultas[i]);
                    checkJurusan(i);
                }
            }
        }

        btnSimpan.setOnClickListener(this);
        edtTtl.setOnClickListener(this);
        tvUbah.setOnClickListener(this);
        edtFakultas.setOnClickListener(this);
        edtJurusan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_text_ttl_edit_profile :
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;

            case R.id.edit_text_fakultas_edit_profile :
                DialogFragment fakultasDialog = new SingleChoiceDialogFragment(listFakultas);
                fakultasDialog.setCancelable(true);
                fakultasDialog.show(getSupportFragmentManager(), FAKULTAS_TAG);
                break;

            case R.id.edit_text_jurusan_edit_profile :
                String jurusan = edtFakultas.getText().toString().trim();
                if (!TextUtils.isEmpty(jurusan)){
                    DialogFragment jurusanDialog = new SingleChoiceDialogFragment(listJurusan);
                    jurusanDialog.setCancelable(true);
                    jurusanDialog.show(getSupportFragmentManager(), JURUSAN_TAG);
                }else{
                    Toast.makeText(this, "Pilih fakultas terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_ubah_foto_profile :
                Toast.makeText(this, "Ubah Foto", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_simpan_edit_profile :
                showAlert(getResources().getString(R.string.label_edit_profile_dialog), EditProfileActivity.class);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        edtTtl.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onPositiveFakultasClicked(String[] list, int position) {
        edtFakultas.setText(list[position]);
        edtJurusan.setText("");

        checkJurusan(position);
    }

    @Override
    public void onPositiveJurusanClicked(String[] list, int position) {
        edtJurusan.setText(list[position]);
    }

    private void checkJurusan(int position) {
        switch (position) {
            case 0:
                listJurusan = getResources().getStringArray(R.array.jurusan_fitk);
                break;

            case 1:
                listJurusan = getResources().getStringArray(R.array.jurusan_adab_humaniora);
                break;

            case 2:
                listJurusan = getResources().getStringArray(R.array.jurusan_ushuluddin);
                break;

            case 3:
                listJurusan = getResources().getStringArray(R.array.jurusan_syariah_hukum);
                break;

            case 4:
                listJurusan = getResources().getStringArray(R.array.jurusan_dakwah_komunikasi);
                break;

            case 5:
                listJurusan = getResources().getStringArray(R.array.jurusan_dirasat);
                break;

            case 6:
                listJurusan = getResources().getStringArray(R.array.jurusan_psikologi);
                break;

            case 7:
                listJurusan = getResources().getStringArray(R.array.jurusan_ekonomi);
                break;

            case 8:
                listJurusan = getResources().getStringArray(R.array.jurusan_sains_teknologi);
                break;

            case 9:
                listJurusan = getResources().getStringArray(R.array.jurusan_ilmu_kesehatan);
                break;

            case 10:
                listJurusan = getResources().getStringArray(R.array.jurusan_isip);
                break;

            case 11:
                listJurusan = getResources().getStringArray(R.array.jurusan_kedokteran);
                break;
        }
    }

    private void showAlert(String body, Class<?> cls){
        CustomDialogTwo customDialogTwo = new CustomDialogTwo(body, cls);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        customDialogTwo.show(mFragmentManager, CustomDialogTwo.class.getSimpleName());
    }
}
