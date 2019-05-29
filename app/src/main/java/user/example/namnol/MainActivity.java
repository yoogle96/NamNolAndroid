package user.example.namnol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnMatching;
    private Button btnRandomChat;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        btnMatching = findViewById(R.id.btn_matching);
        btnRandomChat = findViewById(R.id.btn_randomChat);

        btnMatching.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ChatRoom.class);
                intent.putExtra("userName", userName);
                startActivityForResult(intent, 3000);
            }
        });

        btnRandomChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RandomChat.class);
                intent.putExtra("userName", userName);
                startActivityForResult(intent, 3000);
            }
        });
    }
}
