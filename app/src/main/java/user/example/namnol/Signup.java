package user.example.namnol;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends AppCompatActivity {
    TextView etId;
    TextView etPassword;
    TextView etPasswordConfirm;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 뷰 연결
        etId = ((EditText)findViewById(R.id.et_id));
        etPassword = ((EditText)findViewById(R.id.et_password));
        etPasswordConfirm = ((EditText)findViewById(R.id.et_passwordConfirm));
        btnSubmit = findViewById(R.id.btn_submit);


        // 회원가입 버튼 클릭 이벤트
        btnSubmit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

    }
}
