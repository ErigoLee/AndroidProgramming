package kr.ac.duksung.dust;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    TextView textView,textView2,textView3,textView4,textView5,textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        setTitle(city+" 상세정보");


        String co = intent.getStringExtra("co");
        String o3 = intent.getStringExtra("o3");
        String no2 = intent.getStringExtra("no2");
        String pm10 = intent.getStringExtra("pm10");
        String pm25 = intent.getStringExtra("pm25");

        textView = findViewById(R.id.textView5);
        textView2 = findViewById(R.id.textView4);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView6);
        textView5 = findViewById(R.id.textView7);
        textView6 = findViewById(R.id.textView8);

        textView.setText("일산화탄소: "+co);
        textView2.setText("오존: "+o3);
        textView3.setText("이산화질소: "+no2);
        textView4.setText("미세먼지: "+pm10);
        textView5.setText("초미세먼지: "+pm25);

        if (MainActivity.requestQueue == null) {
            MainActivity.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();
    }

    public void makeRequest() {
        String url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureLIst"+
                "?serviceKey=1fa%2FlR44wFH%2FRDIeAYeC%2BF3ciZM5Uf9ELbYrnvnedWpgRZg3yo2oKVhi2Z4dKt3DSwYQ3KiwUqeDWqVUbRtgMg%3D%3D"+
                "&numOfRows=10&pageNo=1&itemCode=PM10&dataGubun=DAILY&searchCondition=MONTH&_returnType=json";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "network error",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        request.setShouldCache(false);
        MainActivity.requestQueue.add(request);
    }



    public void parseJson(String json) {
        try {
            android.util.Log.d("test: ", json);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("list");
            JSONObject obj = array.getJSONObject(0);
            String dataTime = obj.getString("dataTime");
            String seoul = obj.getString("seoul");
            textView6.setText(seoul+" ("+dataTime+")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
