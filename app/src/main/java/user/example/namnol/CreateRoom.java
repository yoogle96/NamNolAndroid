package user.example.namnol;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CreateRoom extends AppCompatActivity {

    //    다른 액티비티를 띄우기 위한 요청 코드(상수)
    public static final int move_page = 1001;
    Spinner spinner;
    private Handler mHandler;
    private Runnable mRunnable;
    private TextView nofp, mtitleinfo, minputinfo;
    private Button btn;
    String[] spinnerValue = {
            "운동",
            "스터디",
            "직거래"
    };

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = reference.child("ChatRoom");

    String moim_title;
    String moim_info;
    String moim_group;
    String moim_num;
    Map<String, Object> map = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        //component init
        minputinfo = (TextView)findViewById(R.id.inputinfo);
        mtitleinfo = (TextView) findViewById(R.id.titleinfo);
        spinner = (Spinner) findViewById(R.id.menu_Spinner);
        nofp = (TextView) findViewById(R.id.numberOfpeople);
        btn = (Button) findViewById(R.id.Spinner_Button);


        //spinner adapter init
        spinnerAdapter adapter = new spinnerAdapter(this, android.R.layout.simple_list_item_1);
        adapter.addAll(spinnerValue);
        adapter.add("모임 선택");

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());

        //spinner function
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String key = conditionRef.push().getKey();
                DatabaseReference dbRef = conditionRef.child(key);
                moim_title = mtitleinfo.getText().toString();
                moim_info = minputinfo.getText().toString();
                moim_group = String.valueOf(spinner.getSelectedItem());
                moim_num = nofp.getText().toString();
                map.put("moim_title", moim_title);
                map.put("moim_info", moim_info);
                map.put("moim_group", moim_group);
                map.put("moim_num", moim_num);
                map.put("chat", "");
                dbRef.updateChildren(map);
                //                message print
                Toast.makeText(CreateRoom.this, "생성 모임 : "  + String.valueOf(spinner.getSelectedItem())
                        + "\n모집 인원 : "+ nofp.getText() + " 명", Toast.LENGTH_SHORT).show();

                //                delay
                //        Runnable을 사용해 1초 delay 후 detail page로 이동


                Log.v("Key : ", conditionRef.push().getKey());

                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), RoomDetail.class);
                        intent.putExtra("moim_title",mtitleinfo.getText().toString());
                        intent.putExtra("moim_info",minputinfo.getText().toString());
                        intent.putExtra("moim_group",String.valueOf(spinner.getSelectedItem()));
                        intent.putExtra("moim_num",nofp.getText().toString());

                        startActivity(intent);
                    }
                };
                //handler 를 통해 system delay
                mHandler = new Handler();
                mHandler.postDelayed(mRunnable,1000);
            }
        });
    }
}
