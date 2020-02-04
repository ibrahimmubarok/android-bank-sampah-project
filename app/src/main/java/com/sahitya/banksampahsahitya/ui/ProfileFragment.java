package com.sahitya.banksampahsahitya.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sahitya.banksampahsahitya.MainActivity;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.ItemProfileModel;
import com.sahitya.banksampahsahitya.model.ProfileUserModel;
import com.sahitya.banksampahsahitya.presentation.adapter.ProfileAdapter;
import com.sahitya.banksampahsahitya.presentation.membership.SettingsActivity;
import com.sahitya.banksampahsahitya.presentation.membership.changepassword.ChangePasswordActivity;
import com.sahitya.banksampahsahitya.presentation.membership.editprofile.EditProfileActivity;
import com.sahitya.banksampahsahitya.presentation.membership.help.HelpActivity;
import com.sahitya.banksampahsahitya.presentation.membership.login.LoginActivity;
import com.sahitya.banksampahsahitya.utils.ProfileUtils;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.sahitya.banksampahsahitya.MainActivity.accountList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.rv_profile)
    RecyclerView rvProfile;
    @BindView(R.id.container_logout)
    LinearLayout containerLogout;
    @BindView(R.id.main_layout_profile)
    LinearLayout linearProfile;
    @BindView(R.id.layout_no_koneksi)
    FrameLayout noInternetLayout;
    @BindView(R.id.tv_refresh_connection)
    TextView tvRefresh;
    @BindView(R.id.progress_bar_profile)
    ProgressBar progressBar;

    private Unbinder unbinder;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ProfileUtils profileModel = ViewModelProviders.of(this).get(ProfileUtils.class);
        profileModel.getLiveDataProfileUser().observe(this, getProfileData);

        unbinder = ButterKnife.bind(this, view);

        progressBar.setVisibility(View.VISIBLE);

        setListMenuProfile();

        setLogoutAccount();

        profileModel.asyncProfileUser(MainActivity.idUser, progressBar ,linearProfile, noInternetLayout);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                noInternetLayout.setVisibility(View.GONE);
                profileModel.asyncProfileUser(MainActivity.idUser, progressBar ,linearProfile, noInternetLayout);
            }
        });

        return view;
    }

    private void setLogoutAccount() {
        containerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Apakah Kamu Yakin Ingin Log Out?");
                builder.setTitle("Log Out");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                accountList.clear();
                                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
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
            }
        });
    }

    private void setListMenuProfile() {
        ArrayList<ItemProfileModel> itemProfileModelList = new ArrayList<>();

        itemProfileModelList.add(new ItemProfileModel(R.drawable.ic_disbursement, "Bantuan"));
        itemProfileModelList.add(new ItemProfileModel(R.drawable.ic_edit_profile, "Edit Profile"));
        itemProfileModelList.add(new ItemProfileModel(R.drawable.ic_change_password, "Ganti Password"));
        itemProfileModelList.add(new ItemProfileModel(R.drawable.ic_setting, "Pengaturan"));

        ProfileAdapter profileAdapter = new ProfileAdapter(getContext(), itemProfileModelList);

        rvProfile.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvProfile.setAdapter(profileAdapter);

        profileAdapter.setOnProfileClickListener(new ProfileAdapter.OnProfileClickListener() {

            @Override
            public void onProfileClicked(View view, int position) {
                switch (position) {
                    case 0:
                        HelpActivity.start(getContext());
                        break;
                    case 1:
                        EditProfileActivity.start(getContext());
                        break;
                    case 2:
                        ChangePasswordActivity.start(getContext());
                        break;
                    case 3:
                        SettingsActivity.start(getContext());
                        break;
                }
            }
        });
    }

    private Observer<ArrayList<ProfileUserModel>> getProfileData = new Observer<ArrayList<ProfileUserModel>>() {
        @Override
        public void onChanged(ArrayList<ProfileUserModel> profileUserModel) {
            if (profileUserModel != null){
                Glide.with(getContext())
                        .load(profileUserModel.get(0).getFoto())
                        .into(imgAvatar);
                tvEmail.setText(profileUserModel.get(0).getEmail());
                tvName.setText(profileUserModel.get(0).getName());
                Log.d(TAG, "Ada Data");
                progressBar.setVisibility(View.GONE);
            }else{
                progressBar.setVisibility(View.GONE);
            }
        }
    };
}
