package com.ftouchcustomer.Profile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.NavigationTabs.BottomNavigationActivity;
import com.ftouchcustomer.OTP.ClsSendOtp;
import com.ftouchcustomer.OTP.ClsSendVerificationEmail;
import com.ftouchcustomer.OTP.ClsVerifyOtp;
import com.ftouchcustomer.OTP.OtpActivityViewModel;
import com.ftouchcustomer.Profile.City.ClsCity;
import com.ftouchcustomer.Profile.Country.CountryViewModels;
import com.ftouchcustomer.Profile.State.StateListActivity;
import com.ftouchcustomer.Profile.getProfile.ClsGetProfile;
import com.ftouchcustomer.Profile.updateProfile.ClsUpdateProfile;
import com.ftouchcustomer.R;
import com.ftouchcustomer.Util.utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    ClsUserInfo clsUserInfo;
    private EditText tv_name, tv_mobile, tv_email, tv_pin_code;
    private TextView tv_mobile_verify, tv_email_verify, tv_state, tv_gender;
    private AutoCompleteTextView tv_city;
    private LinearLayout ll_email_verified, ll_mobile_verified, ll_dob, ll_gender;
    private ImageView iv_expand_more;
    private CountryViewModels countryViewModels;
    private ClsGetProfile getProfiles;
    private ClsUpdateProfile clsUpdateProfile = new ClsUpdateProfile();
    private List<ClsCity> cities = new ArrayList<>();
    private List<String> citiesName = new ArrayList<>();
    private OtpActivityViewModel otpActivityViewModel;
    private ProgressDialog pd;
    int emailLength;
    int stateId = 0;
    boolean goBack = true;

    String shMobile, shEmail;

    PinEntryEditText pinEntryEditText;
    TextView tv_mobile_no, tv_resend, tv_verify, textView, tv_dob;
    ImageView iv_close;
    String _mobileNo;
    Dialog mDialog;

    DatePickerDialog datePickerDialog;
    int year, month, day;
    Calendar calendar;

    int counter = 30;
    boolean increment = true;
    CountDownTimer timer;
    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        calendar = Calendar.getInstance();

        clsUserInfo = ClsGlobal.getUserInfo(this);
        shMobile = clsUserInfo.getRegisteredmobilenumber();
        shEmail = clsUserInfo.getEmailaddress();
        emailLength = shEmail.length();

        countryViewModels = new ViewModelProvider(this).get(CountryViewModels.class);
        otpActivityViewModel = new ViewModelProvider(this).get(OtpActivityViewModel.class);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_email = findViewById(R.id.tv_email);
        tv_dob = findViewById(R.id.tv_dob);
        tv_gender = findViewById(R.id.tv_gender);

        RelativeLayout relative_layout_save = findViewById(R.id.relative_layout_save);
        tv_state = findViewById(R.id.tv_state);
        tv_city = findViewById(R.id.tv_city);
        tv_pin_code = findViewById(R.id.tv_pin_code);
        tv_mobile_verify = findViewById(R.id.tv_mobile_verify);
        tv_email_verify = findViewById(R.id.tv_email_verify);
        ll_mobile_verified = findViewById(R.id.ll_mobile_verified);
        ll_email_verified = findViewById(R.id.ll_email_verified);
        ll_dob = findViewById(R.id.ll_dob);
        ll_gender = findViewById(R.id.ll_gender);
        iv_expand_more = findViewById(R.id.iv_expand_more);

        ll_dob.setOnClickListener(view -> {
            calendar.add(Calendar.YEAR, -25);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);

                    String myFormat = "dd/MM/yyyy"; // In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    tv_dob.setText(sdf.format(calendar.getTime()));
                }
            }, year, month, day);

            datePickerDialog.show();
        });

        ll_gender.setOnClickListener(view -> {
            selectGender();
        });

        pd = ClsGlobal._prProgressDialog(this, "Getting Profile...", true);
        pd.show();
        if (ClsGlobal.isNetworkConnected(this)) {
            Log.d("--lll--", "onCreate: Internet Available ");
            if (!clsUserInfo.getRegisteredmobilenumber().equalsIgnoreCase("")) {
                Log.d("--lll--", "onCreate: mobile number Available ");
                getProfile();
            } else {
                pd.dismiss();
                Log.d("--lll--", "onCreate: mobile number Not Available ");
            }
        } else {
            pd.dismiss();
            Log.d("--lll--", "onCreate: No Internet ");
            Toast.makeText(this, "Check Your Internet connection", Toast.LENGTH_SHORT).show();
        }

        iv_expand_more.setOnClickListener(v -> {
//            Toast.makeText(this, "state clicked....", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, StateListActivity.class);
            startActivityForResult(intent, 2);
        });

        initToolbar();

