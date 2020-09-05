package com.ftouchcustomer.Orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Repository.Repository;

public class OrderListViewModel extends AndroidViewModel {

    private Repository repository;

    public OrderListViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<ClsCustomerOrderResponse> getCustomerOrderList(ClsCustomerOrderListParams clsCustomerOrderListParams) {
        return repository.getCustomerOrderList(clsCustomerOrderListParams);
    }




}
