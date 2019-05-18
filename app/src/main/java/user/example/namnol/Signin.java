package user.example.namnol;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class Signin extends AppCompatActivity {
    private final String BASE_URL = "http://10.0.2.2:8000";
    private Retrofit mRetrofit;
    private UserAPI userAPI;
    private Call<UserDTO> mUserDTO;

    TextView etId;
    TextView etPassword;
    Button btnSignin;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setRetrofitInit();

        etId = (EditText) findViewById(R.id.et_id);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnSignin = (Button) findViewById(R.id.btn_signin);
        btnSignup = (Button) findViewById(R.id.btn_signup);

        btnSignin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                signinUser();
            }
        });
    }

    private void setRetrofitInit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userAPI = mRetrofit.create(UserAPI.class);
    }

    // 유저 로그인
    private void signinUser(){
        UserDTO user = new UserDTO(etId.getText().toString(), etPassword.getText().toString());
        mUserDTO = userAPI.signinUser(user);

        mUserDTO.enqueue(new Callback<UserDTO>(){
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response){
                System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t){
                Log.d("에러", t.toString());
            }
        });
    }
}
