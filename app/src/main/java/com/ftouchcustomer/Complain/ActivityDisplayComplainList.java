package com.ftouchcustomer.Complain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Global.ApiClient;
import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Global.ConnectionDetector;
import com.ftouchcustomer.Interface.InterfaceComplain;
import com.ftouchcustomer.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDisplayComplainList extends AppCompatActivity {

    TextView txt_no_data;
    RecyclerView recyclerView;
    List<ClsComplainList> lstClsComplainLists = new ArrayList<>();
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        main();
    }

    private void main() {


        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Select complain type");
            setSupportActionBar(toolbar);

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_no_data = findViewById(R.id.txt_no_data);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getComplainList();


    }


    private void getComplainList() {
        lstClsComplainLists = new ArrayList<>();

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent) {
            InterfaceComplain interfaceComplain = ApiClient.getRetrofitInstance().create(InterfaceComplain.class);
            Log.e("--URL--", "interfaceCountry: " + interfaceComplain);

            Call<ClsComplainSuccess> obj = interfaceComplain.value("fTouch Hotel");
            Log.e("--URL--", "url: " + obj.request().url());

            final ProgressDialog pd =
                    ClsGlobal._prProgressDialog(ActivityDisplayComplainList.this, "Waiting...", true);
            pd.show();

            obj.enqueue(new Callback<ClsComplainSuccess>() {
                @Override
                public void onResponse(Call<ClsComplainSuccess> call, Response<ClsComplainSuccess> response) {
                    pd.dismiss();

                    if (response.body() != null) {
                        try {
                            List<ClsComplainList> liveStateList = response.body().getData();
                            if (liveStateList != null && liveStateList.size() != 0) {
                                lstClsComplainLists.addAll(liveStateList);
                            }

                            fillComplainListAdapter();

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsComplainSuccess> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "No Response found !", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    void fillComplainListAdapter() {

        AdapterComplainList adapter = new AdapterComplainList(ActivityDisplayComplainList.this);

        adapter.SetOnClickListener((obj, position) -> {
            Intent intent = new Intent();
            intent.putExtra("complain_name", obj.getDispositionCode());
            setResult(RESULT_OK, intent);
            finish();
        });
        adapter.AddItems(lstClsComplainLists);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
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
