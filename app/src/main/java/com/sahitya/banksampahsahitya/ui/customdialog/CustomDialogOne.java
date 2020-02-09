package com.sahitya.banksampahsahitya.ui.customdialog;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogOne extends DialogFragment implements View.OnClickListener{

    @BindView(R.id.tv_oke)
    TextView tvOke;
    @BindView(R.id.tv_head_dialog_one)
    TextView tvHead;
    @BindView(R.id.tv_body_dialog_one)
    TextView tvBody;

    private String head;
    private String body;

    private Unbinder unbinder;

    public CustomDialogOne() {
        // Required empty public constructor
    }

    public CustomDialogOne(String head, String body) {
        this.head = head;
        this.body = body;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_dialog);
        return inflater.inflate(R.layout.fragment_custom_dialog_one, container, false);
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_oke){
            getDialog().dismiss();
        }
    }
}