//        pd.setOnDismissListener(dialogInterface -> {
//                    if (goBack) onBackPressed();
//                }
//        );

        relative_layout_save.setOnClickListener(v -> {
            if (ClsGlobal.isNetworkConnected(this)) {
                checkValidations();
            } else {
                Toast.makeText(this, "Please check your Internet!", Toast.LENGTH_SHORT).show();
            }
        });

        tv_mobile_verify.setOnClickListener(v -> {
            if (tv_mobile.getText().toString().equals("")) {
                tv_mobile.setError("Required");
            } else if (tv_mobile.getText().toString().length() != 10) {
                tv_mobile.setError("10 Digit number required");
            } else {
                if (ClsGlobal.isNetworkConnected(this)) {

                    _mobileNo = tv_mobile.getText().toString();

                    mDialog = new Dialog(this);
                    if (mDialog.isShowing()) return;
                    mDialog = new Dialog(ProfileActivity.this);
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

                    String mobileNo = tv_mobile.getText().toString();
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
                            Toast.makeText(this, "OTP resend successfully!", Toast.LENGTH_SHORT).show();

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

                } else {
                    Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                Log.d("Mobile : ", "afterTextChanged: " + s.length());
                if (s.length() > 0) {
                    tv_mobile_verify.setVisibility(View.VISIBLE);
                    ll_mobile_verified.setVisibility(View.GONE);
                } else if (s.length() == 0) {
                    tv_mobile_verify.setVisibility(View.GONE);
                    ll_mobile_verified.setVisibility(View.GONE);
                }
            }
        });

        tv_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tv_email_verify.setVisibility(View.VISIBLE);
                    ll_email_verified.setVisibility(View.GONE);
                } else if (s.length() == 0) {
                    tv_email_verify.setVisibility(View.GONE);
                    ll_email_verified.setVisibility(View.GONE);

                }
            }
        });

        tv_email_verify.setOnClickListener(v -> {

            if (tv_email.getText().toString().equals("")) {
                tv_email.setError("Required");
            } else if (!utils.isValidEmail(tv_email.getText().toString())) {
                tv_email.setError("Enter valid Email.");
            } else {
                String email = tv_email.getText().toString();

                dialog = new BottomSheetDialog(ProfileActivity.this,R.style.AppBottomSheetDialogTheme);
                dialog.setContentView(R.layout.dialog_email_verify);
                dialog.show();

                TextView tv_dialog_email = dialog.findViewById(R.id.tv_dialog_email);
                TextView tv_save = dialog.findViewById(R.id.tv_save);

                tv_dialog_email.setText(email);
                tv_save.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    pd = ClsGlobal._prProgressDialog(this, "Sending verification link...", false);
                    pd.show();
                    verifyEmail();
                });
            }
        });
    }

    private void selectGender() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String[] lan = {"Male", "Female", "Other"};
        builder.setTitle("Choose Gender")

                .setSingleChoiceItems(lan, -1, (arg0, arg1) -> {

                })

                .setPositiveButton("OK", (dialog, id) -> {

                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                    SharedPreferences sharedPreferences = getSharedPreferences("Language_Source", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String language = selectedPosition + " selected";
                    Log.d("--abc--", "SelectLanguage: " + language);

                    switch (selectedPosition) {
                        case 0:
                            tv_gender.setText("Male");
                            break;

                        case 1:
                            tv_gender.setText("Female");
                            break;

                        case 2:
                            tv_gender.setText("Other");
                            break;
                    }
                    editor.apply();
                })

                .setNegativeButton("Cancel", (dialog, id) -> {

                })
                .show();
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
                    ClsGlobal.setUserInfo(clsUserInfo, this);
                    Log.d("setUserInfo", "Profile Verified OTP api: ");

                    startActivity(new Intent(this, BottomNavigationActivity.class));
                    Toast.makeText(this, "Verified Successfully.", Toast.LENGTH_SHORT).show();
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

    private void updateProfile() {
        String name = tv_name.getText().toString();
        String mobile = tv_mobile.getText().toString();
        String email = tv_email.getText().toString();
        String state = tv_state.getText().toString();
        String city = tv_city.getText().toString();
        String pinCode = tv_pin_code.getText().toString();
        String gender = tv_gender.getText().toString();
        String dob = tv_dob.getText().toString();

        clsUpdateProfile.setName(name);
        clsUpdateProfile.setMobileNumber(mobile);
        clsUpdateProfile.setEmail(email);
        clsUpdateProfile.setState(state);
        clsUpdateProfile.setStateId(stateId);
        clsUpdateProfile.setCity(city);
        clsUpdateProfile.setPinCode(pinCode);
        clsUpdateProfile.setGender(gender);
        clsUpdateProfile.setDOB(dob);

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsUpdateProfile);
        Log.e("--URL--", "clsUpdateProfile---" + jsonInString);

        countryViewModels.updateProfile(clsUpdateProfile)
                .observe(this, clsUpdateProfileResponse -> {
                    pd.dismiss();

                    String msg = clsUpdateProfileResponse.getSUCCESS();

                    if ("0".equals(msg)) {
                        Toast.makeText(this, "Fail to upload a data.", Toast.LENGTH_SHORT).show();
                    } else if ("1".equals(msg)) {

                        ClsUserInfo _ObjUser = new ClsUserInfo();
                        _ObjUser.setName(name);
                        _ObjUser.setRegisteredmobilenumber(mobile);
                        _ObjUser.setEmailaddress(email);
                        _ObjUser.setState(state);
                        _ObjUser.setCity(city);
                        _ObjUser.setPincode(pinCode);
                        _ObjUser.setLoginStatus("Active");
                        ClsGlobal.setUserInfo(_ObjUser, this);
                        Log.d("setUserInfo", "Profile Update Profile: ");

                        Toast.makeText(this, "profile updated successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, BottomNavigationActivity.class);
                        startActivity(intent);
                    } else if ("2".equals(msg)) {
                        Toast.makeText(this, "Mobile number is not valid.", Toast.LENGTH_SHORT).show();
                    } else if ("3".equals(msg)) {
                        Toast.makeText(this, "Mobile number is required.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "We facing issue to the update your profile, please try again letter.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getProfile() {
        countryViewModels.getProfile(shMobile, "")
                .observe(this, clsGetProfileResponse -> {
                    goBack = false;

                    if (clsGetProfileResponse != null) {
                        getProfiles = clsGetProfileResponse.getData();

                        String success = clsGetProfileResponse.getSuccess();
                        if (success.equalsIgnoreCase("2")) {
                            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                        } else if (success.equalsIgnoreCase("0")) {
                            Toast.makeText(this, "Fail to load a data.", Toast.LENGTH_SHORT).show();
                        }

                        if (success.equalsIgnoreCase("1")) {
                            tv_name.setText(getProfiles.getName());
                            tv_mobile.setText(getProfiles.getMobileNo());
                            tv_email.setText(getProfiles.getEmail());
                            tv_state.setText(getProfiles.getState());
                            tv_state.setTag(getProfiles.getStateID());
                            stateId = getProfiles.getStateID();

                            tv_city.setText(getProfiles.getCity());
                            tv_pin_code.setText(getProfiles.getPinCode());
                            tv_gender.setText(getProfiles.getGender());
                            tv_dob.setText(getProfiles.getDOB());

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

                            ClsGlobal.setUserInfo(clsUserInfo, this);
                            Log.d("setUserInfo", "Profile GetProfile: ");

                            if (getProfiles.getMobileVerified().equals("YES")) {
                                ll_mobile_verified.setVisibility(View.VISIBLE);
                                tv_mobile_verify.setVisibility(View.GONE);
                            } else {
                                tv_mobile_verify.setVisibility(View.VISIBLE);
                                ll_mobile_verified.setVisibility(View.GONE);
                            }

                            if (getProfiles.getEmailVerified().equals("YES")) {
                                String email = tv_email.getText().toString();
                                Log.d("emailv", "getProfile: " + email);

                                ll_email_verified.setVisibility(View.VISIBLE);
                                tv_email_verify.setVisibility(View.GONE);
                            } else {
                                tv_email_verify.setVisibility(View.VISIBLE);
                                ll_email_verified.setVisibility(View.GONE);
                            }

                        } else {
                            Toast.makeText(this, clsGetProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    pd.dismiss();
                });
    }

    private void verifyEmail() {
        String mobile = tv_mobile.getText().toString();
        String email = tv_email.getText().toString();

        ClsSendVerificationEmail verificationEmail = new ClsSendVerificationEmail();
        verificationEmail.setCustomerCode("");
        verificationEmail.setEmailToVerify(email);
        verificationEmail.setAppName(ClsGlobal.AppName);
        verificationEmail.setMobileNumber(mobile);
        verificationEmail.setUserType("Customer");

        Gson gson = new Gson();
        String jsonInString = gson.toJson(verificationEmail);
        Log.e("--URL--", "verificationEmail---" + jsonInString);

        otpActivityViewModel.verifyEmail(verificationEmail).observe(this, clsSendVerificationEmail -> {
            pd.dismiss();
            if (clsSendVerificationEmail != null) {
                String message = clsSendVerificationEmail.getSuccess();
                Log.d("msg", "sendOtp: " + message);

                if ("0".equals(message)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Email Verification");
                    builder.setMessage("Fail to send verification mail!");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if ("1".equals(message)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Email Verification");
                    builder.setMessage("We sent verification link on your email address, please check in junk/spam folder if don't appear in inbox.");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Email Verification");
                    builder.setMessage("we are facing issue in verify email, please try again later.");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            } else {
                Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidations() {

        if (tv_name.getText().toString().equals("")) {
            tv_name.setError("Required");
            return;
        }

        if (tv_gender.getText().toString().equals("")) {
            tv_state.setError("Required");
            return;
        }

        if (tv_mobile.getText().toString().equals("")) {
            tv_mobile.setError("Required");
            return;
        } else if (tv_mobile.getText().toString().length() != 10) {
            tv_mobile.setError("10 Digit number required");
            return;
        }

        if (tv_email.getText().toString().equals("")) {
            tv_email.setError("Required");
            return;
        } else if (!utils.isValidEmail(tv_email.getText().toString())) {
            tv_email.setError("Enter valid Email.");
            return;
        }

        if (tv_state.getText().toString().equals("")) {
            tv_state.setError("Select");
            return;
        }
        if (tv_city.getText().toString().equals("")) {
            tv_city.setError("Required");
            return;
        }

        if (tv_dob.getText().toString().equals("")) {
            tv_dob.setError("Required");
            return;
        }

        if (tv_pin_code.getText().toString().equals("")) {
            tv_pin_code.setError("Required");
            return;
        } else if (tv_pin_code.getText().toString().length() != 6) {
            tv_pin_code.setError("6 Digit number required");
            return;
        }

        if (tv_name != null && tv_mobile != null && tv_email != null && tv_state != null &&
                tv_city != null && tv_pin_code != null && tv_dob != null && tv_gender != null) {

            pd = ClsGlobal._prProgressDialog(this, "Updating profile...", false);
            pd.show();
            updateProfile();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.setting_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void getCity(int StateID) {

        countryViewModels.getCityResponse(StateID).observe(this, clsCityResponce -> {
            if (clsCityResponce != null) {
                cities = new ArrayList<>();
                citiesName = new ArrayList<>();
                cities = clsCityResponce.getData();
                if (cities.size() != 0) {
                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(cities);
                    Log.e("city", "city---" + jsonInString);
                    for (ClsCity city : cities) {
                        citiesName.add(city.getCityName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_list_item_1, (List<String>) citiesName);

                    tv_city.setThreshold(1);
                    tv_city.setAdapter(adapter);
                } else {
                    Toast.makeText(this, clsCityResponce.getSuccess() + " " + StateID, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Something went wrong".concat(String.valueOf(StateID)), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 2) {
                String state = data.getStringExtra("state");
                stateId = data.getIntExtra("stateId", 0);
                Log.d("stateId", "onActivityResult: " + stateId);
                tv_state.setText(state);
                tv_state.setTextColor(Color.parseColor("#757575"));
                getCity(stateId);
            }
        } catch (Exception ex) {
            Log.d("state selection", "onActivityResult: " + ex.toString());
        }
    }
}
