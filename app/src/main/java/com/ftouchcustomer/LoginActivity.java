package com.ftouchcustomer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.Global.ConnectionDetector;
import com.ftouchcustomer.Global.DisplayTAndCActivity;
import com.ftouchcustomer.NavigationTabs.BottomNavigationActivity;
import com.ftouchcustomer.OTP.ClsSendOtp;
import com.ftouchcustomer.OTP.ClsVerifyOtp;
import com.ftouchcustomer.OTP.OtpActivityViewModel;
import com.ftouchcustomer.Profile.Country.CountryViewModels;
import com.ftouchcustomer.Profile.getProfile.ClsGetProfile;
import com.google.gson.Gson;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText edt_mobile;
    Button btn_continue;
    TextView txt_skip;
    TextView txt_terms;
    String _mobileNo;
    Dialog mDialog;
    PinEntryEditText pinEntryEditText;
    TextView tv_mobile_no, tv_resend, tv_verify, textView;
    ImageView iv_close;
    private ProgressDialog pd;
    private OtpActivityViewModel otpActivityViewModel;
    String loginStatus = "";
    String noLogin = "";
    ClsUserInfo clsUserInfo;
    ClsGetProfile getProfiles;
    CheckBox chk_agree;

    int counter = 30;
    boolean increment = true;
    CountDownTimer timer;

    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    private static final Pattern MobileNo_Pattern
            = Pattern.compile(ClsGlobal.MobileNo_Pattern);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        clsUserInfo = ClsGlobal.getUserInfo(this);
        loginStatus = clsUserInfo.getLoginStatus();

        if (loginStatus != null && loginStatus.equalsIgnoreCase("Active")) {
            Intent intent = new Intent(this, BottomNavigationActivity.class);
            startActivity(intent);
        }

        otpActivityViewModel = new ViewModelProvider(this).get(OtpActivityViewModel.class);
        main();
    }

    private void main() {
        noLogin = getIntent().getStringExtra("noLogin");
        chk_agree = findViewById(R.id.chk_agree);
        txt_terms = findViewById(R.id.txt_terms);
        edt_mobile = findViewById(R.id.edt_mobile);
        btn_continue = findViewById(R.id.btn_continue);
        txt_skip = findViewById(R.id.txt_skip);

        edt_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 10) {
                    closeKeyBoard();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txt_terms.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        txt_terms.setOnClickListener(view -> {

            cd = new ConnectionDetector(getApplicationContext());
            isInternetPresent = cd.isConnectingToInternet();

            if (isInternetPresent) {

                Intent intent = new Intent(getApplication(), DisplayTAndCActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Please check your internet connection!", Toast.LENGTH_SHORT)
                        .show();
            }

        });

        btn_continue.setOnClickListener(v -> {

            if (!chk_agree.isChecked()) {
                Toast.makeText(LoginActivity.this, "Please accept terms & conditions", Toast.LENGTH_SHORT).show();

            } else {

                boolean validation = validation();
                if (validation) {

                    _mobileNo = edt_mobile.getText().toString();

                    mDialog = new Dialog(this);
                    if (mDialog.isShowing()) return;
                    mDialog = new Dialog(LoginActivity.this);
                    mDialog.setContentView(R.layout.dialog_otp_verify);
                    mDialog.setCanceledOnTouchOutside(false);
                    //noinspection ConstantConditions
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.setCancelable(false);
                    mDialog.show();

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(Objects.requireNonNull(mDialog.getWindow()).getAttributes());
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    mDialog.getWindow().setAttributes(lp);

                    tv_mobile_no = mDialog.findViewById(R.id.tv_mobile);
                    tv_resend = mDialog.findViewById(R.id.txt_link_Resend);
                    tv_verify = mDialog.findViewById(R.id.tv_verify);
                    pinEntryEditText = mDialog.findViewById(R.id.pinView);
                    textView = mDialog.findViewById(R.id.textView);
                    iv_close = mDialog.findViewById(R.id.iv_close);

                    String mobileNo = edt_mobile.getText().toString();
                    tv_mobile_no.setText("+91 " + mobileNo);

                    if (ClsGlobal.isNetworkConnected(this)) {
                        pd = ClsGlobal._prProgressDialog(this, "Sending OTP...", true);
                        pd.show();
                        sendOtp();
                        pd.setOnDismissListener(dialog -> {
                            tv_resend.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.VISIBLE);
                        });
                    } else {
                        Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }

                    iv_close.setOnClickListener(view -> mDialog.dismiss());

                    tv_verify.setOnClickListener(view -> {
                        int length = pinEntryEditText.getText().length();

                        if (length == 4) {
                            if (ClsGlobal.isNetworkConnected(this)) {
                                pd = ClsGlobal._prProgressDialog(this, "Verifying OTP...", true);
                                pd.show();
                                verifiedOtpAPI();
                            }
                        }
                    });

                    tv_resend.setOnClickListener(view -> {
                        if (!increment) {

//                            Toast.makeText(this, "OTP resend successfully!", Toast.LENGTH_SHORT).show();

                            if (ClsGlobal.isNetworkConnected(this)) {
                                // Final code...
                                pd = ClsGlobal._prProgressDialog(this, "We are sending OTP to you...", true);
                                pd.show();
                                sendOtp();
                                pd.setOnDismissListener(dialog -> {
                                    tv_resend.setVisibility(View.VISIBLE);
                                    textView.setVisibility(View.VISIBLE);
                                });
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Please check your internet connection!", Toast.LENGTH_SHORT)
                                        .show();
                            }
                            resetCounter();
                        }
                    });


                }
            }

        });

        txt_skip.setOnClickListener(v -> {

            if (!chk_agree.isChecked()) {
                Toast.makeText(LoginActivity.this, "Please accept terms & conditions", Toast.LENGTH_SHORT).show();

            } else {

                ClsUserInfo clsUserInfo = new ClsUserInfo();
                clsUserInfo.setRegisteredmobilenumber("");
                clsUserInfo.setLoginStatus("Deactive");
                clsUserInfo.setCustomerId("0");
                ClsGlobal.setUserInfo(clsUserInfo, this);
                Log.d("setUserInfo", "Login skip: ");
                Intent i = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                startActivity(i);
            }


        });
    }


    void resetCounter() {
        if (increment && counter != 30) {
            timer.cancel();
        }
        counter = 30;
        increment = true;
        tv_resend.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setVisibility(View.VISIBLE);
    }

    void setCounter() {

        timer = new CountDownTimer(31000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (increment) {
                    counter--;
                    textView.setText("(" + String.valueOf(counter) + ")");
                    Log.d("--counter--", "onTick: " + counter);
                    if (counter == 0) {
                        textView.setVisibility(View.GONE);
                        increment = false;
                        enableResendButton();
                    }
                }
            }

            public void onFinish() {
                textView.setVisibility(View.GONE);
            }
        }.start();
    }

    void enableResendButton() {
        Log.d("enableResendButton", "call: ");
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setEnabled(true);
    }

    void verifiedOtpAPI() {

        String otp = pinEntryEditText.getText().toString();
        Log.d("OTP", "verifiedOtpAPI: " + otp);
        ClsVerifyOtp clsVerifyOtp = new ClsVerifyOtp();
        clsVerifyOtp.setMobileNumber(_mobileNo);
        clsVerifyOtp.setVerificationCode(otp);
        clsVerifyOtp.setUserType("Customer");
        clsVerifyOtp.setOTPSendMode("Registration");

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsVerifyOtp);
        Log.e("--URL--", "clsVerifyOtp---" + jsonInString);

        otpActivityViewModel.verifyOtp(clsVerifyOtp).observe(this, clsVerifyOtp1 -> {
            pd.dismiss();
            if (clsVerifyOtp1 != null) {
                String message = clsVerifyOtp1.getSuccess();

                if ("0".equals(message)) {
                    Toast.makeText(this, "Verification fail!", Toast.LENGTH_SHORT).show();
                } else if ("1".equals(message)) {

                    ClsUserInfo clsUserInfo = new ClsUserInfo();
                    clsUserInfo.setRegisteredmobilenumber(_mobileNo);
                    clsUserInfo.setLoginStatus("Active");
                    clsUserInfo.setCustomerId("0");
                    ClsGlobal.setUserInfo(clsUserInfo, this);
                    Log.d("setUserInfo", "Login Verified OTP api: ");
                    getProfile(_mobileNo);
                    Intent i = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                    i.putExtra("noLogin",noLogin);
                    startActivity(i);
                } else if ("2".equals(message)) {
                    Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                } else if ("3".equals(message)) {
                    Toast.makeText(this, "Invalid Verification Code", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to Verify OTP", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOtp() {

        ClsSendOtp clsSendOtp = new ClsSendOtp();
        clsSendOtp.setMobileNumber(_mobileNo);
        clsSendOtp.setUserType("Customer");
        clsSendOtp.setOtpSendMode("Registration");

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsSendOtp);
        Log.e("--URL--", "clsSendOtp---" + jsonInString);

        otpActivityViewModel.sendOtp(clsSendOtp).observe(this, clsSendOtpResponse -> {
            pd.dismiss();
            if (clsSendOtpResponse != null) {
                String message = clsSendOtpResponse.getSuccess();
                Log.d("msg", "sendOtp: " + message);

                resetCounter();
                setCounter();

                if ("0".equals(message)) {
                    Toast.makeText(this, "Failed to send OTP!", Toast.LENGTH_SHORT).show();
                } else if ("1".equals(message)) {
//                    resetCounter();
//                    setCounter();
//                    tv_resend.setVisibility(View.VISIBLE);
//                    textView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "OTP Send Successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to send OTP!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validation() {
        Matcher matcher = MobileNo_Pattern.matcher(edt_mobile.getText().toString());
        if (!matcher.find()) {
            Toast.makeText(getApplicationContext(), "Invalid Mobile No!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void closeKeyBoard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager iMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            iMM.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.message_logout_prompt, null);

        TextView tvMessage = (TextView) alertLayout.findViewById(R.id.tvPromptMessage);

        AlertDialog alertDialog = new AlertDialog.Builder(this,
                R.style.AppCompatAlertDialogStyle).create(); //Read Update.
        alertDialog.setView(alertLayout);
        alertDialog.setTitle("Confirmation");
        tvMessage.setText("Are you sure want to close this application?");

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> finishAffinity());
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.cancel());

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void getProfile(String _mobileNo) {
        CountryViewModels countryViewModels = new ViewModelProvider(this).get(CountryViewModels.class);

        countryViewModels.getProfile(_mobileNo, "")
                .observe(this, clsGetProfileResponse -> {

                    if (clsGetProfileResponse != null) {
                        getProfiles = clsGetProfileResponse.getData();

                        String success = clsGetProfileResponse.getSuccess();
                        if (success.equalsIgnoreCase("2")) {
                            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                        } else if (success.equalsIgnoreCase("0")) {
                            Toast.makeText(this, "Fail to load a data.", Toast.LENGTH_SHORT).show();
                        }

                        if (getProfiles != null) {

                            ClsUserInfo clsUserInfo = new ClsUserInfo();
                            clsUserInfo.setCustomerId(getProfiles.getCustomerMasterID());
                            clsUserInfo.setName(getProfiles.getName());
                            clsUserInfo.setLoginStatus("Active");
                            clsUserInfo.setRegisteredmobilenumber(getProfiles.getMobileNo());
                            clsUserInfo.setEmailaddress(getProfiles.getEmail());
                            clsUserInfo.setState(getProfiles.getState());
                            clsUserInfo.setCity(getProfiles.getCity());
                            clsUserInfo.setPincode(getProfiles.getPinCode());
                            clsUserInfo.setStateId(getProfiles.getStateID());
                            clsUserInfo.setDob(getProfiles.getDOB());
                            clsUserInfo.setGender(getProfiles.getGender());
                            Log.d("setUserInfo", "Login Get Profile: ");

                            Toast.makeText(this, "Welcome " + getProfiles.getName(), Toast.LENGTH_SHORT).show();

                            ClsGlobal.setUserInfo(clsUserInfo, this);

                        } else {
                            Toast.makeText(this, clsGetProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    pd.dismiss();
                });
    }
}
