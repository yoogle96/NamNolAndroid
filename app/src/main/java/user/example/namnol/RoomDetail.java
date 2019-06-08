package user.example.namnol;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RoomDetail extends AppCompatActivity {

    private TextView mmoim_title, mmoim_num, mmoim_group, mmoim_info;
    private Button mmoim_btn;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference conditionRef = reference.child("ChatRoom");
    private String key;
    private String roomName;
    Intent intent;
    String userName;
    String title;
    String info;
    String curr;
    String kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        intent = getIntent();

        userName = intent.getExtras().getString("userName");

        //component init
        mmoim_group = (TextView)findViewById(R.id.show_group);
        mmoim_info = (TextView)findViewById(R.id.show_info);
        mmoim_num = (TextView)findViewById(R.id.show_number);
        mmoim_title = (TextView)findViewById(R.id.show_title);
        mmoim_btn = (Button)findViewById(R.id.show_button);

        title = intent.getExtras().getString("moim_title");
        info = intent.getExtras().getString("moim_info");
        curr = intent.getExtras().getString("moim_num");
        kind = intent.getExtras().getString("moim_group");
        key = intent.getExtras().getString("roomKey");

        mmoim_group.setText(kind);
        mmoim_title.setText(title);
        mmoim_info.setText(info);
        mmoim_num.setText(curr);

        roomName = intent.getExtras().getString("title");

        mmoim_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(RoomDetail.this, Chat.class);
                chatIntent.putExtra("roomType", "chat");
                chatIntent.putExtra("userName", userName);
                chatIntent.putExtra("roomKey", key);
                chatIntent.putExtra("roomName", title);
                startActivity(chatIntent);
            }
        });

        //Get Data From gaesipan

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    mmoim_info.setText(dataSnapshot.child(key).child("moim_info").getValue().toString());
                }catch (Exception e){
//                    mmoim_title.setText(intent.getExtras().getString("moim_title"));
//                    mmoim_info.setText(intent.getExtras().getString("moim_info"));
//                    mmoim_group.setText(intent.getExtras().getString("moim_group"));
//                    mmoim_num.setText(intent.getExtras().getString("moim_num"));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
