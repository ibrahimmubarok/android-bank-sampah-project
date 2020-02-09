package com.sahitya.banksampahsahitya.ui;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.GarbageInfoModel;
import com.sahitya.banksampahsahitya.presentation.adapter.CategoryGarbageInfoAdapter;
import com.sahitya.banksampahsahitya.presentation.adapter.GarbageInfoAdapter;
import com.sahitya.banksampahsahitya.utils.GarbageInfoApiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoSampahFragment extends Fragment {

    private static final String TAG = InfoSampahFragment.class.getSimpleName();

    @BindView(R.id.main_layout_garbage_info)
    LinearLayout linearLayoutInfoSampah;
    @BindView(R.id.recycler_view_category_garbage_info)
    RecyclerView rvCategory;
    @BindView(R.id.recycler_view_garbage_info)
    RecyclerView rvGarbageInfo;
    @BindView(R.id.layout_no_koneksi)
    FrameLayout noKoneksiLayout;
    @BindView(R.id.tv_refresh_connection)
    TextView tvRefresh;
    @BindView(R.id.progress_bar_garbage_info)
    ProgressBar progressBar;
    @BindView(R.id.edit_text_search_garbage_info)
    EditText edtSearch;

    private Unbinder unbinder;

    private List<GarbageInfoModel> garbageInfoList;
    private List<String> categoryList;

    private GarbageInfoAdapter adapter;
    private CategoryGarbageInfoAdapter categoryAdapter;

    public InfoSampahFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garbage_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GarbageInfoApiUtils garbageInfoApiUtils = ViewModelProviders.of(this).get(GarbageInfoApiUtils.class);
        garbageInfoApiUtils.getLiveDataGarbageInfo().observe(this, getGarbageInfo);

        unbinder = ButterKnife.bind(this, view);

        garbageInfoList = new ArrayList<>();
        categoryList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);

        categoryAdapter = new CategoryGarbageInfoAdapter(this.getContext(), categoryList);
        categoryAdapter.notifyDataSetChanged();

        adapter = new GarbageInfoAdapter(this.getContext(), garbageInfoList);
        adapter.notifyDataSetChanged();

        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvCategory.setAdapter(categoryAdapter);

        rvGarbageInfo.setHasFixedSize(true);
        rvGarbageInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGarbageInfo.setAdapter(adapter);

        garbageInfoApiUtils.asyncGarbageInfoData(progressBar, linearLayoutInfoSampah , noKoneksiLayout);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                noKoneksiLayout.setVisibility(View.GONE);
                garbageInfoApiUtils.asyncGarbageInfoData(progressBar, linearLayoutInfoSampah , noKoneksiLayout);
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String nama = textView.getText().toString();

                    adapter.getFilter().filter(nama);

                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        categoryAdapter.setCategoryGarbageInfoClickListener(new CategoryGarbageInfoAdapter.OnCategoryGarbageInfoClickListener() {
            @Override
            public void onCategoryClicked(View view, int position, String name) {
                if (position == 0) {
                    adapter.setAllCategoryGarbage();
                } else {
                    adapter.setCategoryGarbage(name);
                }
                adapter.setNameCategory(name);
            }
        });
    }

    private Observer<ArrayList<GarbageInfoModel>> getGarbageInfo = new Observer<ArrayList<GarbageInfoModel>>() {
        @Override
        public void onChanged(ArrayList<GarbageInfoModel> riwayat) {
            if (!riwayat.isEmpty()){
                adapter.setData(riwayat);

                categoryList.add("Semua");
                categoryAdapter.setData(getDataCategory(riwayat, categoryList));

                Log.d("Masuk Pak Eko", riwayat.get(0).getNama());
                progressBar.setVisibility(View.GONE);
            }else{
                Log.d("Gaada Data Pak Eko", "0");
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    private List<String> getDataCategory(List<GarbageInfoModel> mGarbage, List<String> mCategory){
        int j = 0;
        for (int i=0; i<mGarbage.size(); i++){
            if (mCategory.isEmpty()){
                mCategory.add(mGarbage.get(i).getKategori());
            }else{
                if (!mGarbage.get(i).getKategori().equals(mCategory.get(j))){
                    mCategory.add(mGarbage.get(i).getKategori());
                    j++;
                }
            }
        }
        return categoryList;
    }
}