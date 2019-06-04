package user.example.namnol;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
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

public class Signup extends AppCompatActivity {
    private final String BASE_URL = "https://namnol.herokuapp.com";
    private Retrofit mRetrofit;
    private UserAPI userAPI;
    private Call<ResponseBody> mUserDTO;

    TextView etId;
    TextView etEmail;
    TextView etPassword;
    TextView etPasswordConfirm;
    TextView tvError;
    Button btnSignup;
    Button btnCancel;

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
        tvError = findViewById(R.id.tv_error);
        btnSignup = findViewById(R.id.btn_signup);
        btnCancel = findViewById(R.id.btn_cancel);


        // 회원가입 버튼 클릭 이벤트
        btnSignup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                createUser();
            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
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

        final UserDTO userDTO = new UserDTO(etId.getText().toString(), etEmail.getText().toString() , etPassword.getText().toString(), etPasswordConfirm.getText().toString());
        mUserDTO = userAPI.createUser(userDTO);
        final String userName = userDTO.username;

        mUserDTO.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                try{
                    int res = response.code();
                    if(res == 201){
                        Intent signUpIntent = new Intent();
                        signUpIntent.putExtra("signUpId", userName);
                        setResult(RESULT_OK, signUpIntent);
                        finish();
                    }else{
                        tvError.setText("아이디 또는 비밀번호, 이메일 양식 오류");
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t){
                Log.d("에러", t.toString());
            }
        });



    }
}
