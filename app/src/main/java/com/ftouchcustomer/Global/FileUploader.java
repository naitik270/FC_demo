package com.ftouchcustomer.Global;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ftouchcustomer.Complain.ClsCustomerComplainParams;
import com.ftouchcustomer.Interface.InterfaceCustomerComplain;
import com.ftouchcustomer.Payment.ClsCustomerPaymentParams;
import com.ftouchcustomer.Payment.InterfaceMakePayment;
import com.ftouchcustomer.PlaceOrder.ClsPlaceOrderResponse;
import com.ftouchcustomer.PlaceOrder.InterfacePlaceOrder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileUploader {

    private FileUploaderCallback mFileUploaderCallback;
    long filesize = 0l;
    String mode = "";
    private boolean showProgressBar = false;
    Context mContext;
    ClsUserInfo getUserInfo = new ClsUserInfo();


    public FileUploader(Context context) {
        this.mContext = context;
        getUserInfo = ClsGlobal.getUserInfo(mContext);
    }


    public void showUploadProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }


    public void placeOrder(File file, int _merchantID, String _merchantCode,
                           String _deliveryType, int _items, int _addressID, String _comment,
                           String _paymentMethod, String _paymentDate, String _paymentStatus, String _paymentReferenceNo,
                           double _deliveryCharges, double _totalAmount, double _grandTotal) {

        InterfacePlaceOrder interfacePlaceOrder = ApiClient.getRetrofitInstance()
                .create(InterfacePlaceOrder.class);

        PRRequestBody mFile = new PRRequestBody(file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(
                "File", file.getName(), mFile);

        RequestBody CustomerID = RequestBody.create(
                MultipartBody.FORM, String.valueOf(getUserInfo.getCustomerId()));

        RequestBody MobileNo = RequestBody.create(
                MultipartBody.FORM, getUserInfo.getRegisteredmobilenumber());

        RequestBody MerchantID = RequestBody.create(
                MultipartBody.FORM, String.valueOf(_merchantID));

        RequestBody MerchantCode = RequestBody.create(
                MultipartBody.FORM, _merchantCode);

        RequestBody DeliveryType = RequestBody.create(
                MultipartBody.FORM, _deliveryType);

        RequestBody Items = RequestBody.create(
                MultipartBody.FORM, String.valueOf(_items));

        RequestBody AddressID = RequestBody.create(
                MultipartBody.FORM, String.valueOf(_addressID));

        RequestBody Comment = RequestBody.create(
                MultipartBody.FORM, _comment);

        RequestBody PaymentMethod = RequestBody.create(
                MultipartBody.FORM, _paymentMethod);

        RequestBody PaymentDate = RequestBody.create(
                MultipartBody.FORM, _paymentDate);

        RequestBody PaymentStatus = RequestBody.create(
                MultipartBody.FORM, _paymentStatus);

        RequestBody PaymentReferenceNo = RequestBody.create(
                MultipartBody.FORM, _paymentReferenceNo);

        RequestBody DeliveryCharges = RequestBody.create(
                MultipartBody.FORM, String.valueOf(_deliveryCharges));

        RequestBody TotalAmount = RequestBody.create(
                MultipartBody.FORM, String.valueOf(_totalAmount));

        RequestBody GrandTotal = RequestBody.create(
                MultipartBody.FORM, String.valueOf(_grandTotal));

        Call<ClsPlaceOrderResponse> callPlaceOrder =
                interfacePlaceOrder.placeOrderAPI(fileToUpload,
                        CustomerID, MobileNo, MerchantID,
                        MerchantCode, DeliveryType, Items, AddressID, Comment,
                        PaymentMethod, PaymentDate, PaymentStatus, PaymentReferenceNo,DeliveryCharges,TotalAmount,GrandTotal);


        Log.e("--Gson--", "callPlaceOrder: " + callPlaceOrder.request().url());

        Log.e("--Gson--", "MobileNo: " + getUserInfo.getRegisteredmobilenumber());
        Log.e("--Gson--", "_merchantID: " + _merchantID);
        Log.e("--Gson--", "_merchantCode: " + _merchantCode);
        Log.e("--Gson--", "_deliveryType: " + _deliveryType);
        Log.e("--Gson--", "_items: " + _items);
        Log.e("--Gson--", "_addressID: " + _addressID);
        Log.e("--Gson--", "_comment: " + _comment);
        Log.e("--Gson--", "_paymentMethod: " + _paymentMethod);
        Log.e("--Gson--", "_paymentDate: " + _paymentDate);
        Log.e("--Gson--", "_paymentStatus: " + _paymentStatus);
        Log.e("--Gson--", "_paymentReferenceNo: " + _paymentReferenceNo);
//

//        Gson gson2 = new Gson();
//        String jsonInString2 = gson2.toJson(callPlaceOrder);
//        Log.e("--URL--", "callPlaceOrder: " + jsonInString2);
//
        callPlaceOrder.enqueue(new Callback<ClsPlaceOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<ClsPlaceOrderResponse> call,
                                   @NonNull Response<ClsPlaceOrderResponse> response) {
                if (response.body() != null && response.isSuccessful()) {


                    Log.e("--Gson--", "response: " + response);
                    Log.e("--Gson--", "call: " + call);


                    mFileUploaderCallback.onFinish(response.body().getSuccess());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ClsPlaceOrderResponse> call,
                                  @NonNull Throwable t) {
                mFileUploaderCallback.onError();
                Log.e("--Gson--", "onFailure: " + call);
                Log.e("--Gson--", "t: " + t);
            }
        });

    }

    public void getMakePaymentAPI(File UploadFile, int OrderID, int MerchantID, String MerchantCode,
                                  String PaymentReferenceNo, String PaymentStatus) {

        InterfaceMakePayment interfacePlaceOrder = ApiClient.getRetrofitInstance()
                .create(InterfaceMakePayment.class);

        PRRequestBody mFile = new PRRequestBody(UploadFile);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(
                "Bill", UploadFile.getName(), mFile);

        RequestBody re_OrderID = RequestBody.create(MultipartBody.FORM,
                String.valueOf(OrderID));

        RequestBody re_CustomerID = RequestBody.create(
                MultipartBody.FORM, getUserInfo.getCustomerId());

        RequestBody re_CustomerMobileNo = RequestBody.create(
                MultipartBody.FORM, getUserInfo.getRegisteredmobilenumber());

        RequestBody re_MerchantID = RequestBody.create(
                MultipartBody.FORM, String.valueOf(MerchantID));

        RequestBody re_MerchantCode = RequestBody.create(
                MultipartBody.FORM, MerchantCode);

        RequestBody re_PaymentReferenceNo = RequestBody.create(
                MultipartBody.FORM, PaymentReferenceNo);

        RequestBody re_PaymentStatus = RequestBody.create(
                MultipartBody.FORM, PaymentStatus);

        Call<ClsCustomerPaymentParams> callMakePayment =
                interfacePlaceOrder.getCustomerMakePayment(fileToUpload, re_OrderID,
                        re_CustomerID, re_CustomerMobileNo, re_MerchantID,
                        re_MerchantCode, re_PaymentReferenceNo, re_PaymentStatus);

        Log.e("--Gson--", "callPlaceOrder: " + callMakePayment.request().url());

        Log.e("--Gson--", "MobileNo: " + getUserInfo.getRegisteredmobilenumber());
        Log.e("--Gson--", "re_OrderID: " + re_OrderID);
        Log.e("--Gson--", "re_CustomerID: " + re_CustomerID);
        Log.e("--Gson--", "re_CustomerMobileNo: " + re_CustomerMobileNo);
        Log.e("--Gson--", "re_MerchantID: " + re_MerchantID);
        Log.e("--Gson--", "re_MerchantCode: " + re_MerchantCode);
        Log.e("--Gson--", "re_PaymentReferenceNo: " + re_PaymentReferenceNo);
        Log.e("--Gson--", "re_PaymentStatus: " + re_PaymentStatus);


        callMakePayment.enqueue(new Callback<ClsCustomerPaymentParams>() {
            @Override
            public void onResponse(Call<ClsCustomerPaymentParams> call,
                                   Response<ClsCustomerPaymentParams> response) {

                Log.e("--Gson--", "response: " + response.body().getSuccess());
                Log.e("--Gson--", "call: " + call);

                mFileUploaderCallback.onFinish(response.body().getSuccess());
            }

            @Override
            public void onFailure(Call<ClsCustomerPaymentParams> call, Throwable t) {
                Log.e("--Gson--", "call: " + t.getMessage());
                mFileUploaderCallback.onError();
            }
        });

    }

