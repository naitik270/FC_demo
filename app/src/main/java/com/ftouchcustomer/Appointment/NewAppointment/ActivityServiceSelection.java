package com.ftouchcustomer.Appointment.NewAppointment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Global.ApiClient;
import com.ftouchcustomer.Interface.InterfaceServices;
import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityServiceSelection extends AppCompatActivity implements AdapterServiceSelection.UpdateSelectStatus {

    RecyclerView recyclerView;
    List<ClsServicesList> clsServicesLists = new ArrayList<>();
    List<ClsServicesList> filterList = new ArrayList<>();
    AdapterServiceSelection adapter;
    private TextView tv_no_item_found;
    String mServiceUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_selection);

        if (getActionBar() != null) {
            getActionBar().hide();
        }

        Toolbar toolbar = findViewById( R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent1 = getIntent();
        mServiceUrl = intent1.getStringExtra("mServiceUrl");
        Log.d("mServiceUrl", "onCreate: " + mServiceUrl);

        tv_no_item_found = findViewById(R.id.tv_no_item_found);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterServiceSelection(clsServicesLists, this);
        recyclerView.setAdapter(adapter);

        getServices();
    }

    private void getServices() {
        InterfaceServices scalarService = ApiClient.getRetrofitInstance().create(InterfaceServices.class);
        Call<List<ClsServicesList>> stringCall = scalarService.ServiceList(mServiceUrl);
        stringCall.enqueue(new Callback<List<ClsServicesList>>() {
            @Override
            public void onResponse(@NonNull Call<List<ClsServicesList>> call, @NonNull Response<List<ClsServicesList>> response) {
                if (response.isSuccessful()) {
                    filterList = clsServicesLists = response.body();

                    if (clsServicesLists != null) {
                        tv_no_item_found.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.AddItems(clsServicesLists);
                    } else {
                        tv_no_item_found.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    tv_no_item_found.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ClsServicesList>> call, Throwable t) {
                Toast.makeText(ActivityServiceSelection.this, t.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_serch_and_tick, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMain(newText.trim(), clsServicesLists);
                return true;
            }
        });

        searchView.setQueryHint("Search");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        View closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(v -> {

            searchView.setQuery("", false);
            searchView.clearFocus();
        });

        return true;
    }

    void filterMain(String text, List<ClsServicesList> lst) {

        filterList = StreamSupport.stream(lst)
                .filter(str -> str.getServiceName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonInString = gson.toJson(filterList);
        Log.e("--URL--", "filterList---" + jsonInString);

        adapter.AddItems(filterList);

        if (text.isEmpty()) {
            adapter.AddItems(clsServicesLists);

            Gson gson1 = new Gson();
            String jsonInString1 = gson1.toJson(filterList);
            Log.e("--URL--", "NoFilterList---" + jsonInString1);
        }

        if (filterList.size() != 0) {
            tv_no_item_found.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.AddItems(filterList);
        } else {
            tv_no_item_found.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            saveServices();
        }
        return super.onOptionsItemSelected(item);
    }
    String service = "";

/*
    @RequiresApi(api = Build.VERSION_CODES.O)
    void saveServices() {
        Log.d("--CheckBox--", "STEP1");
        List<String> serviceList = new ArrayList<>();
        for (ClsServicesList OBJ : clsServicesLists) {
            if (OBJ.getIsSelected()) {
                serviceList.add(OBJ.getServiceName());
            }
        }

        service = String.join(",", serviceList);

        if (!service.equalsIgnoreCase("")) {
            Intent intent = new Intent(this, ActivityNewAppointment.class);
            intent.putExtra("service", service);
            setResult(2, intent);
            finish();
        } else {
            Toast.makeText(this, "NO SERVICE FOUND !", Toast.LENGTH_SHORT).show();
        }

    }
*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    void saveServices() {

            List<String> serviceList = new ArrayList<>();
            for (ClsServicesList OBJ : clsServicesLists) {
                if (OBJ.getIsSelected()) {
                    serviceList.add(OBJ.getServiceName());
                }
            }

            Gson gson = new Gson();
            String jsonInString = gson.toJson(serviceList);
            Log.d("--CheckBox--", "Adapter serviceList : " + jsonInString);

            String service = "";
            service = String.join(",", serviceList);

            Intent intent = new Intent(this, ActivityNewAppointment.class);
            intent.putExtra("service", service);
            setResult(2, intent);
            finish();
    }

        @Override
    public void updateSelectStatus(ClsServicesList obJ, boolean checked) {
//        ClsServicesList OBJservicesList = clsServicesLists.get(position);
//        OBJservicesList.setSelected(checked);
        int index = clsServicesLists.indexOf(obJ);
        clsServicesLists.set(index, obJ);
        filterList = clsServicesLists;
        adapter.notifyDataSetChanged();

    }
}
