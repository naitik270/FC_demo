package com.ftouchcustomer.ViewModelClass;

import android.app.Application;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Database.GetSetValue.ClsOrderDetail;
import com.ftouchcustomer.Orders.ClsGetMerchantPaymentMethodResponse;
import com.ftouchcustomer.Repository.Repository;

import java.util.List;

public class AddToItemViewModel extends AndroidViewModel {

    private Repository repository;
    LiveData<List<ClsOrderDetailsEntity>> lstOrderDetail;
    LiveData<List<ClsOrderDetailsEntity>> lstOrderDetailByID;

    LiveData<ClsOrderDetail> _objOrderDetail;



    public AddToItemViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        lstOrderDetail = repository.getOrderDetailList();

    }

    public LiveData<List<ClsOrderDetailsEntity>> getLstOrderDetail() {
        return lstOrderDetail;
    }

    public List<ClsOrderDetailsEntity> getOrderDetailListByMerchantCode() {
        return repository.getOrderDetailListByMerchantCode();
    }

/*
    public LiveData<ClsOrderDetail> getFooterValue(String CustomerCode) {
        return repository.getFooterValue(CustomerCode);
    }
*/

    public LiveData<List<ClsOrderDetailsEntity>> getOrderDetailByID(String _customerCode) {
        lstOrderDetailByID = repository.getOrderDetailByID(_customerCode);
        return lstOrderDetailByID;
    }

    public void insert(ClsOrderDetailsEntity obj) {
        repository.InsertOrderDetail(obj);
    }

    public void DeleteByOrderDetailID(int OrderDetailID,String msg) {
        repository.DeleteByOrderDetailID(OrderDetailID,msg);
    }

    public void DeleteOrderByCode(String customerCode,String msg) {
        repository.DeleteOrderByCode(customerCode,msg);
    }

    public void getItemNameByCharacter(String value) {
        repository.getItemNameByCharacter(value);
    }

    public LiveData<ClsGetMerchantPaymentMethodResponse> getMerchantPaymentMethod(String _merchantCode) {
        return repository.getMerchantPaymentMethod(_merchantCode);
    }

    public LiveData<ClsOrderDetail> getFooterValue(String CustomerCode) {
        return repository.getFooterValue(CustomerCode);
    }
}