/*

    public void getUploadComplainAPI(File UploadFile, String MobileNumber, String CustomerCode,
                                     String RequestSubject,String RequestRemark,
                                     String ProductName,String FileName,String FileExtension,String ApplicationType) {

        InterfaceCustomerComplain interfaceComplain =
                ApiClient.getRetrofitInstance().create(InterfaceCustomerComplain.class);

        PRRequestBody mFile = new PRRequestBody(UploadFile);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(
                "Complain_", UploadFile.getName(), mFile);

        RequestBody re_MobileNumber = RequestBody.create(MultipartBody.FORM, MobileNumber);

        RequestBody re_CustomerCode = RequestBody.create(
                MultipartBody.FORM, CustomerCode);

        RequestBody re_RequestSubject = RequestBody.create(
                MultipartBody.FORM, RequestSubject);

        RequestBody re_RequestRemark = RequestBody.create(
                MultipartBody.FORM, RequestRemark);

        RequestBody re_ProductName = RequestBody.create(
                MultipartBody.FORM, ProductName);

        RequestBody re_FileName = RequestBody.create(
                MultipartBody.FORM, FileName);

        RequestBody re_FileExtension = RequestBody.create(
                MultipartBody.FORM, FileExtension);

        RequestBody re_ApplicationType = RequestBody.create(
                MultipartBody.FORM, ApplicationType);

        Call<ClsCustomerComplainParams> callUploadComplain =
                interfaceComplain.postComplain(fileToUpload, re_MobileNumber,
                        re_CustomerCode, re_RequestSubject, re_RequestRemark,
                        re_ProductName, re_FileName, re_FileExtension,re_ApplicationType);

        Log.e("--Gson--", "callUploadComplain: " + callUploadComplain.request().url());

        Log.e("--Gson--", "MobileNo: " + getUserInfo.getRegisteredmobilenumber());
        Log.e("--Gson--", "re_MobileNumber: " + re_MobileNumber);
        Log.e("--Gson--", "re_CustomerCode: " + re_CustomerCode);
        Log.e("--Gson--", "re_RequestSubject: " + re_RequestSubject);
        Log.e("--Gson--", "re_RequestRemark: " + re_RequestRemark);
        Log.e("--Gson--", "re_ProductName: " + re_ProductName);
        Log.e("--Gson--", "re_FileName: " + re_FileName);
        Log.e("--Gson--", "re_FileExtension: " + re_FileExtension);
        Log.e("--Gson--", "re_ApplicationType: " + re_ApplicationType);


        */
