package com.example.newmaplepush;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageFragmentActivity extends AppCompatActivity {

    private static final String TAG = "ManageActivity";

    String myJSON;

    String Editname, Nowname;

    private String token;
    private static final String TAG_NAME = "id";
    private static final String TAG_RESULTS = "result";
    private View header;
    JSONArray manage = null;
    ArrayList<HashMap<String, String>> manageList;

    private Button btn;
    ListView list;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.manage_list);
        list = (ListView) findViewById(R.id.key_word_list);

        findViewById(R.id.add_button).setOnClickListener(listaddbtn);


        manageList = new ArrayList<HashMap<String, String>>();
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            manage = jsonObj.getJSONArray(TAG_RESULTS);
            Log.e("확인  ", String.valueOf(manage));

            for (int i = 0; i < manage.length(); i++) {
                JSONObject c = manage.getJSONObject(i);
                String name = c.getString(TAG_NAME);
                HashMap<String, String> manage = new HashMap<String, String>();
                manage.put(TAG_NAME, name);
                manageList.add(manage);
            }

            ListAdapter adapter = new SimpleAdapter(
                    ManageFragmentActivity.this, manageList, R.layout.manage_main,
                    new String[]{TAG_NAME},
                    new int[]{R.id.name}
            );



            list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    // 리스트 추가 클릭
    Button.OnClickListener listaddbtn = new View.OnClickListener() {
        public void onClick(View v) {
            Editname = ((EditText)(findViewById(R.id.edit_name))).getText().toString();
            token = getIntent().getStringExtra("token");

            //String url = "http://10.0.2.2/maplepush/managelistinsert.php"; // 수정
             String url = "http://cglab.sch.ac.kr/nj/managelistinsert.php"; // 수정
            ContentValues values = new ContentValues();
            values.put("Token", token);
            values.put("Name", Editname);
            NetworkTask networkTask = new NetworkTask(url, values);
            networkTask.execute();

            onResume();
        }
    };

    // 리스트 삭제
    Button.OnClickListener listdelbtn = new View.OnClickListener() {
        public void onClick(View v) {
            Log.e("클릭한 내용", "11112");

            /*Nowname = ((TextView)(findViewById(R.id.name))).getText().toString();
            token = getIntent().getStringExtra("token");

            Log.e("클릭한 내용", Nowname);

            String url = "http://10.0.2.2/maplepush/managelistdelete.php"; // 수정
            // String url = "http://cglab.sch.ac.kr/nj/managelistinsert.php"; // 수정
            ContentValues values = new ContentValues();
            values.put("Token", token);
            values.put("Name", Editname);
            NetworkTask networkTask = new NetworkTask(url, values);
            networkTask.execute();

            onResume();*/
        }
    };

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;

        }

        @Override
        protected String doInBackground(Void... params) {

            Log.e("values", String.valueOf(values));
            Log.e("url", url);


            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            Log.e("값 전송 결과 확인", result);
            return result;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            myJSON = s;
            showList();

        }
    }

    public void onResume()
    {
        super.onResume();
        setContentView(R.layout.manage_list);
        list = (ListView) findViewById(R.id.key_word_list);

        findViewById(R.id.add_button).setOnClickListener(listaddbtn);

        manageList = new ArrayList<HashMap<String, String>>();
        token = getIntent().getStringExtra("token");
        //String url = "http://10.0.2.2/maplepush/managelist.php"; // 수정
        String url = "http://cglab.sch.ac.kr/nj/managelist.php"; // 수정
        ContentValues values = new ContentValues();
        values.put("Token", token);
        //values.put("Name", Editname);
        NetworkTask networkTask = new NetworkTask(url, values);
        networkTask.execute();



    }

}

