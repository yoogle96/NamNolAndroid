package user.example.namnol;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NoticeAPI {
    @GET("/notice")
    Call<ResponseBody>getNotice ();
}
