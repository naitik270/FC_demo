package com.ftouchcustomer.Orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Merchant.ClsMerchantParams;
import com.ftouchcustomer.Merchant.ClsMerchantResponse;
import com.ftouchcustomer.Repository.Repository;

import java.util.List;

public class MenuUrlViewModel extends AndroidViewModel {

    private Repository repository;

    public MenuUrlViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<List<ClsLayerItemMaster>> getMenuListUrl(String userCode) {
        return repository.getMenuList(userCode);
    }

}
