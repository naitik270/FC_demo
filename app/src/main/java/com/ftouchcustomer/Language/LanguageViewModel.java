package com.ftouchcustomer.Language;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Repository.Repository;

public class LanguageViewModel extends AndroidViewModel {

    private Repository mRepository;

    public LanguageViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = new Repository(application);
    }

    public LiveData<ClsUpdateLanguageResponse> updateLanguage(ClsUpdateLanguage clsUpdateLanguage){
        return  mRepository.updateLanguage(clsUpdateLanguage);
    }
}
