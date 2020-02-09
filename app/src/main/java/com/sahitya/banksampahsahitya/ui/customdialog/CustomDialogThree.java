package com.sahitya.banksampahsahitya.ui.customdialog;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.sahitya.banksampahsahitya.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogThree extends DialogFragment implements View.OnClickListener{

    @BindView(R.id.tv_oke_dialog_three)
    TextView tvOke;
    @BindView(R.id.tv_head_dialog_three)
    TextView tvHead;
    @BindView(R.id.tv_body_dialog_three)
    TextView tvBody;

    private Unbinder unbinder;

    private String head;
    private String body;

    public CustomDialogThree() {
        // Required empty public constructor
    }

    public CustomDialogThree(String head, String body) {
        this.head = head;
        this.body = body;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_dialog);
        return inflater.inflate(R.layout.fragment_custom_dialog_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        tvHead.setText(head);
        tvBody.setText(body);

        tvOke.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_oke_dialog_three){
            getDialog().dismiss();
        }
    }
}
