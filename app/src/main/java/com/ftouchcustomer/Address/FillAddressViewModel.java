package com.ftouchcustomer.Address;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Address.defaultAddress.ClsDefaultAddress;
import com.ftouchcustomer.Address.deleteAddress.ClsDeleteAddress;
import com.ftouchcustomer.Address.getAddress.ClsGetCustomerAddressResponse;
import com.ftouchcustomer.Address.postAddress.ClsCustomerAddress;
import com.ftouchcustomer.Repository.Repository;

public class FillAddressViewModel extends AndroidViewModel {
    private Repository repository;

    public FillAddressViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<ClsCustomerAddress> updateAddress(ClsCustomerAddress clsCustomerAddress) {
        return repository.postCustomerAddress(clsCustomerAddress);
    }

    public LiveData<ClsGetCustomerAddressResponse> getCustomerAddress(String mobile) {
        return repository.getCustomerAddress(mobile);
    }

    public LiveData<ClsDeleteAddress> deleteCustomerAddress(ClsDeleteAddress obj) {
        return repository.deleteCustomerAddress(obj);
    }

    public LiveData<ClsCustomerAddress> updateCustomerAddress(ClsCustomerAddress clsCustomerAddress) {
        return repository.updateCustomerAddress(clsCustomerAddress);
    }

    public LiveData<ClsDefaultAddress> defaultAddress(ClsDefaultAddress clsDefaultAddress) {
        return repository.defaultAddress(clsDefaultAddress);
    }

}
