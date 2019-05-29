package user.example.namnol;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class Signin extends AppCompatActivity {
    private final String BASE_URL = "https://namnol.herokuapp.com";
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

        // 로그인 버튼 이벤트
        btnSignin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                signinUser();
            }
        });

        // 회원가입 버튼 이벤트
        btnSignup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
//                Intent intent = new Intent(Signin.this, Signup.class);
                Intent intent = new Intent(Signin.this, Portal.class);
                startActivityForResult(intent, 3000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 3000:
                    etId.setText(data.getStringExtra("signUpId"));
                    break;
            }
        }
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
                if(response.code() == 200){
                    Intent intent = new Intent(Signin.this, MainActivity.class);
                    intent.putExtra("userName", etId.getText().toString());
                    startActivityForResult(intent, 3000);
                }

            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t){
                Log.d("에러", t.toString());
            }
        });
    }
}
