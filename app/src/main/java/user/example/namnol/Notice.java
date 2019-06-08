package user.example.namnol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Notice extends AppCompatActivity {
    private final String BASE_URL = "https://namnol.herokuapp.com";
    private Retrofit mRetrofit;
    private NoticeAPI noticeAPI;
    private Call<ResponseBody> noticeDTO;

    private RecyclerView recyclerView;
    private ArrayList<NoticeDTO> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        setRetrofitInit();
        getNotice();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        new Description().execute();
    }

    // retrofit init
    private void setRetrofitInit(){
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES); // read timeout

        okHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        noticeAPI = mRetrofit.create(NoticeAPI.class);
    }

    // Notice GET
    private void getNotice(){


    }

    private class Description extends AsyncTask<Void, Void, Void>{
        private ProgressDialog progressDialog = new ProgressDialog(Notice.this);

        @Override
        protected void onPreExecute(){
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                noticeDTO = noticeAPI.getNotice();
                noticeDTO.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                        try{

                            String res = response.body().string();
                            JSONArray obj = new JSONArray(res);
                            for(int i = 0 ; i < obj.length(); i++){
                                JSONObject notice = obj.getJSONObject(i);
                                String title = notice.getString("title");
                                String writer = notice.getString("writer");
                                String date = notice.getString("date");

                                list.add(new NoticeDTO(title, writer, date));
                            }
                            System.out.println(list);


                        }catch (Exception e){
                            Log.e("error", e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t){
                        Log.d("에러", t.toString());
                    }
                });
                // list가 비어있지 않을때 까지 계속 기다리기
                while(list.isEmpty()){
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            NoticeAdapter myAdapter = new NoticeAdapter(list);
            System.out.println(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }
}
