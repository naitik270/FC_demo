package com.ftouchcustomer.Database.Interface_Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import com.ftouchcustomer.Classes.ClsTotalAmountCustomerCodeWise;
import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Database.Entity.OrderMasterEntity;
import com.ftouchcustomer.Database.GetSetValue.ClsOrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {

    @Query("SELECT * FROM OrderDetails")
    LiveData<List<ClsOrderDetailsEntity>> getOrderDetailList();

    @Query("SELECT * FROM OrderDetails ORDER BY CustomerCode")
    List<ClsOrderDetailsEntity> getOrderDetailListByMerchantCode();

    @Query("SELECT sum(Amount) as TotalAmount,CustomerCode " +
            "FROM OrderDetails GROUP by CustomerCode ")
    List<ClsTotalAmountCustomerCodeWise> getTotalAmountCustomerCodeWise();

    @Insert
    long Insert(ClsOrderDetailsEntity objEntity);


/*

    @Query("UPDATE FranchiseVisitMaster SET DispositionCode = :DispositionCode," +
            "NextMeetingDateStr= :NextMeetingDateStr," +
            "NextMeetingRemark= :NextMeetingRemark WHERE MobileNumber = :MobileNumber")
    int updateFranchiseMaster(String MobileNumber, String DispositionCode, Date NextMeetingDateStr, String NextMeetingRemark);


    @Query("SELECT COUNT(0) FROM FranchiseVisitMaster")
    int totalCount();
*/



    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT COUNT(0) as item_count ,SUM(Amount) as total , OrderDetailID ,CustomerCode,CustomerID, OrderNo " +
            "FROM OrderDetails where CustomerCode = :CustomerCode ")
    LiveData<ClsOrderDetail> getFooterValue(String CustomerCode);


    @Query("SELECT * FROM OrderDetails where CustomerCode = :CustomerCode ")
    LiveData<List<ClsOrderDetailsEntity>> getOrderDetailByID(String CustomerCode);


    @Query("DELETE FROM OrderDetails WHERE OrderDetailID = :OrderDetailID")
    int DeleteByOrderDetailID(int OrderDetailID);


    //    @Query("DELETE FROM OrderDetails")
    @Query("DELETE FROM OrderDetails WHERE CustomerCode = :customerCode")
    int DeleteOrderByCode(String customerCode);


    @Query("SELECT * FROM OrderDetails where Item LIKE :value")
    LiveData<List<ClsOrderDetailsEntity>> getItemNameByCharacter(String value);


}
