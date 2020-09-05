package com.ftouchcustomer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ftouchcustomer.Global.ClsGlobal;

public class SplashActivity extends AppCompatActivity {
    Animation anim;
    ImageView header_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sp = getSharedPreferences("Language_Source", MODE_PRIVATE);
        String language = sp.getString("language_Source", "en");
        Log.d("--abc--", "login: " + language);
        ClsGlobal.setLanguage(this,language);

    /*    new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, BottomNavigationActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
*/


        header_icon = findViewById(R.id.header_icon); // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                i.putExtra("noLogin", "");
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        header_icon.startAnimation(anim);


    }
}
