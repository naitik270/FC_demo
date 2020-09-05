package com.ftouchcustomer.Orders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.PlaceOrder.ClsCancelOrderParams;
import com.ftouchcustomer.Repository.Repository;

public class CancelOrderViewModel extends AndroidViewModel {

    private Repository repository;

    public CancelOrderViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<ClsPlaceOrderDeleteResponse> cancelOrder(ClsCancelOrderParams obj) {
        return repository.cancelPlaceOrderAPI(obj);
    }

}
