package user.example.namnol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Notice extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<NoticeDTO> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        new Description().execute();
    }

    private class Description extends AsyncTask<Void, Void, Void>{
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog = new ProgressDialog(Notice.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // TODO: wait 자바스크립트 로딩 시간 기다려여함
                Document doc = Jsoup.connect("https://www.nsu.ac.kr/?m1=page%25&menu_id=186%25&page=1%25&count=10%25").get();
                Elements mElementDataSize = doc.select("tbody").select("tr"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_title = elem.child(1).text();
                    String my_writer = elem.child(3).text();
                    String my_pubDate = elem.child(4).text();

                    //Log.d("test", "test" + mTitle);
                    //ArrayList에 계속 추가한다.
                    list.add(new NoticeDTO(my_title, my_writer, my_pubDate));
                }

                //추출한 전체 <li> 출력해 보자.
                Log.d("debug :", "List " + mElementDataSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            NoticeAdapter myAdapter = new NoticeAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }
}
