package com.ftouchcustomer.Profile.State;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.Profile.Country.CountryViewModels;
import com.ftouchcustomer.Profile.ProfileActivity;
import com.ftouchcustomer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;


public class StateListActivity extends AppCompatActivity implements CountryAdapter.onStateClickListener {
    CountryViewModels countryViewModels;
    private List<ClsState> states = new ArrayList<>();
    CountryAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tv_no_item_found;
    private ProgressDialog pd;
    List<ClsState> filterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        tv_no_item_found = findViewById(R.id.tv_no_item_found);
        countryViewModels = new ViewModelProvider(this).get(CountryViewModels.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (ClsGlobal.isNetworkConnected(this)) {
            pd = ClsGlobal._prProgressDialog(this, "Getting States...", false);
            pd.show();
            getState(101);
        } else {
            Toast.makeText(this, "Please check your Internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void getState(int CountryID) {
        countryViewModels.getStateResponse(CountryID).observe(this, clsStateResponce -> {
            if (clsStateResponce != null) {


                Gson gson = new Gson();
                String jsonInString = gson.toJson(clsStateResponce);
                Log.e("--URL--", "clsStateResponse---" + jsonInString);

                if (clsStateResponce.getSuccess().equals("1")) {
                    states = clsStateResponce.getData();
                    if (states.size() != 0) {

                        Gson gson1 = new Gson();
                        String jsonInString1 = gson1.toJson(states);
                        Log.e("states", "states---" + jsonInString1);

                        adapter = new CountryAdapter(setHeaderState(states, StateListActivity.this), StateListActivity.this);//newHarderList
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(this, clsStateResponce.getSuccess() + " " + CountryID, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, clsStateResponce.getSuccess() + " " + CountryID, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Something went wrong " + CountryID, Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        });
    }

    public static ArrayList<ClsState> setHeaderState(List<ClsState> stateList, Context context) {//newHarderList
        try {
            ArrayList<ClsState> states = new ArrayList<>();
            if (stateList != null) {
                String firstCharCountry = "";

                for (int i = 0; i < stateList.size(); i++) {
                    ClsState obj = stateList.get(i);
                    if (firstCharCountry.toUpperCase().equals(obj.getStateName().substring(0, 1).toUpperCase())) {
                        states.add(obj);
                    } else {
                        firstCharCountry = obj.getStateName().substring(0, 1).toUpperCase();
                        ClsState state = new ClsState();
                        state.setStateID(-2);
                        state.setStateName(firstCharCountry);
                        states.add(state);
                        i = i - 1;
                    }
                }
            } else {
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
            }
            return states;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onStateClick(ClsState clsState) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("state", clsState.getStateName());
        intent.putExtra("stateId", clsState.getStateID());
        setResult(2, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
                filterMain(newText.trim(), states);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void filterMain(String text, List<ClsState> lst) {

        filterList = StreamSupport.stream(lst)
                .filter(str -> str.getStateName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
        filterList = setHeaderState(filterList,this);
        Gson gson = new Gson();
        String jsonInString = gson.toJson(filterList);
        Log.e("--URL--", "filterList---" + jsonInString);

        adapter.AddItems(filterList);

        if (text.isEmpty()) {
            adapter.AddItems(states);

            Gson gson1 = new Gson();
            String jsonInString1 = gson1.toJson(filterList);
            Log.e("--URL--", "NoFilterList---" + jsonInString1);
        }

        if (filterList.size() != 0){
            tv_no_item_found.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.AddItems(filterList);
        }else{
            tv_no_item_found.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
