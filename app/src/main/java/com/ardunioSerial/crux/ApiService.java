package com.ardunioSerial.crux;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("https://ad-tracker-a8a4c.firebaseapp.com/ad_event?companyId=pcbway&amp;appId=arduino_serial_monitor&amp;adId=pcbway_all_ads&amp;redirectLink=https://www.pcbway.com/?from=crux2020")
    Call<ResponseBody> getPcbWayAdClick();

}
