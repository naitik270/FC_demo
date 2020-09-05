package com.ftouchcustomer.Appointment.NewAppointment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ftouchcustomer.Global.ClsGlobal;

import com.ftouchcustomer.Global.ClsUserInfo;
import com.ftouchcustomer.NavigationTabs.BottomNavigationActivity;
import com.ftouchcustomer.R;
import com.ftouchcustomer.Appointment.GetAppointment.AppointmentViewModel;
import com.google.gson.Gson;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityNewAppointment extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int PICK_CONTACT = 5;
    AppointmentViewModel appointmentViewModel;
    private TextView tv_service;
    private TextView tv_date;
    private TextView tv_time;
    private EditText et_remark;
    private EditText et_name;
    private EditText et_mobile;
    ImageView iv_browse_contact,iv_service;
    private ProgressDialog pd;

    DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    Calendar calendar;
    ImageView iv_select_date,iv_select_time;
    ClsUserInfo clsUserInfo;

    String mCode;
    String mServiceUrl;
    int mId;
    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        calendar = Calendar.getInstance();

        Intent intent1 = getIntent();
        mId = intent1.getIntExtra("mId", 0);
        mCode = intent1.getStringExtra("mCode");
        mServiceUrl = intent1.getStringExtra("mServiceUrl");

        clsUserInfo = ClsGlobal.getUserInfo(this);


        RelativeLayout relative_layout_save = findViewById(R.id.relative_layout_save);

        iv_service = findViewById(R.id.iv_service);
        tv_service = findViewById(R.id.tv_service);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        et_remark = findViewById(R.id.et_remark);
        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        iv_select_date = findViewById(R.id.iv_select_date);
        iv_browse_contact = findViewById(R.id.iv_browse_contact);
        iv_select_time = findViewById(R.id.iv_select_time);

        iv_service.setOnClickListener(view -> SelectServices());
