package com.example.android.beamlargefiles.apiManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
//"pushsms?user=sitaram&authkey=92QmrvhurcK2&sender=SGMALI&mobile=+918460298962&text=SITARAM+G.MALI+Co-Op+CRE+SOC+Name+-+%7B%23var%23%7D%0AAC.+no+-+%7B%23var%23%7D%0APRV.+BAL+-+%7B%23var%23%7D%0ADEP.AMT+-+%7B%23var%23%7D%0ACUR.BAL+-+%7B%23var%23%7D%0ALast+Date+-+%7B%23var%23%7D%0AThank+You%21%21%0ARTtrad&entityid=1201159141994639834&templateid=1007537675847225564"

    @GET("pushsms")
    Call<SmsResponse> pushsms(
            @Query("user") String user,
            @Query("authkey") String authkey,
            @Query("sender") String sender,
            @Query("entityid") String entityid,
            @Query("templateid") String templateid,
            @Query(value = "mobile") String mobile,
            @Query(value = "text" ,encoded=true) String text

//            "user=sitaram" +
//                    "&authkey=92QmrvhurcK2" +
//                    "&sender=SGMALI" +
//                    "&mobile=mobile1,mobile2" +
//                    "&text=text" +
//                    "&entityid=entityid" +
//                    "&templateid=templateid"
    );


}