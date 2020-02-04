package com.sahitya.banksampahsahitya.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SingleChoiceDialogFragment extends DialogFragment {

    public static final String FAKULTAS_TAG = "fakultas_tag";
    public static final String JURUSAN_TAG = "jurusan_tag";
    private int position = 0;
    private String[] list;
    private SingleChoiceListener mListener;

    public SingleChoiceDialogFragment(String[] list) {
        this.list = list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SingleChoiceListener) context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getTag().equals(FAKULTAS_TAG)) {
            builder.setTitle("Pilih Salah Satu Fakultas")
                    .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            position = i;
                        }
                    }).setPositiveButton("PILIH", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mListener.onPositiveFakultasClicked(list, position);
                }
            }).setNegativeButton("TUTUP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getDialog().dismiss();
                }
            });

        }else if (getTag().equals(JURUSAN_TAG)){
            builder.setTitle("Pilih Salah Satu Jurusan")
                    .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            position = i;
                        }
                    }).setPositiveButton("PILIH", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mListener.onPositiveJurusanClicked(list, position);
                }
            }).setNegativeButton("TUTUP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getDialog().dismiss();
                }
            });
        }

        return builder.create();
    }

    public interface SingleChoiceListener{
        void onPositiveFakultasClicked(String[] list, int position);
        void onPositiveJurusanClicked(String[] list, int position);
    }
}
