package com.ftouchcustomer.NavigationTabs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ftouchcustomer.R;
import com.ftouchcustomer.Settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.ftouchcustomer.Language.LanguageSelectionActivity.getMyLanguageCode;
import static com.ftouchcustomer.Language.LanguageSelectionActivity.getMyLanguageName;
import static com.ftouchcustomer.Language.LanguageSelectionActivity.myLanguage;

public class BottomNavigationActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;
    SharedPreferences pref;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Fragment currentFragment;
    String mode = "";

    String placeOrder = "";
    String noLogin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);


        noLogin = getIntent().getStringExtra("noLogin");
        placeOrder = getIntent().getStringExtra("placeOrder");
        Log.d("--val--", "placeOrder: " + placeOrder);


        RelativeLayout relativeLayout = findViewById(R.id.container);

        overridePendingTransition(0, 0);
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        relativeLayout.startAnimation(animation);

        setupBottomNavigation();

        pref = getSharedPreferences(myLanguage,
                Context.MODE_PRIVATE);

        if (pref.getBoolean("_folderFlag", true)) {

            if (pref.contains(getMyLanguageName)) {
                String spLanguageName = pref.getString(getMyLanguageName, "");
                String spLanguageCode = pref.getString(getMyLanguageCode, "");
                Log.d("--val--", "sp: " + spLanguageName);
                Log.d("--val--", "sp: " + spLanguageCode);
            }
        }
//
//        if (placeOrder != null && placeOrder.equalsIgnoreCase("placeOrder")) {
//            loadOrdersFragment();
//            Log.d("--val--", "IFIFIFIF");
//        } else {
//            Log.d("--val--", "ESLEELSE");
//            loadHomeFragment();
//        }

        if (noLogin != null && noLogin.equalsIgnoreCase("noLogin")) {
            loadCartFragment();
        } else {
            loadHomeFragment();
        }


        if (placeOrder != null) {

            if (placeOrder.equalsIgnoreCase("placeOrder")) {
                loadOrdersFragment();
                Log.d("--val--", "IFIFIFIF");
            } else if (placeOrder.equalsIgnoreCase("NewAppointment")) {
                Log.d("--val--", "ESLEELSE");
                loadAppointmentFragment();
            } else {
                loadHomeFragment();
            }
        } else {
            loadHomeFragment();
        }

//        if (placeOrder != null && placeOrder.equalsIgnoreCase("NewAppointment")) {
//            loadAppointmentFragment();
//        } else {
//            loadHomeFragment();
//        }

    }

    private void loadOrdersFragment() {
        currentFragment = new OrdersFragment();
        switchFragment(currentFragment, "merchant");
        mode = "merchant";
    }

    private void loadCartFragment() {
        currentFragment = new CartFragment();
        switchFragment(currentFragment, "cart");
        mode = "cart";
    }

    private void setupBottomNavigation() {

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    loadHomeFragment();
                    return true;
                case R.id.action_merchant:
                    loadOrdersFragment();
                    return true;
                case R.id.action_appointment:
                    loadAppointmentFragment();
                    return true;
                case R.id.action_cart:
                    loadCartFragment();
                    return true;
                case R.id.action_settings:
                    loadSettingFragment();
                    return true;
            }
            return false;
        });

        NestedScrollView nested_content = findViewById(R.id.nested_scroll_view);
        nested_content.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY < oldScrollY) { // up
                        animateNavigation(false);
                        animateSearchBar(false);
                    }
                    if (scrollY > oldScrollY) { // down
                        animateNavigation(true);
                        animateSearchBar(true);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    boolean isNavigationHide = false;

    private void animateNavigation(final boolean hide) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return;
        isNavigationHide = hide;
        int moveY = hide ? (2 * mBottomNavigationView.getHeight()) : 0;
        mBottomNavigationView.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    boolean isSearchBarHide = false;

    private void animateSearchBar(final boolean hide) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
        isSearchBarHide = hide;
    }

    private void loadHomeFragment() {
        currentFragment = new HomeFragment();
        switchFragment(currentFragment, "home");
        mode = "home";
    }

    private void loadAppointmentFragment() {
        currentFragment = new FragmentAppointment();
        switchFragment(currentFragment, "appointment");
        mode = "appointment";
    }

    private void loadSettingFragment() {
        currentFragment = new SettingsFragment();
        switchFragment(currentFragment, "setting");
        mode = "setting";
    }

    @Override
    public void onBackPressed() {

        if (mBottomNavigationView.getSelectedItemId() == R.id.action_home) {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.message_logout_prompt, null);

            TextView tvMessage = (TextView) alertLayout.findViewById(R.id.tvPromptMessage);

            AlertDialog alertDialog = new AlertDialog.Builder(BottomNavigationActivity.this,
                    R.style.AppCompatAlertDialogStyle).create(); //Read Update.
            alertDialog.setView(alertLayout);
            alertDialog.setTitle("Confirmation");
            tvMessage.setText("Are you sure want to close this application?");

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> finishAffinity());
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.cancel());

            alertDialog.setCancelable(false);
            alertDialog.show();
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.action_home);
        }
    }

    private void switchFragment(Fragment fragment, String mode1) {
        if (!mode.equals(mode1)) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_frame, fragment);
            transaction.commit();
        }
    }
}
