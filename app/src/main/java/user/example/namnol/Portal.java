package user.example.namnol;


import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;

public class Portal extends AppCompatActivity {
    private final String BASE_URL = "https://sso.nsu.ac.kr";
    private Retrofit mRetrofit;
    private PortalAPI portalAPI;
    private Call<ResponseBody> portalDTO;

    TextView etId;
    TextView etPassword;
    Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        setRetrofitInit();

        etId = (EditText) findViewById(R.id.et_id);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnCheck = findViewById(R.id.btn_check);

        btnCheck.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                checkPortal();
            }
        });
    }

    private void setRetrofitInit(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        portalAPI = mRetrofit.create(PortalAPI.class);
    }

    private void checkPortal(){
        PortalDTO portal = new PortalDTO(etId.getText().toString(), etPassword.getText().toString());
        System.out.println(etId.getText().toString());
        System.out.println(etPassword.getText().toString());
        portalDTO = portalAPI.checkPortal(portal.id, portal.password);

        portalDTO.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){

                try{
                    String res = response.body().string();
                    JSONObject obj = new JSONObject(res);



                    Log.d("My App", obj.getString("code"));
                }catch (Exception ex){
                    Log.e("error","Exception -> "+ex.getMessage());
                }

//                    Log.e("Success", response.body().);
//                Log.e("Success", response.body().toString());
//                System.out.println("성공!");
//                System.out.println(response.body());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t){
//                Log.d("에러", new Gson()t.toString());
            }
        });
    }
}
