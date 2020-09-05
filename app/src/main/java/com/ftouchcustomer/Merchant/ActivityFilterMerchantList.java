package com.ftouchcustomer.Merchant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;

public class ActivityFilterMerchantList extends AppCompatActivity {

    EditText et_name;
    EditText et_city;
    EditText et_pin_code;
    TextView tv_categories;
    RelativeLayout relative_layout_save;
    EditText et_address;

    CheckBox ckb_online_store, ckb_favourite;
    ImageView iv_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_merchant_list);

        et_name = findViewById(R.id.et_name);
        et_city = findViewById(R.id.et_city);
        et_pin_code = findViewById(R.id.et_pin_code);
        tv_categories = findViewById(R.id.tv_categories);
        relative_layout_save = findViewById(R.id.relative_layout_save);
        et_address = findViewById(R.id.et_address);
        ckb_online_store = findViewById(R.id.ckb_online_store);
        ckb_favourite = findViewById(R.id.ckb_favourite);
        iv_service = findViewById(R.id.iv_service);

        iv_service.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityFilterMerchantList.this, ActivityCategoriesFilters.class);
            startActivityForResult(intent, 2);
        });

        relative_layout_save.setOnClickListener(view -> {

            ClsGlobal.hideKeyboard(ActivityFilterMerchantList.this);

            Intent intent = getIntent();
            intent.putExtra("requiredMerchantName", et_name.getText().toString());
            intent.putExtra("requiredCity", et_city.getText().toString());
            intent.putExtra("requiredPinCode", et_pin_code.getText().toString());
            intent.putExtra("requiredCategory", tv_categories.getText().toString());
            intent.putExtra("requiredAddress", et_address.getText().toString());

            if (ckb_favourite.isChecked()) {
                intent.putExtra("requiredFav", "yes");
            } else {
                intent.putExtra("requiredFav", "");
            }

            if (ckb_online_store.isChecked()) {
                intent.putExtra("requiredStore", "Enable");
            } else {
                intent.putExtra("requiredStore", "");
            }

            setResult(RESULT_OK, intent);
            finish();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2) {
                String category = data.getStringExtra("category");
                tv_categories.setText(category);
            }
        } catch (Exception ex) {
            Log.d("service selection", "onActivityResult: " + ex.toString());
        }
    }

}