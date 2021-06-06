package kr.ac.duksung.dust;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;

public class AirActivity extends AppCompatActivity {
    TextView textView,textView2;
    ArrayList<String> citys;
    ArrayList<String> city;
    ArrayList<String> co;
    ArrayList<String> o3;
    ArrayList<String> no2;
    ArrayList<String> d10;
    ArrayList<String> d25;
    ArrayAdapter<String>adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        setTitle("서울 구별 미세먼지");

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        citys = new ArrayList<String>();
        city = new ArrayList<String>();
        co = new ArrayList<String>();
        o3 = new ArrayList<String>();
        no2 = new ArrayList<String>();
        d10 = new ArrayList<String>();
        d25 = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, citys);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("city",city.get(i));
                intent.putExtra("co",co.get(i));
                intent.putExtra("o3",o3.get(i));
                intent.putExtra("no2",no2.get(i));
                intent.putExtra("pm10",d10.get(i));
                intent.putExtra("pm25",d25.get(i));
                startActivity(intent);
            }
        });

        if (MainActivity.requestQueue == null) {
            MainActivity.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.update:
                citys.clear();
                city.clear();
                co.clear();
                o3.clear();
                no2.clear();
                d10.clear();
                d25.clear();
                makeRequest();
                return true;
            default:
                return false;
        }
    }
    public void makeRequest() {
        String url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst"+
                "?serviceKey=1fa%2FlR44wFH%2FRDIeAYeC%2BF3ciZM5Uf9ELbYrnvnedWpgRZg3yo2oKVhi2Z4dKt3DSwYQ3KiwUqeDWqVUbRtgMg%3D%3D"+
                "&numOfRows=25&pageNo=1&sidoName=%EC%84%9C%EC%9A%B8&searchCondition=DAILY&_returnType=json";
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
            int min = 1000000, max = 0;
            for(int i=0; i<array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if(i==0){
                    String data = obj.getString("dataTime");
                    textView.setText(data);
                }
                String ku = obj.getString("cityName");
                String pm10 = obj.getString("pm10Value");
                String temp = pm10.trim();
                if(!temp.equals("")){
                    int pm10_num = Integer.parseInt(temp);
                    if (min > pm10_num)
                        min = pm10_num;
                    if (max < pm10_num)
                        max = pm10_num;
                }
                citys.add(ku+":"+pm10);
                city.add(ku);
                co.add(obj.getString("coValue"));
                o3.add(obj.getString("o3Value"));
                no2.add(obj.getString("no2Value"));
                d10.add(pm10);
                d25.add(obj.getString("pm25Value"));

            }
            textView2.setText("최고: "+max+"/최저:"+min);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

