package com.ftouchcustomer.Merchant;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Repository.Repository;

public class MerchantViewModel extends AndroidViewModel {

    private Repository repository;

    public MerchantViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<ClsMerchantResponse> getMerchantList(ClsMerchantParams clsMerchantParams) {
        return repository.getMerchantList(clsMerchantParams);
    }

    public LiveData<ClsMerchantProfileAdd> getMerchantProfileAdd(ClsMerchantProfileAdd clsMerchantProfileAdd) {
        return repository.getMerchantProfileAdd(clsMerchantProfileAdd);
    }

    public LiveData<ClsCategoriesResponse> getCategoriesList(String customerCode,String mode) {
        return repository.getCategoriesList(customerCode,mode);
    }

    public LiveData<ClsFavoriteMerchantResponse> favoriteMerchant(ClsFavoriteMerchantParams obj) {
        return repository.favoriteMerchantAPI(obj);
    }

}
