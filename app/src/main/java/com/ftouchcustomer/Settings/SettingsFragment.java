package com.ftouchcustomer.Settings;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Classes.ClsSettingNameVal;
import com.ftouchcustomer.Complain.ActivitySelectComplain;
import com.ftouchcustomer.Address.ActivityAddress;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.Language.ClsUpdateLanguage;
import com.ftouchcustomer.Language.LanguageViewModel;
import com.ftouchcustomer.LoginActivity;
import com.ftouchcustomer.NavigationTabs.BottomNavigationActivity;
import com.ftouchcustomer.Profile.ProfileActivity;
import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {


    private List<ClsSettingNameVal> mList;
    private RecyclerView mRv;
    private SettingAdapter mCv;
    private LanguageViewModel languageViewModel;
    private int languageSelected;
    ClsUserInfo clsUserInfo;


//    ProgressDialog pd;

    public static SettingsFragment newInstance() {

        return new SettingsFragment();
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageViewModel = new ViewModelProvider(this).get(LanguageViewModel.class);

        //language detect from shared pref and change according
        SharedPreferences sp = getActivity().getSharedPreferences("Language_Source", MODE_PRIVATE);
        String language = sp.getString("language_Source", "Not Found");
        Log.d("--abc--", "main: " + language);

        clsUserInfo = ClsGlobal.getUserInfo(getActivity());

        if (language.equals("gu")) {
            languageSelected = 1;
        } else if (language.equals("en")) {
            languageSelected = 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_setting, container, false);
        ClsGlobal.isMoveToBack = true;

        main(v);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void main(View v) {
        mRv = v.findViewById(R.id.mRv);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewData();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void viewData() {

        mList = new ArrayList<>();

        mList.add(new ClsSettingNameVal(R.drawable.ic_profile,
                R.string.setting_profile,
                R.string.setting_profile_description, 1));

        mList.add(new ClsSettingNameVal(R.drawable.ic_home,
                R.string.setting_addresses,
                R.string.setting_addresses_description, 2));

        mList.add(new ClsSettingNameVal(R.drawable.ic_rate,
                R.string.setting_rate_app,
                R.string.setting_rate_app_description, 3));

        mList.add(new ClsSettingNameVal(R.drawable.ic_share,
                R.string.setting_share_app,
                R.string.setting_share_app_description, 4));

        mList.add(new ClsSettingNameVal(R.drawable.ic_error,
                R.string.setting_complain,
                R.string.setting_complain_description, 5));

       /* mList.add(new ClsSettingNameVal(R.drawable.ic_language,
                R.string.setting_language,
                R.string.setting_language_description, 6));
*/
        if (clsUserInfo.getLoginStatus() != null
                && !clsUserInfo.getLoginStatus().equalsIgnoreCase("")
                && !clsUserInfo.getLoginStatus().equalsIgnoreCase("Deactive")){

            mList.add(new ClsSettingNameVal(R.drawable.ic_logout,
                    R.string.setting_logout,
                    R.string.setting_logout_description, 7));
        }else{
            mList.add(new ClsSettingNameVal(R.drawable.ic_login,
                    R.string.setting_Login,
                    R.string.setting_Login_description,8));
        }


        mCv = new SettingAdapter(getActivity(), 0, mList, clsSettingNames -> {
            int obj = clsSettingNames.getPosition();

            if (obj == 7) {

                LogoutAlert();

            } else if (obj == 5) {

                Intent intent = new Intent(getActivity(), ActivitySelectComplain.class);

                intent.putExtra("_code", "");
                intent.putExtra("mobile", "");
                startActivity(intent);

            } else if (obj == 6) {
                SelectLanguage();


            } else if (obj == 3) {
                RateApp();
            } else if (obj == 4) {
                ShareApp();
            } else if (obj == 1) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                getActivity().startActivity(intent);
            } else if (obj == 2) {
                if (clsUserInfo.getLoginStatus() != null
                        && !clsUserInfo.getLoginStatus().equalsIgnoreCase("")
                        && !clsUserInfo.getLoginStatus().equalsIgnoreCase("Deactive")) {
                    Intent intent = new Intent(getActivity(), ActivityAddress.class);
                    intent.putExtra("placeOrder", "");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "Update your profile first!", Toast.LENGTH_SHORT).show();
                }
            }
            else if (obj == 8){
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("noLogin", "");
                startActivity(intent);
            }
        });

        mRv.setAdapter(mCv);
    }

    private void ShareApp() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "fTouch \n " +
                    "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share via"));
        } catch (Exception e) {
            Log.e("ShareApp", e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void RateApp() {
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ClsGlobal.isMoveToBack = false;
    }

    //    void LogoutAlert(ClsUserInfo objUserInfo) {
    void LogoutAlert() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.message_logout_prompt, null);

        TextView tvMessage = alertLayout.findViewById(R.id.tvPromptMessage);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),
                R.style.AppCompatAlertDialogStyle).create(); //Read Update.
        alertDialog.setView(alertLayout);
        alertDialog.setTitle("Confirmation");
        tvMessage.setText(getResources().getString(R.string.logout_message));

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                ClsLogout.LogoutApi(getActivity(), objUserInfo);

                ClsUserInfo _ObjUser = new ClsUserInfo();
                _ObjUser.setName("");
                _ObjUser.setRegisteredmobilenumber("");
                _ObjUser.setEmailaddress("");
                _ObjUser.setState("");
                _ObjUser.setCity("");
                _ObjUser.setPincode("");
                _ObjUser.setLoginStatus("Deactive");
                ClsGlobal.setUserInfo(_ObjUser, getActivity());

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("noLogin", "");
                startActivity(intent);
            }
        });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void SelectLanguage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] lan = {"English", "ગુજરાતી"};
        builder.setTitle("Choose Language")

                .setSingleChoiceItems(lan, languageSelected, (arg0, arg1) -> {

                })

                .setPositiveButton("OK", (dialog, id) -> {

                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Language_Source", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String language = selectedPosition + " selected";
                    Log.d("--abc--", "SelectLanguage: " + language);

                    switch (selectedPosition) {
                        case 0:
                            updateLanguage("English");
                            setLocale("en");
                            editor.putString("language_Source", "en");
                            Log.d("--abc--", "SelectLanguage: " + "en");
                            break;

                        case 1:
                            updateLanguage("Gujarati");
                            setLocale("gu");
                            editor.putString("language_Source", "gu");
                            Log.d("--abc--", "SelectLanguage: " + "gu");
                            break;
                    }
                    editor.apply();
                })

                .setNegativeButton("Cancel", (dialog, id) -> {

                })
                .show();
    }

    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(getActivity(), BottomNavigationActivity.class);
        Objects.requireNonNull(getActivity()).startActivity(refresh);
    }

    private void updateLanguage(String language) {

        ClsUpdateLanguage clsUpdateLanguage = new ClsUpdateLanguage();
        clsUpdateLanguage.setLanguage(language);
        clsUpdateLanguage.setMobileNumber(clsUserInfo.getRegisteredmobilenumber());

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsUpdateLanguage);
        Log.e("--URL--", "clsUpdateLanguage---" + jsonInString);

        languageViewModel.updateLanguage(clsUpdateLanguage)
                .observe(getActivity(), clsUpdateLanguageResponse -> {

                    Toast.makeText(getActivity(), clsUpdateLanguageResponse.getMESSAGE(), Toast.LENGTH_SHORT).show();
                });
    }


}
