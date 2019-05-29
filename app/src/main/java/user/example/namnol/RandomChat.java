package user.example.namnol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class RandomChat extends AppCompatActivity {

    private Button btnSearch;
    private Button btnCreate;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = reference.child("RandomChat");
    private String userName;
    private String strRoom;

    private String key;
    private DataSnapshot currDataSnap;

    Map<String, Object> map = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_chat);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        btnSearch = findViewById(R.id.btn_search);
        btnCreate = findViewById(R.id.btn_create);

        // 랜덤채팅방 생성
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String key = conditionRef.push().getKey();
                DatabaseReference dbRef = conditionRef.child(key);
                map.put("roomName", "랜덤채팅");
                map.put("currCount", 1);
                map.put("chat", "");
                dbRef.updateChildren(map);

                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.putExtra("roomName", "랜덤채팅");
                intent.putExtra("userName", userName);
                intent.putExtra("roomKey", key);
                intent.putExtra("roomType", "random");
                startActivity(intent);
            }
        });



        // 랜덤채팅방 참여
        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(currDataSnap.exists()){
                    Random random = new Random();

                    int randomChatCount = (int)currDataSnap.getChildrenCount();
                    int rand = random.nextInt(randomChatCount);
                    Iterator itr = currDataSnap.getChildren().iterator();
                    for(int i = 0; i < rand; i++) {
                        itr.next();
                    }
                    DataSnapshot childSnap = (DataSnapshot) itr.next();
                    childSnap.child("currCount").getRef().setValue(2);
                    key = childSnap.getKey();

                    Intent intent = new Intent(getApplicationContext(), Chat.class);
                    intent.putExtra("roomName", "랜덤채팅");
                    intent.putExtra("userName", userName);
                    intent.putExtra("roomKey", key);
                    intent.putExtra("roomType", "random");

                    startActivity(intent);

                    Log.i("Check Count", "존재합니다.");
                }
                else{
                    Log.i("Check Count", "존재하지 않습니다.");
                }
            }
        });

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currDataSnap = dataSnapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
