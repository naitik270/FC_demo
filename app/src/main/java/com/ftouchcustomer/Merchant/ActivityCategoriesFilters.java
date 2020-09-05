package com.ftouchcustomer.Merchant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import static com.ftouchcustomer.Global.ConnectionCheckBroadcast.CheckInternetConnection;

public class ActivityCategoriesFilters extends AppCompatActivity implements AdapterCategoriesFilters.UpdateSelectStatus {

    ProgressDialog pd;
    RecyclerView recyclerView;
    TextView tv_no_item_found;
    AdapterCategoriesFilters adapter;
    MerchantViewModel merchantViewModel;
    List<ClsCatagoryList> clsCatagoryList = new ArrayList<>();
    List<ClsCatagoryList> filterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_filters);

        if (getActionBar() != null) {
            getActionBar().hide();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        merchantViewModel = new ViewModelProvider(this)
                .get(MerchantViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        tv_no_item_found = findViewById(R.id.tv_no_item_found);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AdapterCategoriesFilters(clsCatagoryList, this);
        recyclerView.setAdapter(adapter);

        pd = ClsGlobal._prProgressDialog(this, "Getting List...", true);
        pd.show();
        getCategory();
    }

    private void getCategory() {
        merchantViewModel.getCategoriesList("", "All").observe(this, clsCategoriesResponse -> {

            if (clsCategoriesResponse != null) {

                clsCatagoryList = clsCategoriesResponse.getData();

                if (clsCatagoryList != null) {
                    pd.dismiss();
                    adapter.AddItems(clsCatagoryList);
                }
            } else {
                Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
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
                filterMain(newText.trim(), clsCatagoryList);
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

    void filterMain(String text, List<ClsCatagoryList> lst) {

        filterList = StreamSupport.stream(lst)
                .filter(str -> str.getCategory().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonInString = gson.toJson(filterList);
        Log.e("--URL--", "filterList---" + jsonInString);

        adapter.AddItems(filterList);

        if (text.isEmpty()) {
            adapter.AddItems(clsCatagoryList);

            Gson gson1 = new Gson();
            String jsonInString1 = gson1.toJson(filterList);
            Log.e("--URL--", "NoFilterList---" + jsonInString1);
        }

        if (filterList.size() != 0) {
            tv_no_item_found.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.AddItems(filterList);
            pd.dismiss();
        } else {
            tv_no_item_found.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            pd.dismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            ClsGlobal.hideKeyboard(ActivityCategoriesFilters.this);
            List<String> CategoryList = new ArrayList<>();
            for (ClsCatagoryList OBJ : clsCatagoryList) {
                if (OBJ.getSelected()) {
                    CategoryList.add(OBJ.getCategory());
                }
            }

            String category;
            category = String.join(", ", CategoryList);
            Log.d("--CheckBox--", "String CategoryList : " + category);

            if (CheckInternetConnection(ActivityCategoriesFilters.this)) {
                Intent intent = new Intent(this, ActivityFilterMerchantList.class);
                intent.putExtra("category", category);
                setResult(2, intent);
                finish();
            } else {
                Toast.makeText(ActivityCategoriesFilters.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void updateSelectStatus(ClsCatagoryList obJ, boolean checked) {

        Gson gson = new Gson();
        String jsonInString = gson.toJson(obJ);
        Log.d("--CheckBox--", "updateSelectStatus : " + jsonInString);

        int index = clsCatagoryList.indexOf(obJ);
        clsCatagoryList.set(index, obJ);
        filterList = clsCatagoryList;
        adapter.notifyDataSetChanged();
    }

}