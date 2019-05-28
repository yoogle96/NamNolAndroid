package user.example.namnol;

import android.database.Observable;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PortalAPI {
    @Headers({
            "User-Agent: PostmanRuntime/7.13.0",
            "Accept: */*",
            "Cache-Control: no-cache",
            "Postman-Token: e647348e-0311-4fe8-9950-84228872c769,fd5f8e23-48dc-40da-9d1a-26984a46016e",
            "Host: sso.nsu.ac.kr",
            "cookie: access_token=24c88259-bdf5-458b-8c0f-4ac032600d97",
            "accept-encoding: gzip, deflate",
            "Connection: keep-alive",
            "cache-control: no-cache",
    })
    @FormUrlEncoded
    @POST("/api/login/")
    Call<ResponseBody> checkPortal(@Field("id") String id, @Field("password") String password);
//    Call<PortalDTO> checkPortal(@Body PortalDTO portalDTO);
}
