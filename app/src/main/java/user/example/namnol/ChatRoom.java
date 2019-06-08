package user.example.namnol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatRoom extends AppCompatActivity {

    private ListView listView;
    private Button btnCreate;

    private RecyclerView recyclerView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<RoomDTO> arrRoomList = new ArrayList<>();
    private RoomAdapter myAdapter;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference conditionRef = reference.child("ChatRoom");
    private String name;
    private String userName;
    private String strRoom;

    Map<String, Object> map = new HashMap<String, Object>();

    DataSnapshot myDataSnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTitle("랜덤채팅 APP");
        setContentView(R.layout.activity_chat_room);

        // 로그인화면에서 닉네임을 가져온다.
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");


        recyclerView = (RecyclerView) findViewById(R.id.room_recycle);

        myAdapter = new RoomAdapter(arrRoomList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        btnCreate = findViewById(R.id.btn_create);
//        ListAdapter adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, android.R.layout.two_line_list_item, mRef)
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrRoomList);
//        listView.setAdapter(arrayAdapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(ChatRoom.this, CreateRoom.class);
                createIntent.putExtra("userName", userName);
                startActivity(createIntent);
            }
        });


        // 특정 경로의 전체 내용에 대한 변경 사항을 읽고 수신 대기함
        // onDateChange는 Database가 변경되었을 때 호출되고
        // onCancelled는 취소됐을때 호출된다.
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                arrRoomList.clear();
                while (i.hasNext()) {
                    String key = ((DataSnapshot) i.next()).getKey();
                    String title = dataSnapshot.child(key).child("moim_title").getValue().toString();
                    String kind = dataSnapshot.child(key).child("moim_group").getValue().toString();
                    String curr = dataSnapshot.child(key).child("moim_num").getValue().toString();
                    arrRoomList.add(new RoomDTO(title, kind, curr, key));
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myAdapter.setItemClick(new RoomAdapter.ItemClick() {
            @Override
            public void onClick(View view) {
                TextView titleView = (TextView) view.findViewById(R.id.tv_title);
                TextView kindView = (TextView) view.findViewById(R.id.tv_kind);
                TextView currView = (TextView) view.findViewById(R.id.tv_curr);
                TextView keyView = (TextView) view.findViewById(R.id.tv_key);
                String title = titleView.getText().toString();
                String kind = kindView.getText().toString();
                String curr = currView.getText().toString();
                String key = keyView.getText().toString();

                Intent detailIntent = new Intent(ChatRoom.this, RoomDetail.class);
                detailIntent.putExtra("userName", userName);
                detailIntent.putExtra("moim_title", title);
                detailIntent.putExtra("moim_group", kind);
                detailIntent.putExtra("moim_num", curr);
                detailIntent.putExtra("roomKey", key);
                startActivity(detailIntent);
            }
        });
    }
}