//        tv_date.setOnClickListener(view -> DatePicker());
//        tv_time.setOnClickListener(view -> TimePicker());


        relative_layout_save.setOnClickListener(view -> checkValidation());
        iv_browse_contact.setOnClickListener(view -> {
            requestPermission();
        });

        et_name.setText(clsUserInfo.getName());
        et_mobile.setText(clsUserInfo.getRegisteredmobilenumber());



        iv_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker();
            }
        });



        iv_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker();
            }
        });


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker();
            }
        });


        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker();
            }
        });


    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    private void checkValidation() {
        if (et_name.getText().toString().equals("")) {
            et_name.setError("Name is Required");
            return;
        }
        if (et_mobile.getText().toString().equals("")) {
            et_mobile.setError("Required");
            return;
        } else if (et_mobile.getText().toString().length() != 10) {
            et_mobile.setError("10 Digit number required");
            return;
        }
        if (tv_service.getText().toString().equalsIgnoreCase("Select services") ||
                tv_service.getText().toString().equalsIgnoreCase("")) {
            tv_service.setError("Please select at least one Service");
            return;
        }
        if (tv_date.getText().toString().equals("")) {
            tv_date.setError("Date is Required");
            return;
        }
        if (tv_time.getText().toString().equals("")) {
            tv_time.setError("Time is Required");
            return;
        }

        if (et_name != null && tv_service != null && tv_date != null && tv_time != null) {
            BookAppointment();
        }
    }

    private void BookAppointment() {

        pd = ClsGlobal._prProgressDialog(this, "Adding new Appointment", false);
        pd.show();

        ClsNewAppointments clsNewAppointments = new ClsNewAppointments();
        clsNewAppointments.setMerchantId(mId);
        clsNewAppointments.setMerchantCode(mCode);
        clsNewAppointments.setCustomerId((clsUserInfo.getCustomerId()));
        clsNewAppointments.setCustomerMobileNo(clsUserInfo.getRegisteredmobilenumber());
        clsNewAppointments.setAppointmentMobileNo(et_mobile.getText().toString());
        clsNewAppointments.setAppointment_Date(tv_date.getText().toString());
        clsNewAppointments.setAppointmenTime(tv_time.getText().toString());
        clsNewAppointments.setStatusRemark(et_remark.getText().toString());
        clsNewAppointments.setServices(tv_service.getText().toString());
        clsNewAppointments.setName(et_name.getText().toString());
        clsNewAppointments.setStaffpreference("abc");

        Gson gson = new Gson();
        String jsonInString = gson.toJson(clsNewAppointments);
        Log.e("--URL--", "clsNewAppointments---" + jsonInString);

        appointmentViewModel.NewAppointments(clsNewAppointments)
                .observe(this, clsNewAppointments1 -> {

                    if (clsNewAppointments1 != null) {
                        String message = clsNewAppointments1.getSuccess();

                        if ("0".equals(message)) {
                            Toast.makeText(this, "Appointment fail!", Toast.LENGTH_SHORT).show();
                        } else if ("1".equals(message)) {
                            Toast.makeText(this, "Your appointment is booked successfully.", Toast.LENGTH_SHORT).show();
//                            onBackPressed();
                            Intent intent = new Intent(ActivityNewAppointment.this, BottomNavigationActivity.class);
                            intent.putExtra("placeOrder", "NewAppointment");
                            startActivity(intent);
                        } else if ("2".equals(message)) {
                            Toast.makeText(this, "MerchantId Or CustomerId is required", Toast.LENGTH_SHORT).show();
                        } else if ("3".equals(message)) {
                            Toast.makeText(this, "Enter Valid MerchantId Or CustomerId", Toast.LENGTH_SHORT).show();
                        } else if ("4".equals(message)) {
                            Toast.makeText(this, "Appointment Date is required", Toast.LENGTH_SHORT).show();
                        } else if ("5".equals(message)) {
                            Toast.makeText(this, "Service is Required", Toast.LENGTH_SHORT).show();
                        } else if ("6".equals(message)) {
                            Toast.makeText(this, "Enter Valid Mobile No", Toast.LENGTH_SHORT).show();
                        } else if ("7".equals(message)) {
                            Toast.makeText(this, "Mobile Number is Required", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to add new Appointment", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                    }
                    pd.dismiss();
                });
    }

    private void TimePicker() {

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String todayDate = df.format(d);

        Log.d("--time--", "TimePicker: " + todayDate);

        if (date.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Select Date First", Toast.LENGTH_SHORT).show();
        } else if (date.equalsIgnoreCase(todayDate)) {

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog tpd = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                Calendar datetime = Calendar.getInstance();
                Calendar calendar1 = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                datetime.set(Calendar.MINUTE, minute1);
                if (datetime.getTimeInMillis() >= calendar1.getTimeInMillis()) {
                    tv_time.setText(DisplayTime(hourOfDay, minute1));
                } else {
                    Toast.makeText(ActivityNewAppointment.this, "Past time is not allowed!", Toast.LENGTH_LONG).show();
                }
            }, hour, minute, false);
            tpd.show();

        } else {

            Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view1, hourOfDay, minute) -> {
                        tv_time.setText(DisplayTime(hourOfDay, minute));
                    }, mHour, mMinute, false);

            timePickerDialog.show();
        }
    }

    public static String DisplayTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        return aTime;
    }

    private void DatePicker() {

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                Year = year;
                Month = month;
                Day = dayOfMonth;

                calendar.set(Calendar.YEAR, Year);
                calendar.set(Calendar.MONTH, Month);
                calendar.set(Calendar.DAY_OF_MONTH, Day);
                Format formatter;
                formatter = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());

                tv_date.setText(formatter.format(calendar.getTime()));
                date = formatter.format(calendar.getTime());
                Log.d("--time--", "DatePicker: " + date);
            }
        }, Year, Month, Day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void SelectServices() {
        Intent intent = new Intent(this, ActivityServiceSelection.class);
        intent.putExtra("mServiceUrl", mServiceUrl);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case (PICK_CONTACT): {
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = managedQuery(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                phones.moveToFirst();

                                String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                cNumber = cNumber.replace(" ", "");
                                cNumber = cNumber.replace("+91", "");
                                cNumber = cNumber.replace("-", "");
                                et_mobile.setText(cNumber);
                            }
                        }
                    }
                }
                case (2): {
                    String service = data.getStringExtra("service");
                    tv_service.setText(service);
                }
            }

        } catch (Exception ex) {
            Log.d("service selection", "onActivityResult: " + ex.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
//                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}