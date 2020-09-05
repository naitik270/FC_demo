package com.ftouchcustomer.Language;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Interface.OnRadioClickLanguage;
import com.ftouchcustomer.NavigationTabs.BottomNavigationActivity;
import com.ftouchcustomer.NavigationTabs.HomeFragment;
import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    private RecyclerView mRv;
    List<ClsLanguageGetSet> lstClsLanguageGetSets = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        main();
    }

    private void main() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRv = findViewById(R.id.mRv);
        mRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        viewLanguageList();
    }

    SharedPreferences pref;

    public static final String myLanguage = "myLanguage";
    public static final String getMyLanguageName = "getMyLanguageName";
    public static final String getMyLanguageCode = "getMyLanguageName";


    void viewLanguageList() {
        lstClsLanguageGetSets = new ArrayList<>();
        lstClsLanguageGetSets.add(new ClsLanguageGetSet("ENGLISH", "eu", true));
        lstClsLanguageGetSets.add(new ClsLanguageGetSet("GUJARATI", "gu", false));


        LanguageSelectionAdapter adapter = new LanguageSelectionAdapter
                (LanguageSelectionActivity.this,
                        lstClsLanguageGetSets);

        adapter.SetOnClickListener(new OnRadioClickLanguage() {
            @Override
            public void OnItemClick(ClsLanguageGetSet clsSettingNameVal, int position) {
//                adapter.itemCheckChanged(position);

                confirmDialog(clsSettingNameVal.getCode(), clsSettingNameVal.getLanguageName());

            }
        });

        mRv.setAdapter(adapter);
    }

    void confirmDialog(String language, String fullName) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.message_logout_prompt, null);

        TextView tvMessage = alertLayout.findViewById(R.id.tvPromptMessage);

        AlertDialog alertDialog = new AlertDialog.Builder(LanguageSelectionActivity.this,
                R.style.AppCompatAlertDialogStyle).create(); //Read Update.
        alertDialog.setView(alertLayout);
        alertDialog.setTitle("Confirmation");
        tvMessage.setText("Sure to confirm Change application Language?");

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                setLocale(language);

//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString(getMyLanguageName, fullName); // Storing string
//                editor.putString(getMyLanguageCode, language); // Storing string
//                editor.putBoolean("selectLanguage", true); // Storing string
//                editor.commit(); // commit changes


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

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);


        Intent refresh = new Intent(this, BottomNavigationActivity.class);

//        finish();
//        recreate();
        startActivity(refresh);
//        finish();

//       loadHomeFragment();

    }


    private void loadHomeFragment() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, new HomeFragment())
                .disallowAddToBackStack().commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
