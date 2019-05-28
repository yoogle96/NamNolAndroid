package user.example.namnol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Chat extends AppCompatActivity {
    private Button btnSend;
    private EditText etMsg;
    private ListView lvChating;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayRoom = new ArrayList<>();

    private String strRoomName;
    private String strUserName;

    private DatabaseReference reference;
    private String key;
    private String chatUser;
    private String chatMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        lvChating = (ListView) findViewById(R.id.lv_chating);
        btnSend = findViewById(R.id.btn_send);
        etMsg = findViewById(R.id.et_msg);

        Log.v("roomName : ", getIntent().getExtras().get("roomName").toString());
        Log.v("userName : ", getIntent().getExtras().get("userName").toString());
        strRoomName = getIntent().getExtras().get("roomName").toString();
        strUserName = getIntent().getExtras().get("userName").toString();
        reference = FirebaseDatabase.getInstance().getReference().child(strRoomName);

        setTitle(strRoomName + " 채팅방");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayRoom);
        lvChating.setAdapter(arrayAdapter);

        // 리스트뷰가 갱신될 때 하단으로 자동 스크롤 이동
        lvChating.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                // map을 사용해 name과 메시지를 가져오고, key에 값 요청
                Map<String, Object> map = new HashMap<String, Object>();
                // key로 데이터베이스 오픈
                String key = reference.push().getKey();
                reference.updateChildren(map);

                DatabaseReference dbRef = reference.child(key);

                Map<String, Object> objectMap = new HashMap<String, Object>();

                objectMap.put("name", strUserName);
                objectMap.put("message", etMsg.getText().toString());

                dbRef.updateChildren(objectMap);
                etMsg.setText("");
            }
        });

        /*
        addChileEventListener는 Child에서 일어나는 변화를 감지
        - onChildAdded() : 리스트의 아이템을 검색하거나 추가가 있을때 수신
        - onChildChanged() : 리스트의 아이템의 변화가 있을때 수신
        - onChildRemoved() : 리스트의 아이템이 삭제되었을때 수신
        - onChildMoved() : 리스트의 순서가 변경되었을때 수신
         */

        reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    chatListener(dataSnapshot);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    chatListener(dataSnapshot);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        private void chatListener(DataSnapshot dataSnapshot){
            Iterator i = dataSnapshot.getChildren().iterator();

            while(i.hasNext()){
                chatUser = (String) ((DataSnapshot) i.next()).getValue();
                chatMessage = (String) ((DataSnapshot) i.next()).getValue();

                arrayAdapter.add(chatMessage + " : " + chatUser);
                Log.v("유저 : ", chatUser);
                Log.v("메시지 : ", chatMessage);
            }

            arrayAdapter.notifyDataSetChanged();
    }
}
