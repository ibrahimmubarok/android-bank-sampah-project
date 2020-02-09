package com.sahitya.banksampahsahitya.ui.customdialog;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.presentation.membership.editprofile.EditProfileActivity;
import com.sahitya.banksampahsahitya.presentation.membership.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sahitya.banksampahsahitya.MainActivity.accountList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogTwo extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.tv_head_dialog_two)
    TextView tvHead;
    @BindView(R.id.tv_tidak)
    TextView tvTidak;
    @BindView(R.id.tv_ya)
    TextView tvYa;

    private String head;
    public static boolean isEditProfile = false;

    private Unbinder unbinder;

    private Class<?> cls;

    public CustomDialogTwo() {
        // Required empty public constructor
    }

    public CustomDialogTwo(String head, Class<?> cls) {
        this.head = head;
        this.cls = cls;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_dialog);
        return inflater.inflate(R.layout.fragment_custom_dialog_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        tvHead.setText(head);

        tvTidak.setOnClickListener(this);
        tvYa.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_tidak){
            getDialog().dismiss();
        }else if (view.getId() == R.id.tv_ya){
            if (cls.getSimpleName().equals(LoginActivity.class.getSimpleName())) {
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), cls));
                getActivity().finish();
                accountList.clear();
            }else if (cls.getSimpleName().equals(EditProfileActivity.class.getSimpleName())){
                isEditProfile = true;
                getActivity().finish();
            }
            getDialog().cancel();
        }
    }
}
