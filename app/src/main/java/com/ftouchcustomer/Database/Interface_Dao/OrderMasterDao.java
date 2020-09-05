package com.ftouchcustomer.Database.Interface_Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ftouchcustomer.Database.Entity.OrderMasterEntity;

import java.util.List;

@Dao
public interface OrderMasterDao {

    @Query("SELECT * FROM OrderMaster")
    LiveData<List<OrderMasterEntity>> getFranchiseMasterEntity();

    @Insert
    void Insert(List<OrderMasterEntity> list);

    @Insert
    void Insert(OrderMasterEntity objEntity);

    @Query("DELETE FROM OrderMaster")
    int DeleteAll();


/*

    @Query("UPDATE FranchiseVisitMaster SET DispositionCode = :DispositionCode," +
            "NextMeetingDateStr= :NextMeetingDateStr," +
            "NextMeetingRemark= :NextMeetingRemark WHERE MobileNumber = :MobileNumber")
    int updateFranchiseMaster(String MobileNumber, String DispositionCode, Date NextMeetingDateStr, String NextMeetingRemark);


    @Query("SELECT COUNT(0) FROM FranchiseVisitMaster")
    int totalCount();
*/


}