/*callMakePayment.enqueue(new Callback<ClsCustomerPaymentParams>() {
            @Override
            public void onResponse(Call<ClsCustomerPaymentParams> call,
                                   Response<ClsCustomerPaymentParams> response) {

                Log.e("--Gson--", "response: " + response.body().getSuccess());
                Log.e("--Gson--", "call: " + call);

                mFileUploaderCallback.onFinish(response.body().getSuccess());
            }

            @Override
            public void onFailure(Call<ClsCustomerPaymentParams> call, Throwable t) {
                Log.e("--Gson--", "call: " + t.getMessage());
                mFileUploaderCallback.onError();
            }
        });
*//*



        callUploadComplain.enqueue(new Callback<ClsCustomerComplainParams>() {
            @Override
            public void onResponse(Call<ClsCustomerComplainParams> call, Response<ClsCustomerComplainParams> response) {
                mFileUploaderCallback.onFinish(response.body().getSuccess());
            }

            @Override
            public void onFailure(Call<ClsCustomerComplainParams> call, Throwable t) {
                mFileUploaderCallback.onError();
            }
        });

    }

*/

    public void SetCallBack(FileUploaderCallback fileUploaderCallback) {
        this.mFileUploaderCallback = fileUploaderCallback;
    }


    public class PRRequestBody extends RequestBody {
        private File mFile;

        private int DEFAULT_BUFFER_SIZE = 20000048;

        PRRequestBody(final File file) {
            mFile = file;
            if (mFile.length() < 524288000) {
                DEFAULT_BUFFER_SIZE = 399999;
            }
        }

        @Override
        public MediaType contentType() {
            // i want to upload only images
            return MediaType.parse("multipart/form-data");
        }

        @Override
        public long contentLength() throws IOException {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            long fileLength = mFile.length();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            FileInputStream in = new FileInputStream(mFile);
            long uploaded = 0;
//            Source source = null;

            try {
                int read;
//                source = Okio.source(mFile);
                Handler handler = new Handler(Looper.getMainLooper());

                while ((read = in.read(buffer)) != -1) {


                    // update progress on UI thread
                    if (mode.equalsIgnoreCase("Manual")
                            || mode.equalsIgnoreCase("Manual and Auto") || showProgressBar) {
                        handler.post(new ProgressUpdater(uploaded, fileLength));
                    }

                    uploaded += read;
                    sink.write(buffer, 0, read);
                }
//                sink.writeAll(source);

            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
            } finally {
                in.close();
            }
        }
    }


    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            int current_percent = (int) (100 * mUploaded / mTotal);
            int total_percent = (int) (100 * mUploaded / mTotal);

            if (mFileUploaderCallback != null) {

                mFileUploaderCallback.onProgressUpdate(current_percent, total_percent,
                        "File Size: " + ClsGlobal.readableFileSize(mUploaded) +
                                "/" + ClsGlobal.readableFileSize(filesize));
            }
        }
    }

    public interface FileUploaderCallback {

        void onError();

        void onFinish(String responses);

        void onProgressUpdate(int currentpercent, int totalpercent, String msg);
    }


}
