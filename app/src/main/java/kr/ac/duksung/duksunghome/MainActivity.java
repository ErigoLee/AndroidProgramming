package kr.ac.duksung.duksunghome;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayList<String> urls;
    ArrayAdapter<String> adapter;
    ListView listView;


    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<String>();
        urls = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),items.get(i),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),WebActivity.class);
                intent.putExtra("news_url",urls.get(i));
                startActivity(intent);
            }
        });


        requestQueue = Volley.newRequestQueue(getApplicationContext());
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
        Intent intent;
        switch (item.getItemId()){
            case R.id.update:
                items.clear();
                urls.clear();
                makeRequest();
                return true;
            default:
                return false;
        }
    }

    public void makeRequest() {
        String url = "http://www.chosun.com/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // for Chosun page (homework)
                        try {
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(getApplicationContext(), "encoding error",
                                    Toast.LENGTH_LONG).show();
                        }

                        parseHtml(response);
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
        requestQueue.add(request);
    }

    public void parseHtml(String html) {
        Document doc = Jsoup.parse(html);
        String titleStr = doc.title();
        Elements itemElements = doc.select("div section.art_list div.sec_inner div.sec_con div.top_news h2");
        // XPath '//div/section[@id="sec_headline"]/div[@class="sec_inner"]/div[@class="sec_con"]/div[@class="top_news"]/h2'

        Elements itemElements1 = doc.select("div section.art_list div.sec_inner div.sec_con dl dt");
        // XPath //div/section[@class="art_list"]/div[@class="sec_inner"]/div[@class="sec_con"]/dl/dt




        for(Element element : itemElements) {
            items.add(element.text().trim());
            urls.add(element.select("a").first().absUrl("href"));
            android.util.Log.d("test: ", element.text());
        }
        int size = itemElements1.size();
        int a = 0;
        for(Element element : itemElements1) {
            if (a < size-3) {
                items.add(element.text().trim());
                urls.add(element.select("a").first().absUrl("href"));
            }
            a++;
            android.util.Log.d("test: ", element.text());
        }

        android.util.Log.d("test: ", items.size() + " items");
        adapter.notifyDataSetChanged();
    }



}
