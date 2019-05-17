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

public class Signup extends AppCompatActivity {
    private final String BASE_URL = "http://10.0.2.2:8000";
    private Retrofit mRetrofit;
    private UserAPI userAPI;
    private Call<UserDTO> mUserDTO;

    TextView etId;
    TextView etEmail;
    TextView etPassword;
    TextView etPasswordConfirm;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setRetrofitInit();


        // 뷰 연결
        etId = ((EditText)findViewById(R.id.et_id));
        etEmail = ((EditText)findViewById(R.id.et_email));
        etPassword = ((EditText)findViewById(R.id.et_password));
        etPasswordConfirm = ((EditText)findViewById(R.id.et_passwordConfirm));
        btnSubmit = findViewById(R.id.btn_submit);


        // 회원가입 버튼 클릭 이벤트
        btnSubmit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                createUser();
            }
        });

    }

    // Retrofit 초기화
    private void setRetrofitInit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userAPI = mRetrofit.create(UserAPI.class);
    }

    // 새로운 유저 생성
    private void createUser(){

        UserDTO userDTO = new UserDTO(etId.getText().toString(), etEmail.getText().toString() , etPassword.getText().toString(), etPasswordConfirm.getText().toString());
        mUserDTO = userAPI.createUser(userDTO);

        mUserDTO.enqueue(new Callback<UserDTO>(){
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response){
                Log.d("성공", response.toString());
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t){
                Log.d("에러", t.toString());
            }
        });
    }
}
